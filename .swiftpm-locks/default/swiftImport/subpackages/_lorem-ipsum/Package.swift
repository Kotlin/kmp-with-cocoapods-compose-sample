// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_lorem-ipsum",
  platforms: [
    .iOS("16.0")
  ],
  products: [
    .library(
      name: "_lorem-ipsum",
      type: .none,
      targets: ["_lorem-ipsum"]
    )
  ],
  dependencies: [
    .package(
      url: "https://github.com/lukaskubanek/LoremIpsum",
      from: "2.0.0"
    )
  ],
  targets: [
    .target(
      name: "_lorem-ipsum",
      dependencies: [
        .product(
          name: "LoremIpsum",
          package: "LoremIpsum"
        )
      ]
    )
  ]
)
