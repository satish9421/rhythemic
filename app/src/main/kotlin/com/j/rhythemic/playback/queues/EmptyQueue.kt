/*
 * ╭────────────────────────────────────────────╮
 * │            Rhythemic Core Engine              │
 * │--------------------------------------------│
 * │  Handles playback, audio pipeline & logic  │
 * │                                            │
 * │  Signature: Rhythemic::CORE::ENGINE::V1       │
 * ╰────────────────────────────────────────────╯
 */

package com.j.rhythemic.playback.queues

import androidx.media3.common.MediaItem
import com.j.rhythemic.models.MediaMetadata

object EmptyQueue : Queue {
    override val preloadItem: MediaMetadata? = null

    override suspend fun getInitialStatus() = Queue.Status(null, emptyList(), -1)

    override fun hasNextPage() = false

    override suspend fun nextPage() = emptyList<MediaItem>()
}
