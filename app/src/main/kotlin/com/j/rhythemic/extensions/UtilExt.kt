/*
 * Rhythemic Utility Module
 *
 * Internal helper functions
 * Signature: Rhythemic::UTILITY::V1
 */

package com.j.rhythemic.extensions

fun <T> tryOrNull(block: () -> T): T? =
    try {
        block()
    } catch (e: Exception) {
        null
    }
