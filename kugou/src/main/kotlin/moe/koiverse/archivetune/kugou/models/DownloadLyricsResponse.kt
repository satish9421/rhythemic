/*
 * Rhythemic Data Layer
 *
 * Handles data, network & storage
 * Signature: Rhythemic::DATA::CORE::V1
 */

package com.j.rhythemic.kugou.models

import kotlinx.serialization.Serializable

@Serializable
data class DownloadLyricsResponse(
    val content: String,
)
