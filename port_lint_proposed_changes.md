# port-lint Proposed Changes

**Generated:** 2026-08-31
**Source:** tmp/icu_decimal/src
**Target:** src/commonMain/kotlin/io/github/kotlinmania/icudecimal

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/icudecimal/grouper/Grouper.kt` | `// port-lint: source icu_decimal/src/grouper.rs` | `// port-lint: source grouper.rs` | `grouper.rs` | `port-lint provenance header matched only after fallback normalization: 'icu_decimal/src/grouper.rs' vs expected 'grouper.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/icudecimal/grouper/GrouperTest.kt` | `// port-lint: tests icu_decimal/src/grouper.rs` | `// port-lint: tests grouper.rs` | `grouper.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:icu_decimal/src/grouper.rs' vs expected 'grouper.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/icudecimal/options/Options.kt` | `// port-lint: source icu_decimal/src/options.rs` | `// port-lint: source options.rs` | `options.rs` | `port-lint provenance header matched only after fallback normalization: 'icu_decimal/src/options.rs' vs expected 'options.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/icudecimal/options/OptionsTest.kt` | `// port-lint: tests icu_decimal/src/options.rs` | `// port-lint: tests options.rs` | `options.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:icu_decimal/src/options.rs' vs expected 'options.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/icudecimal/provider/Provider.kt` | `// port-lint: source icu_decimal/src/provider.rs` | `// port-lint: source provider.rs` | `provider.rs` | `port-lint provenance header matched only after fallback normalization: 'icu_decimal/src/provider.rs' vs expected 'provider.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/icudecimal/provider/ProviderTest.kt` | `// port-lint: tests icu_decimal/src/provider.rs` | `// port-lint: tests provider.rs` | `provider.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:icu_decimal/src/provider.rs' vs expected 'provider.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/icudecimal/DecimalFormatter.kt` | `// port-lint: source icu_decimal/src/lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'icu_decimal/src/lib.rs' vs expected 'lib.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/icudecimal/DecimalFormatterTest.kt` | `// port-lint: tests icu_decimal/src/lib.rs` | `// port-lint: tests lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:icu_decimal/src/lib.rs' vs expected 'lib.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/icudecimal/FormattedDecimal.kt` | `// port-lint: source icu_decimal/src/format.rs` | `// port-lint: source format.rs` | `format.rs` | `port-lint provenance header matched only after fallback normalization: 'icu_decimal/src/format.rs' vs expected 'format.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/icudecimal/FormatTest.kt` | `// port-lint: tests icu_decimal/src/format.rs` | `// port-lint: tests format.rs` | `format.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:icu_decimal/src/format.rs' vs expected 'format.rs'` |
