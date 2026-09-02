# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 7/8 (87.5%)
- **Function parity:** 19/22 matched (target 62) — 86.4%
- **Class/type parity:** 9/10 matched (target 22) — 90.0%
- **Combined symbol parity:** 28/32 matched (target 84) — 87.5%
- **Average inline-code cosine:** 0.40 (function body across 7 matched files)
- **Average documentation cosine:** 0.64 (doc text across 7 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 5 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. icu_decimal.grouper

- **Target:** `grouper.Grouper [PROVENANCE-FALLBACK]`
- **Similarity:** 0.41
- **Dependents:** 1
- **Priority Score:** 1020505.9
- **Functions:** 2/3 matched (target 4)
- **Missing functions:** `load`
- **Types:** 1/2 matched
- **Missing types:** `Provider`
- **Tests:** 1/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `grouper.rs` vs expected `grouper.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:grouper.rs` vs expected `grouper.rs`
- **Proposed provenance header:** `// port-lint: source grouper.rs` (current: `// port-lint: source grouper.rs`)
- **Proposed provenance header:** `// port-lint: tests grouper.rs` (current: `// port-lint: tests grouper.rs`)
- **Lint issues:** 2

### 2. icu_decimal.options

- **Target:** `options.Options [PROVENANCE-FALLBACK]`
- **Similarity:** 0.90
- **Dependents:** 1
- **Priority Score:** 1000300.9
- **Functions:** 1/1 matched (target 2)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `options.rs` vs expected `options.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:options.rs` vs expected `options.rs`
- **Proposed provenance header:** `// port-lint: source options.rs` (current: `// port-lint: source options.rs`)
- **Proposed provenance header:** `// port-lint: tests options.rs` (current: `// port-lint: tests options.rs`)
- **Lint issues:** 2

### 3. icu_decimal.provider

- **Target:** `provider.Provider [PROVENANCE-FALLBACK]`
- **Similarity:** 0.74
- **Dependents:** 0
- **Priority Score:** 1102.6
- **Functions:** 7/7 matched (target 8)
- **Missing functions:** _none_
- **Types:** 4/4 matched (target 9)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `provider.rs` vs expected `provider.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:provider.rs` vs expected `provider.rs`
- **Proposed provenance header:** `// port-lint: source provider.rs` (current: `// port-lint: source provider.rs`)
- **Proposed provenance header:** `// port-lint: tests provider.rs` (current: `// port-lint: tests provider.rs`)
- **Lint issues:** 2

### 4. icu_decimal.lib

- **Target:** `icudecimal.DecimalFormatter [PROVENANCE-FALLBACK]`
- **Similarity:** 0.56
- **Dependents:** 0
- **Priority Score:** 704.4
- **Functions:** 6/6 matched (target 35)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 4)
- **Missing types:** _none_
- **Tests:** 1/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `lib.rs` vs expected `lib.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:lib.rs` vs expected `lib.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:lib.rs` vs expected `lib.rs`
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source lib.rs`)
- **Proposed provenance header:** `// port-lint: tests lib.rs` (current: `// port-lint: tests lib.rs`)
- **Proposed provenance header:** `// port-lint: tests lib.rs` (current: `// port-lint: tests lib.rs`)
- **Lint issues:** 3

### 5. icu_decimal.format

- **Target:** `icudecimal.FormattedDecimal [PROVENANCE-FALLBACK]`
- **Similarity:** 0.22
- **Dependents:** 0
- **Priority Score:** 407.8
- **Functions:** 3/3 matched (target 13)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 4)
- **Missing types:** _none_
- **Tests:** 1/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `format.rs` vs expected `format.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:format.rs` vs expected `format.rs`
- **Proposed provenance header:** `// port-lint: source format.rs` (current: `// port-lint: source format.rs`)
- **Proposed provenance header:** `// port-lint: tests format.rs` (current: `// port-lint: tests format.rs`)
- **Lint issues:** 2

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Matched

| Source | Target | Path |
|--------|--------|------|
| `icu_decimal.parts` | `parts.Parts` | `icu_decimal/src/parts` |
| `icu_decimal.size_test_macro` | `icudecimal.SizeTestMacro` | `icu_decimal/src/size_test_macro` |

