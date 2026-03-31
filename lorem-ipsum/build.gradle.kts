plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "org.jetbrains.kotlin.lorem-ipsum"
version = "1.0-SNAPSHOT"

kotlin {
    iosArm64()
    iosSimulatorArm64()

    swiftPMDependencies {
        iosMinimumDeploymentTarget = "16.0"

        // LoremIpsum
        swiftPackage(
            url = "https://github.com/lukaskubanek/LoremIpsum",
            version = "2.0.0",
            products = listOf("LoremIpsum"),
            importedClangModules = listOf("LoremIpsum"),
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