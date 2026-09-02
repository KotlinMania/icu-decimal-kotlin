# port-lint Proposed Changes

**Generated:** 2026-09-01
**Source:** tmp
**Target:** src/commonMain/kotlin/io/github/kotlinmania/icudecimal

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/icudecimal/grouper/Grouper.kt` | `// port-lint: source grouper.rs` | `// port-lint: source grouper.rs` | `grouper.rs` | `port-lint provenance header matched only after fallback normalization: 'grouper.rs' vs expected 'grouper.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/icudecimal/grouper/GrouperTest.kt` | `// port-lint: tests grouper.rs` | `// port-lint: tests grouper.rs` | `grouper.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:grouper.rs' vs expected 'grouper.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/icudecimal/options/Options.kt` | `// port-lint: source options.rs` | `// port-lint: source options.rs` | `options.rs` | `port-lint provenance header matched only after fallback normalization: 'options.rs' vs expected 'options.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/icudecimal/options/OptionsTest.kt` | `// port-lint: tests options.rs` | `// port-lint: tests options.rs` | `options.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:options.rs' vs expected 'options.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/icudecimal/provider/Provider.kt` | `// port-lint: source provider.rs` | `// port-lint: source provider.rs` | `provider.rs` | `port-lint provenance header matched only after fallback normalization: 'provider.rs' vs expected 'provider.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/icudecimal/provider/ProviderTest.kt` | `// port-lint: tests provider.rs` | `// port-lint: tests provider.rs` | `provider.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:provider.rs' vs expected 'provider.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/icudecimal/DecimalFormatter.kt` | `// port-lint: source lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'lib.rs' vs expected 'lib.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/icudecimal/DecimalFormatterTest.kt` | `// port-lint: tests lib.rs` | `// port-lint: tests lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:lib.rs' vs expected 'lib.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/icudecimal/FixedDecimalFormatBenchTest.kt` | `// port-lint: tests lib.rs` | `// port-lint: tests lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:lib.rs' vs expected 'lib.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/icudecimal/FormattedDecimal.kt` | `// port-lint: source format.rs` | `// port-lint: source format.rs` | `format.rs` | `port-lint provenance header matched only after fallback normalization: 'format.rs' vs expected 'format.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/icudecimal/FormatTest.kt` | `// port-lint: tests format.rs` | `// port-lint: tests format.rs` | `format.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:format.rs' vs expected 'format.rs'` |
