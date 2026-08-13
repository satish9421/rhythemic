/*
 * Rhythemic - Modern Music Player
 *
 * Copyright (c) 2026 JAY01-CYBER
 * Signature: Rhythemic::GENERAL::V1
 */

package com.j.rhythemic.lyrics

import android.content.Context
import com.j.rhythemic.lrclib.LrcLib
import com.j.rhythemic.constants.EnableLrcLibKey
import com.j.rhythemic.utils.dataStore
import com.j.rhythemic.utils.get

object LrcLibLyricsProvider : LyricsProvider {
    override val name = "LrcLib"

    override fun isEnabled(context: Context): Boolean = context.dataStore[EnableLrcLibKey] ?: true

    override suspend fun getLyrics(
        id: String,
        title: String,
        artist: String,
        album: String?,
        duration: Int,
    ): Result<String> = LrcLib.getLyrics(title, artist, duration)

    override suspend fun getAllLyrics(
        id: String,
        title: String,
        artist: String,
        album: String?,
        duration: Int,
        callback: (String) -> Unit,
    ) {
        LrcLib.getAllLyrics(title, artist, duration, null, callback)
    }
}
