/*
 * Rhythemic Data Layer
 *
 * Handles data, network & storage
 * Signature: Rhythemic::DATA::CORE::V1
 */

package com.j.rhythemic.innertube.pages

import com.j.rhythemic.innertube.models.Album
import com.j.rhythemic.innertube.models.Artist
import com.j.rhythemic.innertube.models.BrowseEndpoint
import com.j.rhythemic.innertube.models.PlaylistPanelVideoRenderer
import com.j.rhythemic.innertube.models.SongItem
import com.j.rhythemic.innertube.models.WatchEndpoint
import com.j.rhythemic.innertube.models.oddElements
import com.j.rhythemic.innertube.models.splitBySeparator
import com.j.rhythemic.innertube.utils.parseTime

data class NextResult(
    val title: String? = null,
    val items: List<SongItem>,
    val currentIndex: Int? = null,
    val lyricsEndpoint: BrowseEndpoint? = null,
    val relatedEndpoint: BrowseEndpoint? = null,
    val continuation: String?,
    val endpoint: WatchEndpoint,
)

object NextPage {
    fun fromPlaylistPanelVideoRenderer(renderer: PlaylistPanelVideoRenderer): SongItem? {
        // Fallback for long and short bylines
        val byLineRuns = (renderer.longBylineText?.runs ?: renderer.shortBylineText?.runs)?.splitBySeparator() ?: emptyList()
        
        return SongItem(
            id = renderer.videoId ?: return null,
            title = renderer.title?.runs?.firstOrNull()?.text ?: return null,
            artists = byLineRuns.firstOrNull()?.oddElements()?.map {
                Artist(
                    name = it.text,
                    id = it.navigationEndpoint?.browseEndpoint?.browseId,
                )
            }.orEmpty(), // Safe fallback, no more strict returns!
            album = byLineRuns.getOrNull(1)?.firstOrNull()?.takeIf {
                it.navigationEndpoint?.browseEndpoint != null
            }?.let {
                Album(
                    name = it.text,
                    id = it.navigationEndpoint?.browseEndpoint?.browseId ?: "",
                )
            },
            duration = renderer.lengthText?.runs?.firstOrNull()?.text?.parseTime(),
            thumbnail = renderer.thumbnail.thumbnails.lastOrNull()?.url ?: return null,
            explicit = renderer.badges?.any {
                it.musicInlineBadgeRenderer?.icon?.iconType == "MUSIC_EXPLICIT_BADGE"
            } == true,
        )
    }
}
