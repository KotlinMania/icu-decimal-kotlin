// swift-tools-version: 6.0
import PackageDescription

let package = Package(
    name: "SwiftTestHarness",
    platforms: [.macOS("15.0")],
    dependencies: [
        .package(name: "IcuDecimal", path: "../build/SPMPackage/macosArm64/Debug")
    ],
    targets: [
        .testTarget(
            name: "SwiftTestHarnessTests",
            dependencies: [
                .product(name: "IcuDecimalLibrary", package: "IcuDecimal")
            ],
            linkerSettings: [
                .unsafeFlags([
                    "-L", "../build/swift-test",
                    "-lIcuDecimal",
                ]),
            ]
        ),
    ]
)