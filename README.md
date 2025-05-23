[![official project](http://jb.gg/badges/official.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
# Compose Multiplatform iOS Sample (with CocoaPods for Compose Integration)

Demonstrates integrating Compose Multiplatform UI into an iOS-only KMP project, where CocoaPods is used primarily to set up Compose for iOS.

## Core Idea

* **Shared UI for iOS**: Build your application's UI using Compose Multiplatform and run it on iOS.
* **CocoaPods for Compose**: Utilize CocoaPods to integrate the Compose Multiplatform framework into the iOS project.
* **KMP**: Share UI and potentially ViewModels/logic within a single Gradle module.

## Structure

* **`:composeApp`**: The Compose App Gradle module.
    * Contains all shared Compose UI code (`commonMain`).
    * Includes iOS-specific configurations and the entry point for Compose (`iosMain`).
    * `build.gradle.kts` declares Compose Multiplatform dependencies and configures CocoaPods for Compose integration.
* **`:lorem-ipsum`**: A sample library module.
    * Contains shared code.
    * Have a CocoaPods dependency.
* **`:google-maps`**: A sample google maps library module.
    * Contains shared code.
    * Have a GoogleMaps CocoaPods dependency.
* **`iosApp`**: Native iOS application project (Xcode).
    * Hosts the Compose UI via a `UIViewController`.
    * Consumes the framework produced by the `:composeApp` module.

## Key Setup

* **`:composeApp/build.gradle.kts`**:
    * Kotlin iOS target.
    * `cocoapods` block configured to build the Compose framework for iOS.
    * Compose Multiplatform dependencies.
* **`iosApp/ContentView.swift`**:
    * Calls the KMP entry point (e.g., `MainViewControllerKt.MainViewController()`) to embed the Compose UI.

## Running

1.  **Prerequisites**: Xcode, Intellij IDEA, CocoaPods.
2.  **Build**:
    * Sync Gradle in Intellij IDEA (for the `:composeApp` module).
    * In the `iosApp` directory, run `pod install`.
    * Open the generated `.xcworkspace` in Xcode and run on a simulator or device.
