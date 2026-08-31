# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 7/7 (100.0%)
- **Function parity:** 19/20 matched (target 58) — 95.0%
- **Class/type parity:** 9/10 matched (target 21) — 90.0%
- **Combined symbol parity:** 28/30 matched (target 79) — 93.3%
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

### 1. grouper

- **Target:** `grouper.Grouper`
- **Similarity:** 0.41
- **Dependents:** 1
- **Priority Score:** 1020505.9
- **Functions:** 2/3 matched (target 4)
- **Missing functions:** `load`
- **Types:** 1/2 matched
- **Missing types:** `Provider`
- **Tests:** 1/1 matched

### 2. options

- **Target:** `options.Options`
- **Similarity:** 0.90
- **Dependents:** 1
- **Priority Score:** 1000300.9
- **Functions:** 1/1 matched (target 2)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_

### 3. provider

- **Target:** `provider.Provider`
- **Similarity:** 0.74
- **Dependents:** 0
- **Priority Score:** 1102.6
- **Functions:** 7/7 matched (target 8)
- **Missing functions:** _none_
- **Types:** 4/4 matched (target 9)
- **Missing types:** _none_

### 4. lib

- **Target:** `icudecimal.DecimalFormatter`
- **Similarity:** 0.56
- **Dependents:** 0
- **Priority Score:** 704.4
- **Functions:** 6/6 matched (target 31)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 5. format

- **Target:** `icudecimal.FormattedDecimal`
- **Similarity:** 0.22
- **Dependents:** 0
- **Priority Score:** 407.8
- **Functions:** 3/3 matched (target 13)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 4)
- **Missing types:** _none_
- **Tests:** 1/1 matched

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
| `parts` | `parts.Parts` | `parts` |
| `size_test_macro` | `icudecimal.SizeTestMacro` | `size_test_macro` |

