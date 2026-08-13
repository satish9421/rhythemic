/*
 * Rhythemic - Music Player
 *
 * Copyright (c) 2026 Satish Galande
 * Signature: RHYTHEMIC::GENERAL::V1
 */

package com.j.rhythemic.together

import kotlinx.serialization.json.Json

object TogetherJson {
    val json: Json =
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            encodeDefaults = true
            classDiscriminator = "type"
        }
}
