plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "org.jetbrains.kotlin.google-maps"
version = "1.0-SNAPSHOT"

kotlin {
    iosArm64()
    iosSimulatorArm64()

    swiftPMDependencies {
        iosMinimumDeploymentTarget = "16.0"

        // Google Maps
        swiftPackage(
            url = "https://github.com/googlemaps/ios-maps-sdk",
            version = "10.3.0",
            products = listOf("GoogleMaps"),
            importedClangModules = listOf("GoogleMaps"),
        )
    }

    sourceSets.all {
        languageSettings {
            optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
    }

    compilerOptions {
        optIn.add("kotlinx.cinterop.ExperimentalForeignApi")
    }
}