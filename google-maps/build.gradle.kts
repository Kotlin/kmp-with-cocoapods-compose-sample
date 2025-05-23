plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
}

group = "org.jetbrains.kotlin.google-maps"
version = "1.0-SNAPSHOT"

kotlin {
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Kotlin maps module with cocoapods dependencies"
        ios.deploymentTarget = "16.6"

        // We don't need a podspec for a module dependency
        noPodspec()

        pod("GoogleMaps") {
            version = libs.versions.cocoapods.googleMaps.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }

    sourceSets.all {
        languageSettings {
            optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
    }
}