// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_lorem_ipsum",
  platforms: [
    .iOS("16.0"),
  ],
  products: [
      .library(
          name: "_lorem_ipsum",
          type: .none,
          targets: ["_lorem_ipsum"]
      ),
  ],
  dependencies: [
    .package(
      url: "https://github.com/lukaskubanek/LoremIpsum.git",
      from: "2.0.1",
    ),
  ],
  targets: [
    .target(
      name: "_lorem_ipsum",
      dependencies: [
        .product(
          name: "LoremIpsum",
          package: "LoremIpsum",
        ),
      ]
    ),
  ]
)
