// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_google-maps",
  platforms: [
    .iOS("16.0")
  ],
  products: [
    .library(
      name: "_google-maps",
      type: .none,
      targets: ["_google-maps"]
    )
  ],
  dependencies: [
    .package(
      url: "https://github.com/googlemaps/ios-maps-sdk",
      from: "10.3.0"
    )
  ],
  targets: [
    .target(
      name: "_google-maps",
      dependencies: [
        .product(
          name: "GoogleMaps",
          package: "ios-maps-sdk"
        )
      ]
    )
  ]
)
