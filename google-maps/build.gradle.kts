plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "org.jetbrains.kotlin.google-maps"
version = "1.0-SNAPSHOT"

kotlin {
    iosArm64()
    iosSimulatorArm64()

    swiftPMDependencies {
        iosDeploymentVersion.set("16.0")

        `package`(
            url = url("https://github.com/googlemaps/ios-maps-sdk.git"),
            version = exact(libs.versions.spm.googleMaps.get()),
            products = listOf(product("GoogleMaps")),
        )
    }

    sourceSets.all {
        languageSettings {
            optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
    }
}