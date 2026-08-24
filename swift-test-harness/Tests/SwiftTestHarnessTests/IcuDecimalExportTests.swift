import Testing
import IcuDecimal

private typealias DecimalOptions = options
private typealias DecimalInput = input
private typealias DecimalParts = parts
private typealias DecimalProvider = provider
private typealias WriteablePart = ExportedKotlinPackages.io.github.kotlinmania.writeable.Part

@Suite("IcuDecimal Swift Export Tests")
struct IcuDecimalExportTests {
    @Test("Decimal parts expose stable values")
    func decimalPartsExposeStableValues() {
        let cases: [(WriteablePart, String)] = [
            (DecimalParts.PLUS_SIGN, "plusSign"),
            (DecimalParts.MINUS_SIGN, "minusSign"),
            (DecimalParts.INTEGER, "integer"),
            (DecimalParts.FRACTION, "fraction"),
            (DecimalParts.GROUP, "group"),
            (DecimalParts.DECIMAL, "decimal"),
        ]

        for (part, value) in cases {
            #expect(part.category == "decimal")
            #expect(part.value == value)
        }
    }

    @Test("Part uses value equality")
    func partUsesValueEquality() {
        let expected = WriteablePart(category: "decimal", value: "integer")
        #expect(DecimalParts.INTEGER.equals(other: expected))
    }

    @Test("Grouping strategy export")
    func groupingStrategyExport() {
        #expect(
            DecimalOptions.GroupingStrategy.allCases.map(\.description) ==
            ["Auto", "Never", "Always", "Min2"]
        )
        #expect(DecimalOptions.GroupingStrategy.Min2.rawValue == 3)
        #expect(DecimalOptions.GroupingStrategy("Min2") == .Min2)
        #expect(DecimalOptions.GroupingStrategy("Bogus") == nil)
    }

    @Test("Provider value objects export")
    func providerValueObjectsExport() {
        #expect(DecimalProvider.DecimalSymbolsV1.shared.KEY == "decimal/symbols/v1")
        #expect(DecimalProvider.DecimalDigitsV1.shared.KEY == "decimal/digits/v1")
        #expect(DecimalProvider.DecimalDigitsV1.shared.ATTRIBUTES_DOMAIN == "numbering_system")
        #expect(DecimalProvider.MARKERS == ["decimal/symbols/v1", "decimal/digits/v1"])

        let groupingSizes = DecimalProvider.GroupingSizes(primary: 3, secondary: 2, minGrouping: 1)
        #expect(groupingSizes.primary == 3)
        #expect(groupingSizes.secondary == 2)
        #expect(groupingSizes.minGrouping == 1)

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
        #expect(strings.minusSignPrefix == "-")
        #expect(strings.minusSignSuffix == "")
        #expect(strings.plusSignPrefix == "+")
        #expect(strings.plusSignSuffix == "")
        #expect(strings.decimalSeparator == ".")
        #expect(strings.groupingSeparator == ",")
        #expect(strings.numsys == "latn")

        let symbols = DecimalProvider.DecimalSymbols(strings: strings, groupingSizes: groupingSizes)
        #expect(symbols.decimalSeparator() == ".")
        #expect(symbols.groupingSeparator() == ",")
        #expect(symbols.numsys() == "latn")
        #expect(symbols.minusSignAffixes().prefix == "-")
        #expect(symbols.minusSignAffixes().suffix == "")
        #expect(symbols.plusSignAffixes().prefix == "+")
        #expect(symbols.plusSignAffixes().suffix == "")
        #expect(symbols.groupingSizes.primary == 3)
        #expect(symbols.groupingSizes.secondary == 2)
        #expect(symbols.groupingSizes.minGrouping == 1)

        let testingSymbols = DecimalProvider.DecimalSymbols.Companion.shared.newEnForTesting()
        #expect(testingSymbols.decimalSeparator() == ".")
        #expect(testingSymbols.groupingSeparator() == ",")
        #expect(testingSymbols.numsys() == "latn")
        #expect(testingSymbols.groupingSizes.primary == 3)
        #expect(testingSymbols.groupingSizes.secondary == 3)
        #expect(testingSymbols.groupingSizes.minGrouping == 1)
    }

    @Test("Decimal formatter export")
    func decimalFormatterExport() {
        let formatter = DecimalFormatter.Companion.shared.tryNew(locale: "en-US")
        let grouped = DecimalInput.Decimal.Companion.shared.from(value: Int64(1_234_567))
        #expect(formatter.format(value: grouped).asString() == "1,234,567")

        let fractional = DecimalInput.Decimal.Companion.shared.from(value: Int64(200_050))
        fractional.multiplyPow10(power: Int32(-2))
        #expect(formatter.format(value: fractional).asString() == "2,000.50")

        let min2 = DecimalFormatter.Companion.shared.tryNewWithGroupingStrategy(
            locale: "en-US",
            groupingStrategy: DecimalOptions.GroupingStrategy.Min2
        )
        #expect(
            min2.format(value: DecimalInput.Decimal.Companion.shared.from(value: Int64(1_000))).asString() ==
            "1000"
        )
        #expect(
            min2.format(value: DecimalInput.Decimal.Companion.shared.from(value: Int64(10_000))).asString() ==
            "10,000"
        )
    }
}
