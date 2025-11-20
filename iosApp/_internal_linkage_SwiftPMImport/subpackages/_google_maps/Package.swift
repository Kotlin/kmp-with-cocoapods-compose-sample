// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_google_maps",
  platforms: [
    .iOS("16.0"),
  ],
  products: [
      .library(
          name: "_google_maps",
          type: .none,
          targets: ["_google_maps"]
      ),
  ],
  dependencies: [
    .package(
      url: "https://github.com/googlemaps/ios-maps-sdk.git",
      exact: "10.6.0",
    ),
  ],
  targets: [
    .target(
      name: "_google_maps",
      dependencies: [
        .product(
          name: "GoogleMaps",
          package: "ios-maps-sdk",
        ),
      ]
    ),
  ]
)
