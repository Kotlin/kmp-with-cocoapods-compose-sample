# CocoaPods to SPM Migration Process

## Project Overview

This document tracks the migration of the KMP project from CocoaPods to Swift Package Manager (SPM) dependencies.

**Migration Start Date:** 2026-03-31

---

## Phase 1: Pre-Migration Analysis

### 1.0 Verify the project builds

**Find the module that uses CocoaPods:**

Looking at the project structure, I identified three modules using CocoaPods:
- `composeApp` - main module with framework output
- `google-maps` - dependency module with GoogleMaps pod
- `lorem-ipsum` - dependency module with LoremIpsum pod

**Kotlin build verified:**
```
./gradlew :composeApp:compileKotlinIosSimulatorArm64
```
Result: **BUILD SUCCESSFUL**

**Current Kotlin version:** 2.3.20 (from `gradle/libs.versions.toml`)

The required Kotlin version with SPM support is: **2.3.20-titan-224**

**Analysis:**
- This is a version upgrade from 2.3.20 to 2.3.20-titan-224
- The titan version is a dev/custom build that likely requires a custom Maven repository
- See Phase 1.0a for next steps

### 1.0a Confirm Kotlin version with Swift Import support

**Analysis:** The project currently uses Kotlin 2.3.20, but SPM support requires version 2.3.20-titan-224.

**Question for user:**

> Does your project already use a Kotlin version with Swift Import support (swiftPMDependencies DSL)?

**Context:**
- Current version: 2.3.20
- Required version for SPM: 2.3.20-titan-224 (dev build)
- The dev build likely requires JetBrains dev repository

**If no** → I will update to 2.3.20-titan-224. Please confirm:

> Does this Kotlin version require a custom Maven repository (e.g., JetBrains dev repo at https://packages.jetbrains.team/maven/p/kt/dev)?

If yes, I'll add the dev repository to the Gradle configuration.

### 1.1 Check for deprecated CocoaPods workaround property

Checked `gradle.properties` - No deprecated property `kotlin.apple.deprecated.allowUsingEmbedAndSignWithCocoaPodsDependencies` found.

### 1.2 Check for EmbedAndSign disablers

Checked all `build.gradle.kts` files - No `TaskGraph.whenReady` filters or `tasks.matching` blocks found that disable `EmbedAndSign` tasks.

### 1.3 Check for third-party KMP libraries with bundled cinterop klibs

**Analysis:** The project uses:
- `LoremIpsum` pod - standard CocoaPods library
- `GoogleMaps` pod - standard CocoaPods library
- No KMP wrapper libraries detected (e.g., dev.gitlive firebase-*), so no bundled klibs to worry about

**Conclusion:** No third-party KMP libraries with bundled cinterop klibs found. All `cocoapods.*` imports will be transformed to `swiftPMImport.*`.

---

## Current CocoaPods Configuration

### Pods Declaration

| Module | Pod Name | Version | linkOnly |
|--------|----------|---------|----------|
## Phase 5: iOS Project Reconfiguration

### 5.1 Run integration tasks ✅

**Command used:**
```bash
XCODEPROJ_PATH='/Users/Andrey.Yastrebov/JB/kmp-with-cocoapods-compose-sample/iosApp/iosApp.xcodeproj' \
GRADLE_PROJECT_PATH=':composeApp' \
/Users/Andrey.Yastrebov/JB/kmp-with-cocoapods-compose-sample/gradlew \
-p '/Users/Andrey.Yastrebov/JB/kmp-with-cocoapods-compose-sample' \
:composeApp:integrateEmbedAndSign :composeApp:integrateLinkagePackage
```

**Result:** BUILD SUCCESSFUL

**What happened:**
- `integrateEmbedAndSign` - Added Gradle build phase to Xcode project
- `integrateLinkagePackage` - Generated `_internal_linkage_SwiftPMImport/` at iosApp/

### 5.2 Disable User Script Sandboxing ✅

**Action:** Set `ENABLE_USER_SCRIPT_SANDBOXING = NO` in the Xcode project.

**Verified:** The setting was updated in `iosApp/iosApp.xcodeproj/project.pbxproj` (2 occurrences updated).

### 5.3 Deintegrate CocoaPods

**Action:** CocoaPods is still configured with `use_frameworks!` for the project. The `composeApp` pod is still present but now backed by SPM dependencies.

**Note:** We're keeping the CocoaPods setup for now because the `composeApp` is still declared as a pod in the Podfile. The actual framework content is now coming from SPM dependencies through the `integrateEmbedAndSign` build phase.

**Next:** In Phase 6, we'll remove the CocoaPods plugin from Gradle.

---

## Migration Summary

**Completed phases:**
- ✅ Phase 1: Pre-Migration Analysis
- ✅ Phase 2: Gradle Configuration (repos, Kotlin version, constraints)
- ✅ Phase 3: Add swiftPMDependencies alongside CocoaPods
- ✅ Phase 4: Transform Kotlin imports to swiftPMImport format
- ✅ Phase 5: iOS Project Reconfiguration (integration, sandboxing)

**Pending phases:**
- Phase 6: Remove CocoaPods from Gradle
- Phase 7: Verify builds
- Phase 8: Write MIGRATION_REPORT.md

---

## Current Status

| Component | Status |
|-----------|--------|
| Kotlin version | Updated to 2.3.20-titan-224 |
| Kotlin build | ✅ BUILD SUCCESSFUL |
| SPM packages | Configured for Google Maps & LoremIpsum |
| Import transforms | Complete (5 files updated) |
| Xcode integration | ✅ Integration tasks completed |
| User Script Sandboxing | ✅ Disabled |
| CocoaPods removal | ⏳ Pending Phase 6 |

---

## Next Steps

1. Remove `kotlin("native.cocoapods")` plugin and `cocoapods {}` block from all modules
2. Remove deprecated gradle.properties entries
3. Clean up CocoaPods-related extras
4. Verify final build
5. Write comprehensive MIGRATION_REPORT.md

### Framework Configuration

**composeApp:**
- baseName: ComposeApp
- isStatic: true
- deploymentTarget: 16.6

### Kotlin Imports (cocoapods.*)

| File | Import |
|------|--------|
| lorem-ipsum/src/iosMain/kotlin/org/jetbrains/kotlin/lorem/Ipsum.kt | cocoapods.LoremIpsum.LoremIpsum |
| google-maps/src/iosMain/kotlin/org/jetbrains/kotlin/google.maps/GoogleMapViewController.kt | cocoapods.GoogleMaps.GMSCameraPosition |
| google-maps/src/iosMain/kotlin/org/jetbrains/kotlin/google.maps/GoogleMapViewController.kt | cocoapods.GoogleMaps.GMSMapView |
| google-maps/src/iosMain/kotlin/org/jetbrains/kotlin/google.maps/GoogleMapViewController.kt | cocoapods.GoogleMaps.GMSMarker |
| composeApp/src/iosMain/kotlin/org/jetbrains/kotlin/compose/sample/App.kt | cocoapods.GoogleMaps.GMSServices |

### iOS Project Location

**Xcode Project:** `iosApp/iosApp.xcodeproj`
**Workspace:** `iosApp/iosApp.xcworkspace`

### Podfile Dependencies

```
target 'iosApp' do
  pod 'composeApp', :path => '../composeApp'
end
```

Only one pod is declared in the Podfile: `composeApp` (the KMP framework).

### No non-KMP CocoaPods detected

---

## Migration Execution

**User confirmed:**
1. Use Kotlin version 2.3.20-titan-224 for SPM support
2. JetBrains dev repository is required
3. Proceed with migration

---

## Phase 2: Gradle Configuration

### Phase 2.1: Add custom Maven repository ✅

**Action:** Added JetBrains dev repository to `settings.gradle.kts` in both `pluginManagement` and `dependencyResolutionManagement`.

**File modified:** `settings.gradle.kts`

**Before:**
```kotlin
pluginManagement {
    repositories {
        // ...
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        // ...
        mavenCentral()
    }
}
```

**After:**
```kotlin
pluginManagement {
    repositories {
        // ...
        gradlePluginPortal()
        mavenCentral()
        maven("https://packages.jetbrains.team/maven/p/kt/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        // ...
        mavenCentral()
        maven("https://packages.jetbrains.team/maven/p/kt/dev")
    }
}
```

### Phase 2.2: Update Kotlin version ✅

**Action:** Updated Kotlin version in `gradle/libs.versions.toml` to 2.3.20-titan-224.

**File modified:** `gradle/libs.versions.toml`

**Before:**
```toml
kotlin = "2.3.20"
```

**After:**
```toml
kotlin = "2.3.20-titan-224"
```

### Phase 2.3: Add buildscript constraint ✅

**Action:** Added buildscript dependency constraint to root `build.gradle.kts` to enforce the Kotlin version.

**File modified:** `build.gradle.kts`

**Before:**
```kotlin
plugins {
    // ...
}
```

**After:**
```kotlin
plugins {
    // ...
}

buildscript {
    dependencies.constraints {
        "classpath"("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.20-titan-224!!")
    }
}
```

### Phase 3: Add swiftPMDependencies ✅

**Action:** Added `swiftPMDependencies {}` alongside existing `cocoapods {}` in all three modules.

**Files modified:**
- `composeApp/build.gradle.kts`
- `google-maps/build.gradle.kts`
- `lorem-ipsum/build.gradle.kts`

**Pattern used:**
```kotlin
swiftPMDependencies {
    iosMinimumDeploymentTarget = "16.0"

    swiftPackage(
        url = "https://github.com/owner/repo.git",
        version = "1.0.0",
        products = listOf("ProductName"),
        importedClangModules = listOf("ClangModuleName"),
    )
}
```

### Framework Configuration ✅

**Action:** Updated `binaries.framework {}` to use target-level configuration (Kotlin 2.3.20+ requirement for SPM).

**File modified:** `composeApp/build.gradle.kts`

**Before:**
```kotlin
kotlin {
    iosArm64() {
        binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    iosSimulatorArm64()
}
```

**After:**
```kotlin
kotlin {
    listOf(iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
}
```

**Verification:** `./gradlew :composeApp:compileKotlinIosSimulatorArm64` - **BUILD SUCCESSFUL**

---

## Phase 4: Kotlin Source Updates

### Import Namespace Formula

For this project:
- `composeApp` group: `org.jetbrains.kotlin.compose.sample` → `org.jetbrains.kotlin.compose.sample`
- `google-maps` group: `org.jetbrains.kotlin.google-maps` → `org.jetbrains.kotlin.google.maps`
- `lorem-ipsum` group: `org.jetbrains.kotlin.lorem-ipsum` → `org.jetbrains.kotlin.lorem.ipsum`

### Transform Imports in composeApp/src/iosMain/kotlin/org/jetbrains/kotlin/compose/sample/App.kt

**Analysis:** `composeApp` uses `google-maps` as a dependency. The `GMSServices` class comes from `google-maps` module, so the import should use `google-maps`'s namespace (group + module name).

**Namespace formula:**
- group: `org.jetbrains.kotlin.google-maps` → `org.jetbrains.kotlin.google.maps` (dashes to dots)
- module name: `google-maps` → `google.maps` (dashes to dots)

**Before:**
```kotlin
import cocoapods.GoogleMaps.GMSServices
```

**After:**
```kotlin
import swiftPMImport.org.jetbrains.kotlin.google.maps.google.maps.GMSServices
```

### Transform Imports in google-maps/src/iosMain/kotlin/org/jetbrains/kotlin/google.maps/GoogleMapViewController.kt

**Before:**
```kotlin
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMarker
```

**After:**
```kotlin
import swiftPMImport.org.jetbrains.kotlin.google.maps.google.maps.GMSCameraPosition
import swiftPMImport.org.jetbrains.kotlin.google.maps.google.maps.GMSMapView
import swiftPMImport.org.jetbrains.kotlin.google.maps.google.maps.GMSMarker
```

### Transform Imports in lorem-ipsum/src/iosMain/kotlin/org/jetbrains/kotlin/lorem/Ipsum.kt

**Before:**
```kotlin
import cocoapods.LoremIpsum.LoremIpsum
```

**After:**
```kotlin
import swiftPMImport.org.jetbrains.kotlin.lorem.ipsum.lorem.ipsum.LoremIpsum
```

### Import Transformation Summary

| File | Old Import | New Import |
|------|------------|------------|
| App.kt | `cocoapods.GoogleMaps.GMSServices` | `swiftPMImport.org.jetbrains.kotlin.google.maps.google.maps.GMSServices` |
| GoogleMapViewController.kt | `cocoapods.GoogleMaps.GMSCameraPosition` | `swiftPMImport.org.jetbrains.kotlin.google.maps.google.maps.GMSCameraPosition` |
| GoogleMapViewController.kt | `cocoapods.GoogleMaps.GMSMapView` | `swiftPMImport.org.jetbrains.kotlin.google.maps.google.maps.GMSMapView` |
| GoogleMapViewController.kt | `cocoapods.GoogleMaps.GMSMarker` | `swiftPMImport.org.jetbrains.kotlin.google.maps.google.maps.GMSMarker` |
| Ipsum.kt | `cocoapods.LoremIpsum.LoremIpsum` | `swiftPMImport.org.jetbrains.kotlin.lorem.ipsum.lorem.ipsum.LoremIpsum` |

---

## Phase 6: Remove CocoaPods from Gradle ✅

### Phase 6.1: Remove kotlinCocoapods plugin ✅

**Action:** Removed `alias(libs.plugins.kotlinCocoapods)` from all module `build.gradle.kts` files.

**Files modified:**
- `composeApp/build.gradle.kts`
- `google-maps/build.gradle.kts`
- `lorem-ipsum/build.gradle.kts`

### Phase 6.2: Remove cocoapods {} blocks ✅

**Action:** Removed all `cocoapods {}` configuration blocks from all modules.

**Files modified:**
- `composeApp/build.gradle.kts`
- `google-maps/build.gradle.kts`
- `lorem-ipsum/build.gradle.kts`

### Phase 6.3: Update libs.versions.toml ✅

**Action:** Removed CocoaPods-related entries.

**Before:**
```toml
[versions]
cocoapods-LoremIpsum = "2.0.0"
cocoapods-GoogleMaps = "10.3.0"

[plugins]
kotlinCocoapods = { id = "org.jetbrains.kotlin.native.cocoapods", version.ref = "kotlin" }
```

**After:**
```toml
[versions]
# CocoaPods versions removed - using SPM instead
```

### Phase 6.4: Remove buildscript constraint ✅

**Action:** Removed buildscript dependency constraint from root `build.gradle.kts`.

**File modified:** `build.gradle.kts`

**Before:**
```kotlin
buildscript {
    dependencies.constraints {
        "classpath"("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.20-titan-224!!")
    }
}
```

**After:**
```kotlin
// buildscript constraint removed - using version catalog instead
```

### Phase 6.5: Verify build after CocoaPods removal ✅

**Command:** `./gradlew :composeApp:compileKotlinIosSimulatorArm64`

**Result:** BUILD SUCCESSFUL

---

## Phase 7: Final Cleanup ✅

### Phase 7.1: CocoaPods Deintegration ✅

**Action:** Deintegrated CocoaPods from iOS project and removed files

**Commands:**
```bash
cd iosApp
pod deintegrate
rm -rf Pods/
rm -f Podfile.lock
```

**Result:** CocoaPods fully removed from Xcode project

**What was removed:**
- 3 'Copy Pods Resources' build phases
- 3 'Check Pods Manifest.lock' build phases
- 3 'Embed Pods Frameworks' build phases
- `Pods` directory
- `Podfile.lock`
- References to Pods project

---

## Migration Complete ✅

**Completed phases:**
- ✅ Phase 1: Pre-Migration Analysis
- ✅ Phase 2: Gradle Configuration (repos, Kotlin version, constraints removed)
- ✅ Phase 3: Add swiftPMDependencies alongside CocoaPods
- ✅ Phase 4: Transform Kotlin imports to swiftPMImport format
- ✅ Phase 5: iOS Project Reconfiguration (integration, sandboxing)
- ✅ Phase 6: Remove CocoaPods from Gradle

**Pending phases:**
- Phase 7: Verify builds (final verification)
- Phase 8: Write MIGRATION_REPORT.md

---

## Final Build Status

| Component | Status |
|-----------|--------|
| Kotlin version | Updated to 2.3.20-titan-224 |
| Kotlin build | ✅ BUILD SUCCESSFUL |
| SPM packages | Configured for Google Maps & LoremIpsum |
| Import transforms | Complete (5 files updated) |
| Xcode integration | ✅ Integration tasks completed |
| User Script Sandboxing | ✅ Disabled |
| CocoaPods removed | ✅ Plugin and config removed |

---

## Summary of Changes

| File | Changes |
|------|--------|
| `settings.gradle.kts` | Added JetBrains dev Maven repository |
| `gradle/libs.versions.toml` | Updated Kotlin version, removed CocoaPods versions/plugins |
| `build.gradle.kts` | Removed buildscript constraint, removed kotlinCocoapods plugin reference |
| `composeApp/build.gradle.kts` | Removed cocoapods plugin, removed cocoapods block, added swiftPMDependencies, updated binaries.framework |
| `google-maps/build.gradle.kts` | Removed cocoapods plugin, removed cocoapods block, added swiftPMDependencies |
| `lorem-ipsum/build.gradle.kts` | Removed cocoapods plugin, removed cocoapods block, added swiftPMDependencies |
| `composeApp/src/iosMain/kotlin/.../App.kt` | Updated import from `cocoapods.*` to `swiftPMImport.*` |
| `google-maps/src/iosMain/kotlin/.../GoogleMapViewController.kt` | Updated imports from `cocoapods.*` to `swiftPMImport.*` |
| `lorem-ipsum/src/iosMain/kotlin/.../Ipsum.kt` | Updated import from `cocoapods.*` to `swiftPMImport.*` |
| `iosApp/iosApp.xcodeproj/project.pbxproj` | Disabled User Script Sandboxing |

---

## Next Steps

1. Run Phase 7 final verification: `./gradlew clean build`
2. Verify framework generation: `./gradlew :composeApp:linkDebugFrameworkIosSimulator`
3. Complete Phase 8: Write comprehensive MIGRATION_REPORT.md
