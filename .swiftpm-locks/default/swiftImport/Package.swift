// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "KotlinMultiplatformLinkedPackage",
  platforms: [
    .iOS("16.0")
  ],
  products: [
    .library(
      name: "KotlinMultiplatformLinkedPackage",
      type: .none,
      targets: ["KotlinMultiplatformLinkedPackage"]
    )
  ],
  dependencies: [
    .package(path: "subpackages/_composeApp"),
    .package(path: "subpackages/_google-maps"),
    .package(path: "subpackages/_google_maps"),
    .package(path: "subpackages/_lorem-ipsum"),
    .package(path: "subpackages/_lorem_ipsum")
  ],
  targets: [
    .target(
      name: "KotlinMultiplatformLinkedPackage",
      dependencies: [
        .product(name: "_composeApp", package: "_composeApp"),
        .product(name: "_google-maps", package: "_google-maps"),
        .product(name: "_google_maps", package: "_google_maps"),
        .product(name: "_lorem-ipsum", package: "_lorem-ipsum"),
        .product(name: "_lorem_ipsum", package: "_lorem_ipsum")
      ]
    )
  ]
)
