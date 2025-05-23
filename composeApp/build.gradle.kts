plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinCocoapods)
}

group = "org.jetbrains.kotlin.compose.sample"
version = "1.0-SNAPSHOT"

kotlin {
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Kotlin sample project with CocoaPods Compose dependencies"
        homepage = "https://github.com/Kotlin/kotlin-with-cocoapods-compose-sample"

        podfile = project.file("../iosApp/Podfile")

        ios.deploymentTarget = "16.6"

        /*
         * https://youtrack.jetbrains.com/issue/KT-41830/
         * Only link against pods the library (lorem-ipsum and google-maps) depends on.
         */
        pod("LoremIpsum") {
            version = libs.versions.cocoapods.loremIpsum.get()
            linkOnly = true
        }

        pod("GoogleMaps") {
            version = libs.versions.cocoapods.googleMaps.get()
            linkOnly = true
        }

        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
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
}


