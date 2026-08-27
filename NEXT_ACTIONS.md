# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 7/8 (87.5%)
- **Function parity:** 17/22 matched (target 65) — 77.3%
- **Class/type parity:** 9/10 matched (target 23) — 90.0%
- **Combined symbol parity:** 26/32 matched (target 88) — 81.2%
- **Average inline-code cosine:** 0.37 (function body across 6 matched files)
- **Average documentation cosine:** 0.64 (doc text across 6 matched files)
- **Cheat-zeroed Files:** 3
- **Critical Issues:** 5 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. icu_decimal.grouper

- **Target:** `grouper.Grouper`
- **Similarity:** 0.41
- **Dependents:** 1
- **Priority Score:** 1020505.9
- **Functions:** 2/3 matched (target 4)
- **Missing functions:** `load`
- **Types:** 1/2 matched
- **Missing types:** `Provider`
- **Tests:** 1/1 matched

### 2. icu_decimal.options

- **Target:** `options.Options`
- **Similarity:** 0.90
- **Dependents:** 1
- **Priority Score:** 1000300.9
- **Functions:** 1/1 matched (target 2)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_

### 3. icu_decimal.parts

- **Target:** `parts.Parts [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 1
- **Priority Score:** 1000010.0
- **Functions:** 0/0 matched (target 2)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 4. icu_decimal.format

- **Target:** `icudecimal.FormattedDecimal`
- **Similarity:** 0.17
- **Dependents:** 0
- **Priority Score:** 20408.3
- **Functions:** 1/3 matched (target 11)
- **Missing functions:** `get_affixes`, `write_to_parts`
- **Types:** 1/1 matched (target 4)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 5. icu_decimal.provider

- **Target:** `provider.Provider`
- **Similarity:** 0.74
- **Dependents:** 0
- **Priority Score:** 1102.6
- **Functions:** 7/7 matched (target 8)
- **Missing functions:** _none_
- **Types:** 4/4 matched (target 9)
- **Missing types:** _none_

### 6. icu_decimal.lib

- **Target:** `input.Decimal [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 710.0
- **Functions:** 6/6 matched (target 37)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 4)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 7. icu_decimal.size_test_macro

- **Target:** `icudecimal.SizeTestMacro [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 1)
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

