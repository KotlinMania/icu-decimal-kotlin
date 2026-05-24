import XCTest
import IcuDecimal

private typealias DecimalOptions = options
private typealias DecimalParts = parts
private typealias DecimalProvider = provider
private typealias WriteablePart = ExportedKotlinPackages.io.github.kotlinmania.writeable.Part

final class IcuDecimalExportTests: XCTestCase {
    func testDecimalPartsExposeStableValues() {
        let cases: [(WriteablePart, String)] = [
            (DecimalParts.PLUS_SIGN, "plusSign"),
            (DecimalParts.MINUS_SIGN, "minusSign"),
            (DecimalParts.INTEGER, "integer"),
            (DecimalParts.FRACTION, "fraction"),
            (DecimalParts.GROUP, "group"),
            (DecimalParts.DECIMAL, "decimal"),
        ]

        for (part, value) in cases {
            XCTAssertEqual(part.category, "decimal")
            XCTAssertEqual(part.value, value)
        }
    }

    func testPartUsesValueEquality() {
        let expected = WriteablePart(category: "decimal", value: "integer")
        XCTAssertTrue(DecimalParts.INTEGER.equals(other: expected))
    }

    func testGroupingStrategyExport() {
        XCTAssertEqual(
            DecimalOptions.GroupingStrategy.allCases.map(\.description),
            ["Auto", "Never", "Always", "Min2"]
        )
        XCTAssertEqual(DecimalOptions.GroupingStrategy.Min2.rawValue, 3)
        XCTAssertEqual(DecimalOptions.GroupingStrategy("Min2"), .Min2)
        XCTAssertNil(DecimalOptions.GroupingStrategy("Bogus"))
    }

    func testProviderValueObjectsExport() {
        XCTAssertEqual(DecimalProvider.DecimalSymbolsV1.shared.KEY, "decimal/symbols/v1")
        XCTAssertEqual(DecimalProvider.DecimalDigitsV1.shared.KEY, "decimal/digits/v1")
        XCTAssertEqual(DecimalProvider.DecimalDigitsV1.shared.ATTRIBUTES_DOMAIN, "numbering_system")
        XCTAssertEqual(DecimalProvider.MARKERS, ["decimal/symbols/v1", "decimal/digits/v1"])

        let groupingSizes = DecimalProvider.GroupingSizes(primary: 3, secondary: 2, minGrouping: 1)
        XCTAssertEqual(groupingSizes.primary, 3)
        XCTAssertEqual(groupingSizes.secondary, 2)
        XCTAssertEqual(groupingSizes.minGrouping, 1)

        let builder = DecimalProvider.DecimalSymbolStrsBuilder(
            minusSignPrefix: "-",
            minusSignSuffix: "",
            plusSignPrefix: "+",
            plusSignSuffix: "",
            decimalSeparator: ".",
            groupingSeparator: ",",
            numsys: "latn"
        )
        let strings = builder.build()
        XCTAssertEqual(strings.minusSignPrefix, "-")
        XCTAssertEqual(strings.minusSignSuffix, "")
        XCTAssertEqual(strings.plusSignPrefix, "+")
        XCTAssertEqual(strings.plusSignSuffix, "")
        XCTAssertEqual(strings.decimalSeparator, ".")
        XCTAssertEqual(strings.groupingSeparator, ",")
        XCTAssertEqual(strings.numsys, "latn")

        let symbols = DecimalProvider.DecimalSymbols(strings: strings, groupingSizes: groupingSizes)
        XCTAssertEqual(symbols.decimalSeparator(), ".")
        XCTAssertEqual(symbols.groupingSeparator(), ",")
        XCTAssertEqual(symbols.numsys(), "latn")
        XCTAssertEqual(symbols.minusSignAffixes().prefix, "-")
        XCTAssertEqual(symbols.minusSignAffixes().suffix, "")
        XCTAssertEqual(symbols.plusSignAffixes().prefix, "+")
        XCTAssertEqual(symbols.plusSignAffixes().suffix, "")
        XCTAssertEqual(symbols.groupingSizes.primary, 3)
        XCTAssertEqual(symbols.groupingSizes.secondary, 2)
        XCTAssertEqual(symbols.groupingSizes.minGrouping, 1)

        let testingSymbols = DecimalProvider.DecimalSymbols.Companion.shared.newEnForTesting()
        XCTAssertEqual(testingSymbols.decimalSeparator(), ".")
        XCTAssertEqual(testingSymbols.groupingSeparator(), ",")
        XCTAssertEqual(testingSymbols.numsys(), "latn")
        XCTAssertEqual(testingSymbols.groupingSizes.primary, 3)
        XCTAssertEqual(testingSymbols.groupingSizes.secondary, 3)
        XCTAssertEqual(testingSymbols.groupingSizes.minGrouping, 1)
    }
}
