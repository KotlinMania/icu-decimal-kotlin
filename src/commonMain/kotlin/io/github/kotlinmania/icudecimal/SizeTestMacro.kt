// port-lint: source size_test_macro.rs
package io.github.kotlinmania.icudecimal

/**
 * Stack-size documentation carried over from upstream's size-test macro.
 *
 * Kotlin/Multiplatform does not expose Rust's stack layout checks, so this
 * file keeps the upstream size contract visible without turning it into a
 * misleading runtime assertion.
 */
internal const val DECIMAL_FORMATTER_STACK_SIZE_BYTES: Int = 96
