/*
 * Rhythemic Data Layer
 *
 * Handles data, network & storage
 * Signature: Rhythemic::DATA::CORE::V1
 */

package com.j.rhythemic.innertube.pages

import com.j.rhythemic.innertube.models.Album
import com.j.rhythemic.innertube.models.AlbumItem
import com.j.rhythemic.innertube.models.Artist
import com.j.rhythemic.innertube.models.ArtistItem
import com.j.rhythemic.innertube.models.MusicResponsiveListItemRenderer
import com.j.rhythemic.innertube.models.MusicTwoRowItemRenderer
import com.j.rhythemic.innertube.models.PlaylistItem
import com.j.rhythemic.innertube.models.SongItem
import com.j.rhythemic.innertube.models.YTItem
import com.j.rhythemic.innertube.models.oddElements
import com.j.rhythemic.innertube.utils.parseTime

data class LibraryAlbumsPage(
    val albums: List<AlbumItem>,
    val continuation: String?,
) {
    companion object {
        fun fromMusicTwoRowItemRenderer(renderer: MusicTwoRowItemRenderer): AlbumItem? {
            val browseId = renderer.navigationEndpoint.browseEndpoint?.browseId ?: return null
            val playlistId = renderer.thumbnailOverlay?.musicItemThumbnailOverlayRenderer?.content
                ?.musicPlayButtonRenderer?.playNavigationEndpoint
                ?.watchPlaylistEndpoint?.playlistId
                ?: renderer.menu?.menuRenderer?.items?.firstOrNull()
                    ?.menuNavigationItemRenderer?.navigationEndpoint
                    ?.watchPlaylistEndpoint?.playlistId
                ?: browseId.removePrefix("MPREb_").let { "OLAK5uy_$it" }

            return AlbumItem(
                browseId = browseId,
                playlistId = playlistId,
                title = renderer.title.runs?.firstOrNull()?.text ?: return null,
                artists = null,
                year = renderer.subtitle?.runs?.lastOrNull()?.text?.toIntOrNull(),
                thumbnail = renderer.thumbnailRenderer.musicThumbnailRenderer?.getThumbnailUrl() ?: return null,
                explicit = renderer.subtitleBadges?.find {
                    it.musicInlineBadgeRenderer?.icon?.iconType == "MUSIC_EXPLICIT_BADGE"
                } != null
            )
        }
    }
}
