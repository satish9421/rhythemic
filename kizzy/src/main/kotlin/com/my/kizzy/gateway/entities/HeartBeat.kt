/*
 * Rhythemic - Modern Music Player
 *
 * Copyright (c) 2026 JAY01-CYBER
 * Signature: Rhythemic::GENERAL::V1
 */

package com.my.kizzy.gateway.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Heartbeat(
    @SerialName("heartbeat_interval")
    val heartbeatInterval: Long,
)