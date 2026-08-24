# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 7/8 (87.5%)
- **Function parity:** 15/22 matched (target 62) — 68.2%
- **Class/type parity:** 9/10 matched (target 21) — 90.0%
- **Combined symbol parity:** 24/32 matched (target 83) — 75.0%
- **Average inline-code cosine:** 0.37 (function body across 7 matched files)
- **Average documentation cosine:** 0.62 (doc text across 7 matched files)
- **Cheat-zeroed Files:** 2
- **Critical Issues:** 5 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. grouper

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

### 2. options

- **Target:** `options.Options [PROVENANCE-FALLBACK]`
- **Similarity:** 0.90
- **Dependents:** 1
- **Priority Score:** 1000300.9
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `options.rs` vs expected `options.rs`
- **Proposed provenance header:** `// port-lint: source options.rs` (current: `// port-lint: source options.rs`)
- **Lint issues:** 1

### 3. parts

- **Target:** `parts.Parts [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 1
- **Priority Score:** 1000010.0
- **Functions:** 0/0 matched (target 2)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `parts.rs` vs expected `parts.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:parts.rs` vs expected `parts.rs`
- **Proposed provenance header:** `// port-lint: source parts.rs` (current: `// port-lint: source parts.rs`)
- **Proposed provenance header:** `// port-lint: tests parts.rs` (current: `// port-lint: tests parts.rs`)
- **Lint issues:** 2

### 4. format

- **Target:** `icudecimal.FormattedDecimal [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 30410.0
- **Functions:** 0/3 matched (target 10)
- **Missing functions:** `get_affixes`, `write_to_parts`, `test_es_mx`
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_
- **Tests:** 0/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `format.rs` vs expected `format.rs`
- **Proposed provenance header:** `// port-lint: source format.rs` (current: `// port-lint: source format.rs`)
- **Lint issues:** 1

### 5. lib

- **Target:** `input.Decimal [PROVENANCE-FALLBACK]`
- **Similarity:** 0.53
- **Dependents:** 0
- **Priority Score:** 10704.7
- **Functions:** 5/6 matched (target 36)
- **Missing functions:** `test_numbering_resolution_fallback`
- **Types:** 1/1 matched (target 4)
- **Missing types:** _none_
- **Tests:** 0/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `lib.rs` vs expected `lib.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `lib.rs` vs expected `lib.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:lib.rs` vs expected `lib.rs`
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source lib.rs`)
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source lib.rs`)
- **Proposed provenance header:** `// port-lint: tests lib.rs` (current: `// port-lint: tests lib.rs`)
- **Lint issues:** 3

### 6. provider

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

### 7. size_test_macro

- **Target:** `icudecimal.SizeTestMacro [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 1)
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `size_test_macro.rs` vs expected `size_test_macro.rs`
- **Proposed provenance header:** `// port-lint: source size_test_macro.rs` (current: `// port-lint: source size_test_macro.rs`)
- **Lint issues:** 1

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

