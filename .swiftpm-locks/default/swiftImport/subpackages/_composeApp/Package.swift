// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_composeApp",
  platforms: [
    .iOS("16.0")
  ],
  products: [
    .library(
      name: "_composeApp",
      type: .none,
      targets: ["_composeApp"]
    )
  ],
  dependencies: [
    .package(
      url: "https://github.com/googlemaps/ios-maps-sdk",
      from: "10.3.0"
    ),
    .package(
      url: "https://github.com/lukaskubanek/LoremIpsum",
      from: "2.0.0"
    )
  ],
  targets: [
    .target(
      name: "_composeApp",
      dependencies: [
        .product(
          name: "GoogleMaps",
          package: "ios-maps-sdk"
        ),
        .product(
          name: "LoremIpsum",
          package: "LoremIpsum"
        )
      ]
    )
  ]
)
