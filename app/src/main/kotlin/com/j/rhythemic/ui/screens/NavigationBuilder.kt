/*
 * ╭────────────────────────────────────────────╮
 * │             Rhythemic UI System               │
 * │--------------------------------------------│
 * │  Crafted for expressive music experience   │
 * │                                            │
 * │  Signature: Rhythemic::UI::EXPRESSIVE::V1     │
 * ╰────────────────────────────────────────────╯
 */

package com.j.rhythemic.ui.screens

import android.net.Uri
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.j.rhythemic.LocalAnimatedVisibilityScope
import com.j.rhythemic.R
import com.j.rhythemic.constants.DarkModeKey
import com.j.rhythemic.constants.PureBlackKey
import com.j.rhythemic.ui.component.BottomSheet
import com.j.rhythemic.ui.component.BottomSheetMenu
import com.j.rhythemic.ui.component.LocalMenuState
import com.j.rhythemic.ui.component.rememberBottomSheetState
import com.j.rhythemic.ui.screens.BrowseScreen
import com.j.rhythemic.ui.screens.artist.ArtistAlbumsScreen
import com.j.rhythemic.ui.screens.artist.ArtistItemsScreen
import com.j.rhythemic.ui.screens.artist.ArtistScreen
import com.j.rhythemic.ui.screens.artist.ArtistSongsScreen
import com.j.rhythemic.ui.screens.library.LibraryScreen
import com.j.rhythemic.ui.screens.playlist.AutoPlaylistScreen
import com.j.rhythemic.ui.screens.playlist.LocalPlaylistScreen
import com.j.rhythemic.ui.screens.playlist.OnlinePlaylistScreen
import com.j.rhythemic.ui.screens.playlist.TopPlaylistScreen
import com.j.rhythemic.ui.screens.playlist.CachePlaylistScreen
import com.j.rhythemic.ui.screens.search.OnlineSearchResult
import com.j.rhythemic.ui.screens.search.SearchScreen
import com.j.rhythemic.ui.screens.settings.AboutScreen
import com.j.rhythemic.ui.screens.settings.AccountSettings
import com.j.rhythemic.ui.screens.settings.AppearanceSettings
import com.j.rhythemic.ui.screens.settings.CustomizeBackground
import com.j.rhythemic.ui.screens.settings.BackupAndRestore
import com.j.rhythemic.ui.screens.settings.ChangelogScreen
import com.j.rhythemic.ui.screens.settings.ContentSettings
import com.j.rhythemic.ui.screens.settings.DarkMode
import com.j.rhythemic.ui.screens.settings.DiscordLoginScreen
import com.j.rhythemic.ui.screens.settings.DiscordSettings
import com.j.rhythemic.ui.screens.settings.DebugSettings
import com.j.rhythemic.ui.screens.settings.IntegrationScreen
import com.j.rhythemic.ui.screens.settings.LastFMSettings
import com.j.rhythemic.ui.screens.settings.MusicTogetherScreen
import com.j.rhythemic.ui.screens.settings.PalettePickerScreen
import com.j.rhythemic.ui.screens.settings.PlayerSettings
import com.j.rhythemic.ui.screens.settings.PoTokenScreen
import com.j.rhythemic.ui.screens.settings.PrivacySettings
import com.j.rhythemic.ui.screens.settings.SettingsScreen
import com.j.rhythemic.ui.screens.settings.StorageSettings
import com.j.rhythemic.ui.screens.settings.ThemeCreatorScreen
import com.j.rhythemic.ui.screens.settings.UpdateScreen
import com.j.rhythemic.ui.screens.musicrecognition.MusicRecognitionRoute
import com.j.rhythemic.ui.screens.musicrecognition.MusicRecognitionScreen
import com.j.rhythemic.ui.utils.ShowMediaInfo
import com.j.rhythemic.utils.rememberEnumPreference
import com.j.rhythemic.utils.rememberPreference

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.navigationBuilder(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    latestVersionName: String,
) {
    composable(Screens.Home.route) {
        CompositionLocalProvider(
            LocalAnimatedVisibilityScope provides this@composable
        ) {
            HomeScreen(navController)
        }
    }
    composable(
        Screens.Library.route,
    ) {
        LibraryScreen(navController)
    }
    
    // ----------------------------------------------------
    // FIXED: Passed pureBlack to SearchScreen
    // ----------------------------------------------------
    composable(Screens.Search.route) {
        val (pureBlackEnabled) = rememberPreference(PureBlackKey, defaultValue = false)
        val darkTheme by rememberEnumPreference(DarkModeKey, defaultValue = DarkMode.AUTO)
        val isSystemInDarkTheme = isSystemInDarkTheme()
        val pureBlack = pureBlackEnabled && (if (darkTheme == DarkMode.AUTO) isSystemInDarkTheme else darkTheme == DarkMode.ON)
        
        SearchScreen(navController = navController, pureBlack = pureBlack)
    }
    
    composable("history") {
        HistoryScreen(navController)
    }
    composable("stats") {
        StatsScreen(navController)
    }
    composable("year_in_music") {
        YearInMusicScreen(navController)
    }
    composable(MusicRecognitionRoute) {
        MusicRecognitionScreen(navController)
    }
    composable(Screens.MoodAndGenres.route) {
        MoodAndGenresScreen(navController)
    }
    composable("account") {
        AccountScreen(navController, scrollBehavior)
    }
    composable("new_release") {
        NewReleaseScreen(navController, scrollBehavior)
    }
    composable("charts_screen") {
       ChartsScreen(navController)
    }
    composable(
        route = "browse/{browseId}",
        arguments = listOf(
            navArgument("browseId") {
                type = NavType.StringType
            }
        )
    ) {
        BrowseScreen(
            navController,
            scrollBehavior,
            it.arguments?.getString("browseId")
        )
    }
    composable(
        route = "search/{query}",
        arguments =
        listOf(
            navArgument("query") {
                type = NavType.StringType
            },
        ),
        enterTransition = {
            fadeIn(tween(250))
        },
        exitTransition = {
            if (targetState.destination.route?.startsWith("search/") == true) {
                fadeOut(tween(200))
            } else {
                fadeOut(tween(200)) + slideOutHorizontally { -it / 2 }
            }
        },
        popEnterTransition = {
            if (initialState.destination.route?.startsWith("search/") == true) {
                fadeIn(tween(250))
            } else {
                fadeIn(tween(250)) + slideInHorizontally { -it / 2 }
            }
        },
        popExitTransition = {
            fadeOut(tween(200))
        },
    ) {
        // ----------------------------------------------------
        // FIXED: Passed pureBlack to OnlineSearchResult
        // ----------------------------------------------------
        val (pureBlackEnabled) = rememberPreference(PureBlackKey, defaultValue = false)
        val darkTheme by rememberEnumPreference(DarkModeKey, defaultValue = DarkMode.AUTO)
        val isSystemInDarkTheme = isSystemInDarkTheme()
        val pureBlack = pureBlackEnabled && (if (darkTheme == DarkMode.AUTO) isSystemInDarkTheme else darkTheme == DarkMode.ON)
        
        OnlineSearchResult(navController = navController, pureBlack = pureBlack)
    }
    composable(
        route = "album/{albumId}",
        arguments =
        listOf(
            navArgument("albumId") {
                type = NavType.StringType
            },
        ),
    ) {
        AlbumScreen(navController, scrollBehavior)
    }
    composable(
        route = "artist/{artistId}",
        arguments =
        listOf(
            navArgument("artistId") {
                type = NavType.StringType
            },
        ),
    ) {
        ArtistScreen(navController, scrollBehavior)
    }
    composable(
        route = "artist/{artistId}/songs",
        arguments =
        listOf(
            navArgument("artistId") {
                type = NavType.StringType
            },
        ),
    ) {
        ArtistSongsScreen(navController, scrollBehavior)
    }
    composable(
        route = "artist/{artistId}/albums",
        arguments = listOf(
            navArgument("artistId") {
                type = NavType.StringType
            }
        )
    ) {
        ArtistAlbumsScreen(navController, scrollBehavior)
    }
    composable(
        route = "artist/{artistId}/items?browseId={browseId}&params={params}",
        arguments =
        listOf(
            navArgument("artistId") {
                type = NavType.StringType
            },
            navArgument("browseId") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("params") {
                type = NavType.StringType
                nullable = true
            },
        ),
    ) {
        ArtistItemsScreen(navController, scrollBehavior)
    }
    composable(
        route = "online_playlist/{playlistId}",
        arguments =
        listOf(
            navArgument("playlistId") {
                type = NavType.StringType
            },
        ),
    ) {
        OnlinePlaylistScreen(navController, scrollBehavior)
    }
    composable(
        route = "local_playlist/{playlistId}",
        arguments =
        listOf(
            navArgument("playlistId") {
                type = NavType.StringType
            },
        ),
    ) {
        LocalPlaylistScreen(navController, scrollBehavior)
    }
    composable(
        route = "auto_playlist/{playlist}",
        arguments =
        listOf(
            navArgument("playlist") {
                type = NavType.StringType
            },
        ),
    ) {
        AutoPlaylistScreen(navController, scrollBehavior)
    }
    composable(
        route = "cache_playlist/{playlist}",
        arguments =
            listOf(
                navArgument("playlist") {
                    type = NavType.StringType
            },
        ),
    ) {
        CachePlaylistScreen(navController, scrollBehavior)
    }
    composable(
        route = "top_playlist/{top}",
        arguments =
        listOf(
            navArgument("top") {
                type = NavType.StringType
            },
        ),
    ) {
        TopPlaylistScreen(navController, scrollBehavior)
    }
    composable(
        route = "youtube_browse/{browseId}?params={params}",
        arguments =
        listOf(
            navArgument("browseId") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("params") {
                type = NavType.StringType
                nullable = true
            },
        ),
    ) {
        YouTubeBrowseScreen(navController)
    }
    composable("settings") {
        SettingsScreen(navController, scrollBehavior, latestVersionName)
    }
    composable("settings/appearance") {
        AppearanceSettings(navController, scrollBehavior)
    }
    composable("settings/appearance/palette_picker") {
        PalettePickerScreen(navController)
    }
    composable("settings/appearance/theme_creator") {
        ThemeCreatorScreen(navController)
    }
    composable("settings/content") {
        ContentSettings(navController, scrollBehavior)
    }
    composable("settings/player") {
        PlayerSettings(navController, scrollBehavior)
    }
    composable("settings/storage") {
        StorageSettings(navController, scrollBehavior)
    }
    composable("settings/privacy") {
        PrivacySettings(navController, scrollBehavior)
    }
    composable("settings/backup_restore") {
        BackupAndRestore(navController, scrollBehavior)
    }
    composable("settings/discord") {
        DiscordSettings(navController, scrollBehavior)
    }
    composable("settings/integration") {
        IntegrationScreen(navController, scrollBehavior)
    }
    composable("settings/music_together") {
        MusicTogetherScreen(navController, scrollBehavior)
    }
    composable("settings/lastfm") {
        LastFMSettings(navController, scrollBehavior)
    }
    composable("settings/discord/experimental") {
        com.j.rhythemic.ui.screens.settings.DiscordExperimental(navController)
    }
    composable("settings/misc") {
        DebugSettings(navController)
    }
    composable("settings/update") {
        UpdateScreen(navController, scrollBehavior)
    }
    composable("settings/changelog") {
        ChangelogScreen(navController, scrollBehavior)
    }
    composable("settings/discord/login") {
        DiscordLoginScreen(navController)
    }
    composable("settings/about") {
        AboutScreen(navController, scrollBehavior)
    }
    composable("settings/po_token") {
        PoTokenScreen(navController, scrollBehavior)
    }
    composable("customize_background") {
        CustomizeBackground(navController)
    }
    composable(
        route = "$LOGIN_ROUTE?$LOGIN_URL_ARGUMENT={$LOGIN_URL_ARGUMENT}",
        arguments = listOf(
            navArgument(LOGIN_URL_ARGUMENT) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) { backStackEntry ->
        LoginScreen(
            navController,
            startUrl = backStackEntry.arguments?.getString(LOGIN_URL_ARGUMENT)?.let(Uri::decode)
        )
    }
}
