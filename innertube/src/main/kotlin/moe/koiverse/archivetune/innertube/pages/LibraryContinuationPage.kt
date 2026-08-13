/*
 * Rhythemic Data Layer
 *
 * Handles data, network & storage
 * Signature: Rhythemic::DATA::CORE::V1
 */

package com.j.rhythemic.innertube.pages

import com.j.rhythemic.innertube.models.YTItem

data class LibraryContinuationPage(
    val items: List<YTItem>,
    val continuation: String?,
)
