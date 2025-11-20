plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "org.jetbrains.kotlin.lorem-ipsum"
version = "1.0-SNAPSHOT"

kotlin {
    iosArm64()
    iosSimulatorArm64()

    swiftPMDependencies {
        iosDeploymentVersion.set("16.0")

        `package`(
            url = url("https://github.com/lukaskubanek/LoremIpsum.git"),
            version = from(libs.versions.spm.loremIpsum.get()),
            products = listOf(product("LoremIpsum")),
        )
    }

    sourceSets.all {
        languageSettings {
            optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
    }
}