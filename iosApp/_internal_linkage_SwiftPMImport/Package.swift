// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_internal_linkage_SwiftPMImport",
  platforms: [
    .iOS("16.0")
  ],
  products: [
    .library(
      name: "_internal_linkage_SwiftPMImport",
      type: .none,
      targets: ["_internal_linkage_SwiftPMImport"]
    )
  ],
  dependencies: [
    .package(
      url: "https://github.com/googlemaps/ios-maps-sdk",
      from: "10.3.0",
    ),
    .package(
      url: "https://github.com/lukaskubanek/LoremIpsum",
      from: "2.0.0",
    ),
    .package(path: "subpackages/_lorem_ipsum"),
    .package(path: "subpackages/_google_maps")
  ],
  targets: [
    .target(
      name: "_internal_linkage_SwiftPMImport",
      dependencies: [
        .product(
          name: "GoogleMaps",
          package: "ios-maps-sdk",
        ),
        .product(
          name: "LoremIpsum",
          package: "LoremIpsum",
        ),
        .product(name: "_lorem_ipsum", package: "_lorem_ipsum"),
        .product(name: "_google_maps", package: "_google_maps")
      ]
    )
  ]
)
