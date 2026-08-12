// Writeable part annotation value used by decimal part constants.
package io.github.kotlinmania.writeable

/**
 * Annotation metadata for a segment of formatted output.
 *
 * Producers conventionally use [category] to identify the formatting logic
 * and [value] for the semantic meaning of the annotated segment.
 */
data class Part(
    val category: String,
    val value: String,
) {
    companion object {
        /**
         * A part that annotates error segments in writeable output.
         */
        val ERROR: Part =
            Part(
                category = "writeable",
                value = "error",
            )
    }
}
