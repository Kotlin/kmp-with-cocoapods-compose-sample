// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_internal_linkage_SwiftPMImport",
  platforms: [
    .iOS("16.0"),
  ],
  products: [
      .library(
          name: "_internal_linkage_SwiftPMImport",
          type: .none,
          targets: ["_internal_linkage_SwiftPMImport"]
      ),
  ],
  dependencies: [
    .package(path: "subpackages/_lorem_ipsum"),
    .package(path: "subpackages/_google_maps"),
  ],
  targets: [
    .target(
      name: "_internal_linkage_SwiftPMImport",
      dependencies: [
        .product(name: "_lorem_ipsum", package: "_lorem_ipsum"),
        .product(name: "_google_maps", package: "_google_maps"),
      ]
    ),
  ]
)
