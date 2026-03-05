@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "org.jetbrains.kotlin.lorem-ipsum"
version = "1.0-SNAPSHOT"

kotlin {
    iosArm64()
    iosSimulatorArm64()

    swiftPMDependencies {
        iosMinimumDeploymentTarget.set("16.0")

        swiftPackage(
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