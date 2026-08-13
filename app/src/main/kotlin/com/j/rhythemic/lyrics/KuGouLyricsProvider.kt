/*
 * Rhythemic - Modern Music Player
 *
 * Copyright (c) 2026 JAY01-CYBER
 * Signature: Rhythemic::GENERAL::V1
 */

package com.j.rhythemic.lyrics

import android.content.Context
import com.j.rhythemic.kugou.KuGou
import com.j.rhythemic.constants.EnableKugouKey
import com.j.rhythemic.utils.dataStore
import com.j.rhythemic.utils.get

object KuGouLyricsProvider : LyricsProvider {
    override val name = "Kugou"
    override fun isEnabled(context: Context): Boolean =
        context.dataStore[EnableKugouKey] ?: true

    override suspend fun getLyrics(
        id: String,
        title: String,
        artist: String,
        album: String?,
        duration: Int,
    ): Result<String> = KuGou.getLyrics(title, artist, duration)

    override suspend fun getAllLyrics(
        id: String,
        title: String,
        artist: String,
        album: String?,
        duration: Int,
        callback: (String) -> Unit,
    ) {
        KuGou.getAllPossibleLyricsOptions(title, artist, duration, callback)
    }
}
