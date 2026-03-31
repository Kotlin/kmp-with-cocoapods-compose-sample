plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

group = "org.jetbrains.kotlin.compose.sample"
version = "1.0-SNAPSHOT"

kotlin {
    listOf(iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    swiftPMDependencies {
        iosMinimumDeploymentTarget = "16.0"

        // Google Maps
        swiftPackage(
            url = "https://github.com/googlemaps/ios-maps-sdk",
            version = "10.3.0",
            products = listOf("GoogleMaps"),
            importedClangModules = listOf("GoogleMaps"),
        )

        // LoremIpsum
        swiftPackage(
            url = "https://github.com/lukaskubanek/LoremIpsum",
            version = "2.0.0",
            products = listOf("LoremIpsum"),
            importedClangModules = listOf("LoremIpsum"),
        )
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(projects.loremIpsum)
            implementation(projects.googleMaps)
        }

        all {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
    }

    compilerOptions {
        optIn.add("kotlinx.cinterop.ExperimentalForeignApi")
    }
}


