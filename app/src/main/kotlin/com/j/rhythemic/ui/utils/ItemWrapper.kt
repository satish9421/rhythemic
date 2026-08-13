/*
 * Rhythemic Utility Module
 *
 * Internal helper functions
 * Signature: Rhythemic::UTILITY::V1
 */

package com.j.rhythemic.ui.utils

import androidx.compose.runtime.mutableStateOf

class ItemWrapper<T>(
    val item: T,
) {
    private val _isSelected = mutableStateOf(true)

    var isSelected: Boolean
        get() = _isSelected.value
        set(value) {
            _isSelected.value = value
        }
}
