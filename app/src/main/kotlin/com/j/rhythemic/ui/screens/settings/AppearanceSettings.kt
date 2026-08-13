/*
 * ╭────────────────────────────────────────────╮
 * │             Rhythemic UI System               │
 * │--------------------------------------------│
 * │  Crafted for expressive music experience   │
 * │  Style: ANDROID 17 (Ultra-Rounded, M3)     │
 * ╰────────────────────────────────────────────╯
 */

package com.j.rhythemic.ui.screens.settings

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.j.rhythemic.LocalPlayerAwareWindowInsets
import com.j.rhythemic.R
import com.j.rhythemic.constants.ChipSortTypeKey
import com.j.rhythemic.constants.DarkModeKey
import com.j.rhythemic.constants.DefaultOpenTabKey
import com.j.rhythemic.constants.DynamicThemeKey
import com.j.rhythemic.constants.GridItemSize
import com.j.rhythemic.constants.GridItemsSizeKey
import com.j.rhythemic.constants.LibraryFilter
import com.j.rhythemic.constants.LyricsClickKey
import com.j.rhythemic.constants.LyricsScrollKey
import com.j.rhythemic.constants.LyricsTextPositionKey
import com.j.rhythemic.constants.PlayerDesignStyle
import com.j.rhythemic.constants.PlayerDesignStyleKey
import com.j.rhythemic.constants.MiniPlayerStyle
import com.j.rhythemic.constants.MiniPlayerStyleKey
import com.j.rhythemic.constants.PlayerBackgroundStyle
import com.j.rhythemic.constants.PlayerBackgroundStyleKey
import com.j.rhythemic.constants.MiniPlayerBackgroundStyleKey
import com.j.rhythemic.constants.PureBlackKey
import com.j.rhythemic.constants.RandomThemeOnStartupKey
import com.j.rhythemic.constants.UseSystemFontKey
import com.j.rhythemic.constants.PlayerButtonsStyle
import com.j.rhythemic.constants.PlayerButtonsStyleKey
import com.j.rhythemic.constants.LyricsAnimationStyleKey
import com.j.rhythemic.constants.LyricsAnimationStyle
import com.j.rhythemic.constants.LyricsTextSizeKey
import com.j.rhythemic.constants.LyricsLineSpacingKey
import com.j.rhythemic.constants.SliderStyle
import com.j.rhythemic.constants.SliderStyleKey
import com.j.rhythemic.constants.SlimNavBarKey
import com.j.rhythemic.constants.ShowLikedPlaylistKey
import com.j.rhythemic.constants.ShowDownloadedPlaylistKey
import com.j.rhythemic.constants.ShowHomeCategoryChipsKey
import com.j.rhythemic.constants.ShowTopPlaylistKey
import com.j.rhythemic.constants.ShowCachedPlaylistKey
import com.j.rhythemic.constants.ShowTagsInLibraryKey
import com.j.rhythemic.constants.SwipeThumbnailKey
import com.j.rhythemic.constants.SwipeSensitivityKey
import com.j.rhythemic.constants.SwipeToSongKey
import com.j.rhythemic.constants.HidePlayerThumbnailKey
import com.j.rhythemic.constants.RhythemicCanvasKey
import com.j.rhythemic.constants.ThumbnailCornerRadiusKey
import com.j.rhythemic.constants.CropThumbnailToSquareKey
import com.j.rhythemic.constants.DisableBlurKey
import com.j.rhythemic.constants.UseLyricsV2Key
import com.j.rhythemic.ui.component.DefaultDialog
import com.j.rhythemic.ui.component.EnumListPreference
import com.j.rhythemic.ui.component.ListPreference
import com.j.rhythemic.ui.component.IconButton
import com.j.rhythemic.ui.component.PreferenceEntry
import com.j.rhythemic.ui.component.PreferenceGroupTitle
import com.j.rhythemic.ui.component.SwitchPreference
import com.j.rhythemic.ui.component.ThumbnailCornerRadiusSelectorButton
import com.j.rhythemic.ui.player.StyledPlaybackSlider
import com.j.rhythemic.ui.utils.backToMain
import com.j.rhythemic.utils.rememberEnumPreference
import com.j.rhythemic.utils.rememberPreference
import kotlin.math.roundToInt
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceSettings(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val (dynamicTheme, onDynamicThemeChange) = rememberPreference(DynamicThemeKey, true)
    val (randomThemeOnStartup, onRandomThemeOnStartupChange) = rememberPreference(RandomThemeOnStartupKey, false)
    val (darkMode, onDarkModeChange) = rememberEnumPreference(DarkModeKey, DarkMode.AUTO)
    val (playerDesignStyle, onPlayerDesignStyleChange) = rememberEnumPreference(PlayerDesignStyleKey, PlayerDesignStyle.V4)
    val (miniPlayerStyle, onMiniPlayerStyleChange) = rememberEnumPreference(MiniPlayerStyleKey, MiniPlayerStyle.MODERN)
    val (useNewLibraryDesign, onUseNewLibraryDesignChange) = rememberPreference(com.j.rhythemic.constants.UseNewLibraryDesignKey, false)
    val (hidePlayerThumbnail, onHidePlayerThumbnailChange) = rememberPreference(HidePlayerThumbnailKey, false)
    val (archiveTuneCanvasEnabled, onRhythemicCanvasEnabledChange) = rememberPreference(RhythemicCanvasKey, false)
    val (thumbnailCornerRadius, onThumbnailCornerRadiusChange) = rememberPreference(ThumbnailCornerRadiusKey, 16f)
    val (cropThumbnailToSquare, onCropThumbnailToSquareChange) = rememberPreference(CropThumbnailToSquareKey, false)
    val (playerBackground, onPlayerBackgroundChange) = rememberEnumPreference(PlayerBackgroundStyleKey, PlayerBackgroundStyle.DEFAULT)
    
    val (miniPlayerBackground, onMiniPlayerBackgroundChange) = rememberEnumPreference(MiniPlayerBackgroundStyleKey, PlayerBackgroundStyle.DEFAULT)
    
    val (pureBlack, onPureBlackChange) = rememberPreference(PureBlackKey, false)
    val (disableBlur, onDisableBlurChange) = rememberPreference(DisableBlurKey, true)
    val (useSystemFont, onUseSystemFontChange) = rememberPreference(UseSystemFontKey, false)
    val (defaultOpenTab, onDefaultOpenTabChange) = rememberEnumPreference(DefaultOpenTabKey, NavigationTab.HOME)
    val (playerButtonsStyle, onPlayerButtonsStyleChange) = rememberEnumPreference(PlayerButtonsStyleKey, PlayerButtonsStyle.DEFAULT)
    val (lyricsPosition, onLyricsPositionChange) = rememberEnumPreference(LyricsTextPositionKey, LyricsPosition.LEFT)
    val (lyricsAnimation, onLyricsAnimationChange) = rememberEnumPreference<LyricsAnimationStyle>(LyricsAnimationStyleKey, LyricsAnimationStyle.APPLE)
    val (lyricsClick, onLyricsClickChange) = rememberPreference(LyricsClickKey, true)
    val (lyricsScroll, onLyricsScrollChange) = rememberPreference(LyricsScrollKey, true)
    val (lyricsTextSize, onLyricsTextSizeChange) = rememberPreference(LyricsTextSizeKey, 26f)
    val (lyricsLineSpacing, onLyricsLineSpacingChange) = rememberPreference(LyricsLineSpacingKey, 1.3f)
    val (useLyricsV2, onUseLyricsV2Change) = rememberPreference(UseLyricsV2Key, false)
    val (sliderStyle, onSliderStyleChange) = rememberEnumPreference(SliderStyleKey, SliderStyle.Standard)
    val (swipeThumbnail, onSwipeThumbnailChange) = rememberPreference(SwipeThumbnailKey, true)
    val (swipeSensitivity, onSwipeSensitivityChange) = rememberPreference(SwipeSensitivityKey, 0.73f)
    val (gridItemSize, onGridItemSizeChange) = rememberEnumPreference(GridItemsSizeKey, GridItemSize.SMALL)
    val (slimNav, onSlimNavChange) = rememberPreference(SlimNavBarKey, false)
    val (swipeToSong, onSwipeToSongChange) = rememberPreference(SwipeToSongKey, false)
    val (showLikedPlaylist, onShowLikedPlaylistChange) = rememberPreference(ShowLikedPlaylistKey, true)
    val (showDownloadedPlaylist, onShowDownloadedPlaylistChange) = rememberPreference(ShowDownloadedPlaylistKey, true)
    val (showTopPlaylist, onShowTopPlaylistChange) = rememberPreference(ShowTopPlaylistKey, true)
    val (showCachedPlaylist, onShowCachedPlaylistChange) = rememberPreference(ShowCachedPlaylistKey, true)
    val (showTagsInLibrary, onShowTagsInLibraryChange) = rememberPreference(ShowTagsInLibraryKey, true)
    val (showHomeCategoryChips, onShowHomeCategoryChipsChange) = rememberPreference(ShowHomeCategoryChipsKey, true)
    val (defaultChip, onDefaultChipChange) = rememberEnumPreference(ChipSortTypeKey, LibraryFilter.LIBRARY)

    val isSystemInDarkTheme = isSystemInDarkTheme()
    val useDarkTheme = remember(darkMode, isSystemInDarkTheme) {
        if (darkMode == DarkMode.AUTO) isSystemInDarkTheme else darkMode == DarkMode.ON
    }

    var showSliderOptionDialog by rememberSaveable { mutableStateOf(false) }

    if (showSliderOptionDialog) {
        val sliderStyles = remember {
            listOf(SliderStyle.Standard, SliderStyle.Wavy, SliderStyle.Thick, SliderStyle.Circular, SliderStyle.Simple)
        }
        DefaultDialog(
            buttons = {
                TextButton(onClick = { showSliderOptionDialog = false }) {
                    Text(text = stringResource(android.R.string.cancel))
                }
            },
            onDismiss = { showSliderOptionDialog = false }
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                sliderStyles.chunked(3).forEach { styleRow ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        styleRow.forEach { style ->
                            SliderStyleOptionCard(
                                sliderStyle = style,
                                selected = sliderStyle == style,
                                onClick = {
                                    onSliderStyleChange(style)
                                    showSliderOptionDialog = false
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        repeat(3 - styleRow.size) { Spacer(modifier = Modifier.weight(1f)) }
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(R.string.appearance), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(
                        onClick = navController::navigateUp,
                        onLongClick = navController::backToMain,
                    ) {
                        Icon(painterResource(R.drawable.arrow_back), contentDescription = null)
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .windowInsetsPadding(LocalPlayerAwareWindowInsets.current.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)),
            contentPadding = PaddingValues(bottom = 40.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                PreferenceGroupTitle(
                    title = stringResource(R.string.theme),
                    modifier = Modifier.padding(start = 24.dp, bottom = 4.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(Modifier.padding(vertical = 12.dp)) {
                        SwitchPreference(
                            title = { Text(stringResource(R.string.enable_dynamic_theme)) },
                            icon = { Icon(painterResource(R.drawable.palette), null) },
                            checked = dynamicTheme,
                            onCheckedChange = onDynamicThemeChange,
                        )

                        AnimatedVisibility(visible = !dynamicTheme || Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                            Column {
                                SwitchPreference(
                                    title = { Text(stringResource(R.string.random_theme_on_startup)) },
                                    description = stringResource(R.string.random_theme_on_startup_desc),
                                    icon = { Icon(painterResource(R.drawable.shuffle), null) },
                                    checked = randomThemeOnStartup,
                                    onCheckedChange = onRandomThemeOnStartupChange,
                                )
                                PreferenceEntry(
                                    title = { Text(stringResource(R.string.color_palette)) },
                                    description = stringResource(R.string.customize_theme_colors),
                                    icon = { Icon(painterResource(R.drawable.format_paint), null) },
                                    onClick = { navController.navigate("settings/appearance/palette_picker") }
                                )
                            }
                        }

                        EnumListPreference(
                            title = { Text(stringResource(R.string.dark_theme)) },
                            icon = { Icon(painterResource(R.drawable.dark_mode), null) },
                            selectedValue = darkMode,
                            onValueSelected = onDarkModeChange,
                            valueText = {
                                when (it) {
                                    DarkMode.ON -> stringResource(R.string.dark_theme_on)
                                    DarkMode.OFF -> stringResource(R.string.dark_theme_off)
                                    DarkMode.AUTO -> stringResource(R.string.dark_theme_follow_system)
                                }
                            },
                        )

                        AnimatedVisibility(useDarkTheme) {
                            SwitchPreference(
                                title = { Text(stringResource(R.string.pure_black)) },
                                icon = { Icon(painterResource(R.drawable.contrast), null) },
                                checked = pureBlack,
                                onCheckedChange = onPureBlackChange,
                            )
                        }

                        SwitchPreference(
                            title = { Text(stringResource(R.string.disable_blur)) },
                            description = stringResource(R.string.disable_blur_desc),
                            icon = { Icon(painterResource(R.drawable.blur_off), null) },
                            checked = disableBlur,
                            onCheckedChange = onDisableBlurChange,
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.use_system_font)) },
                            description = stringResource(R.string.use_system_font_desc),
                            icon = { Icon(painterResource(R.drawable.text_fields), null) },
                            checked = useSystemFont,
                            onCheckedChange = onUseSystemFontChange,
                        )
                    }
                }
            }

            item {
                PreferenceGroupTitle(
                    title = stringResource(R.string.player),
                    modifier = Modifier.padding(start = 24.dp, bottom = 4.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(Modifier.padding(vertical = 12.dp)) {
                        EnumListPreference(
                            title = { Text(stringResource(R.string.player_design_style)) },
                            icon = { Icon(painterResource(R.drawable.palette), null) },
                            selectedValue = playerDesignStyle,
                            onValueSelected = onPlayerDesignStyleChange,
                            valueText = {
                                when (it) {
                                    PlayerDesignStyle.V1 -> stringResource(R.string.player_design_v1)
                                    PlayerDesignStyle.V2 -> stringResource(R.string.player_design_v2)
                                    PlayerDesignStyle.V3 -> stringResource(R.string.player_design_v3)
                                    PlayerDesignStyle.V4 -> stringResource(R.string.player_design_v4)
                                    PlayerDesignStyle.V5 -> stringResource(R.string.player_design_v5)
                                    PlayerDesignStyle.V6 -> stringResource(R.string.player_design_v6)
                                    else -> it.name
                                }
                            },
                        )

                        EnumListPreference(
                            title = { Text("Mini player style") },
                            icon = { Icon(painterResource(R.drawable.nav_bar), null) },
                            selectedValue = miniPlayerStyle,
                            onValueSelected = onMiniPlayerStyleChange,
                            valueText = {
                                when (it) {
                                    MiniPlayerStyle.MODERN -> "Modern Glass"
                                    MiniPlayerStyle.LEGACY -> "Legacy Classic"
                                    MiniPlayerStyle.MINIMAL -> "Minimal Clean"
                                    MiniPlayerStyle.FLOATING -> "Floating Pill"
                                    MiniPlayerStyle.APPLE_MUSIC -> "Apple Music"
                                }
                            }
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.new_library_design)) },
                            description = stringResource(R.string.new_library_design_description),
                            icon = { Icon(painterResource(R.drawable.grid_view), null) },
                            checked = useNewLibraryDesign,
                            onCheckedChange = onUseNewLibraryDesignChange,
                        )

                        //  Main Player Background (Updated with GALAXY_BLUR)
                        ListPreference(
                            title = { Text(stringResource(R.string.player_background_style)) },
                            icon = { Icon(painterResource(R.drawable.gradient), null) },
                            selectedValue = playerBackground,
                            values = listOf(
                                PlayerBackgroundStyle.DEFAULT,
                                PlayerBackgroundStyle.GRADIENT,
                                PlayerBackgroundStyle.CUSTOM,
                                PlayerBackgroundStyle.BLUR,
                                PlayerBackgroundStyle.BREATHING_BLUR,
                                PlayerBackgroundStyle.GALAXY_BLUR,
                                PlayerBackgroundStyle.COLORING,
                                PlayerBackgroundStyle.GLOW,
                                PlayerBackgroundStyle.GLOW_ANIMATED
                            ),
                            valueText = {
                                when (it) {
                                    PlayerBackgroundStyle.DEFAULT -> stringResource(R.string.follow_theme)
                                    PlayerBackgroundStyle.GRADIENT -> stringResource(R.string.gradient)
                                    PlayerBackgroundStyle.CUSTOM -> stringResource(R.string.custom)
                                    PlayerBackgroundStyle.BLUR -> stringResource(R.string.player_background_blur) ?: "Blur"
                                    PlayerBackgroundStyle.BREATHING_BLUR -> "Breathing Blur"
                                    PlayerBackgroundStyle.GALAXY_BLUR -> "Galaxy Blur"
                                    PlayerBackgroundStyle.COLORING -> stringResource(R.string.coloring)
                                    PlayerBackgroundStyle.GLOW -> stringResource(R.string.glow)
                                    PlayerBackgroundStyle.GLOW_ANIMATED -> "Glow Animated"
                                    else -> it.name
                                }
                            },
                            onValueSelected = onPlayerBackgroundChange,
                        )

                        //  Mini Player Background
                        ListPreference(
                            title = { Text("Mini player background style") },
                            icon = { Icon(painterResource(R.drawable.gradient), null) },
                            selectedValue = miniPlayerBackground,
                            values = listOf(
                                PlayerBackgroundStyle.DEFAULT,
                                PlayerBackgroundStyle.GRADIENT,
                                PlayerBackgroundStyle.BLUR,
                                PlayerBackgroundStyle.BREATHING_BLUR,
                                PlayerBackgroundStyle.GALAXY_BLUR,
                                PlayerBackgroundStyle.BLUR_GRADIENT,
                                PlayerBackgroundStyle.COLORING,
                                PlayerBackgroundStyle.GLOW,
                                PlayerBackgroundStyle.GLOW_ANIMATED,
                                PlayerBackgroundStyle.APPLE_MUSIC,
                                PlayerBackgroundStyle.LIVE_MESH
                            ),
                            valueText = {
                                when (it) {
                                    PlayerBackgroundStyle.DEFAULT -> stringResource(R.string.follow_theme)
                                    PlayerBackgroundStyle.GRADIENT -> stringResource(R.string.gradient)
                                    PlayerBackgroundStyle.BLUR -> stringResource(R.string.player_background_blur) ?: "Blur"
                                    PlayerBackgroundStyle.BREATHING_BLUR -> "Breathing Blur"
                                    PlayerBackgroundStyle.GALAXY_BLUR -> "Galaxy Blur"
                                    PlayerBackgroundStyle.BLUR_GRADIENT -> "Blur Gradient"
                                    PlayerBackgroundStyle.COLORING -> stringResource(R.string.coloring)
                                    PlayerBackgroundStyle.GLOW -> stringResource(R.string.glow)
                                    PlayerBackgroundStyle.GLOW_ANIMATED -> "Glow Animated"
                                    PlayerBackgroundStyle.APPLE_MUSIC -> "Apple Music"
                                    PlayerBackgroundStyle.LIVE_MESH -> "Live Mesh"
                                    else -> it.name
                                }
                            },
                            onValueSelected = onMiniPlayerBackgroundChange,
                        )

                        if (playerBackground == PlayerBackgroundStyle.CUSTOM) {
                            PreferenceEntry(
                                title = { Text(stringResource(R.string.customized_background)) },
                                icon = { Icon(painterResource(R.drawable.image), null) },
                                onClick = { navController.navigate("customize_background") }
                            )
                        }

                        SwitchPreference(
                            title = { Text(stringResource(R.string.hide_player_thumbnail)) },
                            description = stringResource(R.string.hide_player_thumbnail_desc),
                            icon = { Icon(painterResource(R.drawable.hide_image), null) },
                            checked = hidePlayerThumbnail,
                            onCheckedChange = onHidePlayerThumbnailChange
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.Rhythemic_canvas)) },
                            description = stringResource(R.string.Rhythemic_canvas_desc),
                            icon = { Icon(painterResource(R.drawable.motion_photos_on), null) },
                            checked = archiveTuneCanvasEnabled,
                            onCheckedChange = onRhythemicCanvasEnabledChange
                        )

                        ThumbnailCornerRadiusSelectorButton(
                            modifier = Modifier.padding(16.dp),
                            onRadiusSelected = { selectedRadius ->
                                Timber.tag("Thumbnail").d("Radius Selector: $selectedRadius")
                            }
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.crop_thumbnail_to_square)) },
                            description = stringResource(R.string.crop_thumbnail_to_square_desc),
                            icon = { Icon(painterResource(R.drawable.image), null) },
                            checked = cropThumbnailToSquare,
                            onCheckedChange = onCropThumbnailToSquareChange
                        )

                        EnumListPreference(
                            title = { Text(stringResource(R.string.player_buttons_style)) },
                            icon = { Icon(painterResource(R.drawable.palette), null) },
                            selectedValue = playerButtonsStyle,
                            onValueSelected = onPlayerButtonsStyleChange,
                            valueText = {
                                when (it) {
                                    PlayerButtonsStyle.DEFAULT -> stringResource(R.string.default_style)
                                    PlayerButtonsStyle.SECONDARY -> stringResource(R.string.secondary_color_style)
                                    else -> it.name
                                }
                            },
                        )

                        PreferenceEntry(
                            title = { Text(stringResource(R.string.player_slider_style)) },
                            description = sliderStyleLabel(sliderStyle),
                            icon = { Icon(painterResource(R.drawable.sliders), null) },
                            onClick = { showSliderOptionDialog = true },
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.enable_swipe_thumbnail)) },
                            icon = { Icon(painterResource(R.drawable.swipe), null) },
                            checked = swipeThumbnail,
                            onCheckedChange = onSwipeThumbnailChange,
                        )

                        AnimatedVisibility(swipeThumbnail) {
                            var showSensitivityDialog by rememberSaveable { mutableStateOf(false) }
                            
                            if (showSensitivityDialog) {
                                var tempSensitivity by remember { mutableFloatStateOf(swipeSensitivity) }
                                
                                DefaultDialog(
                                    onDismiss = { 
                                        tempSensitivity = swipeSensitivity
                                        showSensitivityDialog = false 
                                    },
                                    buttons = {
                                        TextButton(onClick = { tempSensitivity = 0.73f }) {
                                            Text(stringResource(R.string.reset))
                                        }
                                        Spacer(modifier = Modifier.weight(1f))
                                        TextButton(onClick = { 
                                            tempSensitivity = swipeSensitivity
                                            showSensitivityDialog = false 
                                        }) {
                                            Text(stringResource(android.R.string.cancel))
                                        }
                                        TextButton(onClick = { 
                                            onSwipeSensitivityChange(tempSensitivity)
                                            showSensitivityDialog = false 
                                        }) {
                                            Text(stringResource(android.R.string.ok))
                                        }
                                    }
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        Text(
                                            text = stringResource(R.string.swipe_sensitivity),
                                            style = MaterialTheme.typography.headlineSmall,
                                            modifier = Modifier.padding(bottom = 16.dp)
                                        )
                                        Text(
                                            text = stringResource(R.string.sensitivity_percentage, (tempSensitivity * 100).roundToInt()),
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(bottom = 16.dp)
                                        )
                                        Slider(
                                            value = tempSensitivity,
                                            onValueChange = { tempSensitivity = it },
                                            valueRange = 0f..1f,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                            
                            PreferenceEntry(
                                title = { Text(stringResource(R.string.swipe_sensitivity)) },
                                description = stringResource(R.string.sensitivity_percentage, (swipeSensitivity * 100).roundToInt()),
                                icon = { Icon(painterResource(R.drawable.tune), null) },
                                onClick = { showSensitivityDialog = true }
                            )
                        }
                    }
                }
            }

            item {
                PreferenceGroupTitle(
                    title = stringResource(R.string.lyrics),
                    modifier = Modifier.padding(start = 24.dp, bottom = 4.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(Modifier.padding(vertical = 12.dp)) {
                        SwitchPreference(
                            title = { Text("Lyrics V2 (Experimental)") },
                            description = "Use the new fluid word-synced lyrics engine",
                            icon = { Icon(painterResource(R.drawable.lyrics), null) },
                            checked = useLyricsV2,
                            onCheckedChange = onUseLyricsV2Change,
                        )

                        EnumListPreference(
                            title = { Text(stringResource(R.string.lyrics_text_position)) },
                            icon = { Icon(painterResource(R.drawable.lyrics), null) },
                            selectedValue = lyricsPosition,
                            onValueSelected = onLyricsPositionChange,
                            valueText = {
                                when (it) {
                                    LyricsPosition.LEFT -> stringResource(R.string.left)
                                    LyricsPosition.CENTER -> stringResource(R.string.center)
                                    LyricsPosition.RIGHT -> stringResource(R.string.right)
                                    else -> it.name
                                }
                            },
                        )

                        EnumListPreference(
                            title = { Text(stringResource(R.string.lyrics_animation_style)) },
                            icon = { Icon(painterResource(R.drawable.animation), null) },
                            selectedValue = lyricsAnimation,
                            onValueSelected = onLyricsAnimationChange,
                            valueText = {
                                when (it) {
                                    LyricsAnimationStyle.NONE -> stringResource(R.string.none)
                                    LyricsAnimationStyle.FADE -> stringResource(R.string.fade)
                                    LyricsAnimationStyle.GLOW -> stringResource(R.string.glow)
                                    LyricsAnimationStyle.SLIDE -> stringResource(R.string.slide)
                                    LyricsAnimationStyle.KARAOKE -> stringResource(R.string.karaoke)
                                    LyricsAnimationStyle.APPLE -> stringResource(R.string.apple_music_style)
                                    else -> it.name
                                }
                            }
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.lyrics_click_change)) },
                            icon = { Icon(painterResource(R.drawable.lyrics), null) },
                            checked = lyricsClick,
                            onCheckedChange = onLyricsClickChange,
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.lyrics_auto_scroll)) },
                            icon = { Icon(painterResource(R.drawable.lyrics), null) },
                            checked = lyricsScroll,
                            onCheckedChange = onLyricsScrollChange,
                        )

                        var showLyricsTextSizeDialog by rememberSaveable { mutableStateOf(false) }
                        
                        if (showLyricsTextSizeDialog) {
                            var tempTextSize by remember { mutableFloatStateOf(lyricsTextSize) }
                            
                            DefaultDialog(
                                onDismiss = { 
                                    tempTextSize = lyricsTextSize
                                    showLyricsTextSizeDialog = false 
                                },
                                buttons = {
                                    TextButton(onClick = { tempTextSize = 24f }) {
                                        Text(stringResource(R.string.reset))
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    TextButton(onClick = { 
                                        tempTextSize = lyricsTextSize
                                        showLyricsTextSizeDialog = false 
                                    }) {
                                        Text(stringResource(android.R.string.cancel))
                                    }
                                    TextButton(onClick = { 
                                        onLyricsTextSizeChange(tempTextSize)
                                        showLyricsTextSizeDialog = false 
                                    }) {
                                        Text(stringResource(android.R.string.ok))
                                    }
                                }
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.lyrics_text_size),
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )
                                    Text(
                                        text = "${tempTextSize.roundToInt()} sp",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )
                                    Slider(
                                        value = tempTextSize,
                                        onValueChange = { tempTextSize = it },
                                        valueRange = 16f..36f,
                                        steps = 19,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                        
                        PreferenceEntry(
                            title = { Text(stringResource(R.string.lyrics_text_size)) },
                            description = "${lyricsTextSize.roundToInt()} sp",
                            icon = { Icon(painterResource(R.drawable.text_fields), null) },
                            onClick = { showLyricsTextSizeDialog = true }
                        )
                        
                        var showLyricsLineSpacingDialog by rememberSaveable { mutableStateOf(false) }
                        
                        if (showLyricsLineSpacingDialog) {
                            var tempLineSpacing by remember { mutableFloatStateOf(lyricsLineSpacing) }
                            
                            DefaultDialog(
                                onDismiss = { 
                                    tempLineSpacing = lyricsLineSpacing
                                    showLyricsLineSpacingDialog = false 
                                },
                                buttons = {
                                    TextButton(onClick = { tempLineSpacing = 1.3f }) {
                                        Text(stringResource(R.string.reset))
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    TextButton(onClick = { 
                                        tempLineSpacing = lyricsLineSpacing
                                        showLyricsLineSpacingDialog = false 
                                    }) {
                                        Text(stringResource(android.R.string.cancel))
                                    }
                                    TextButton(onClick = { 
                                        onLyricsLineSpacingChange(tempLineSpacing)
                                        showLyricsLineSpacingDialog = false 
                                    }) {
                                        Text(stringResource(android.R.string.ok))
                                    }
                                }
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.lyrics_line_spacing),
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )
                                    Text(
                                        text = "${String.format("%.1f", tempLineSpacing)}x",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )
                                    Slider(
                                        value = tempLineSpacing,
                                        onValueChange = { tempLineSpacing = it },
                                        valueRange = 1.0f..2.0f,
                                        steps = 19,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                        
                        PreferenceEntry(
                            title = { Text(stringResource(R.string.lyrics_line_spacing)) },
                            description = "${String.format("%.1f", lyricsLineSpacing)}x",
                            icon = { Icon(painterResource(R.drawable.text_fields), null) },
                            onClick = { showLyricsLineSpacingDialog = true }
                        )
                    }
                }
            }

            item {
                PreferenceGroupTitle(
                    title = stringResource(R.string.misc),
                    modifier = Modifier.padding(start = 24.dp, bottom = 4.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(Modifier.padding(vertical = 12.dp)) {
                        EnumListPreference(
                            title = { Text(stringResource(R.string.default_open_tab)) },
                            icon = { Icon(painterResource(R.drawable.nav_bar), null) },
                            selectedValue = defaultOpenTab,
                            onValueSelected = onDefaultOpenTabChange,
                            valueText = {
                                when (it) {
                                    NavigationTab.HOME -> stringResource(R.string.home)
                                    NavigationTab.SEARCH -> stringResource(R.string.search)
                                    NavigationTab.LIBRARY -> stringResource(R.string.filter_library)
                                    else -> it.name
                                }
                            },
                        )

                        ListPreference(
                            title = { Text(stringResource(R.string.default_lib_chips)) },
                            icon = { Icon(painterResource(R.drawable.tab), null) },
                            selectedValue = defaultChip,
                            values = listOf(
                                LibraryFilter.LIBRARY, LibraryFilter.PLAYLISTS, LibraryFilter.SONGS,
                                LibraryFilter.ALBUMS, LibraryFilter.ARTISTS
                            ),
                            valueText = {
                                when (it) {
                                    LibraryFilter.SONGS -> stringResource(R.string.songs)
                                    LibraryFilter.ARTISTS -> stringResource(R.string.artists)
                                    LibraryFilter.ALBUMS -> stringResource(R.string.albums)
                                    LibraryFilter.PLAYLISTS -> stringResource(R.string.playlists)
                                    LibraryFilter.LIBRARY -> stringResource(R.string.filter_library)
                                }
                            },
                            onValueSelected = onDefaultChipChange,
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.show_home_category_chips)) },
                            description = stringResource(R.string.show_home_category_chips_desc),
                            icon = { Icon(painterResource(R.drawable.home_outlined), null) },
                            checked = showHomeCategoryChips,
                            onCheckedChange = onShowHomeCategoryChipsChange,
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.show_tags_in_library)) },
                            description = stringResource(R.string.show_tags_in_library_desc),
                            icon = { Icon(painterResource(R.drawable.filter_alt), null) },
                            checked = showTagsInLibrary,
                            onCheckedChange = onShowTagsInLibraryChange,
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.swipe_song_to_add)) },
                            icon = { Icon(painterResource(R.drawable.swipe), null) },
                            checked = swipeToSong,
                            onCheckedChange = onSwipeToSongChange
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.slim_navbar)) },
                            icon = { Icon(painterResource(R.drawable.nav_bar), null) },
                            checked = slimNav,
                            onCheckedChange = onSlimNavChange
                        )

                        EnumListPreference(
                            title = { Text(stringResource(R.string.grid_cell_size)) },
                            icon = { Icon(painterResource(R.drawable.grid_view), null) },
                            selectedValue = gridItemSize,
                            onValueSelected = onGridItemSizeChange,
                            valueText = {
                                when (it) {
                                    GridItemSize.BIG -> stringResource(R.string.big)
                                    GridItemSize.SMALL -> stringResource(R.string.small)
                                    else -> it.name
                                }
                            },
                        )
                    }
                }
            }

            item {
                PreferenceGroupTitle(
                    title = stringResource(R.string.auto_playlists),
                    modifier = Modifier.padding(start = 24.dp, bottom = 4.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(Modifier.padding(vertical = 12.dp)) {
                        SwitchPreference(
                            title = { Text(stringResource(R.string.show_liked_playlist)) },
                            icon = { Icon(painterResource(R.drawable.favorite), null) },
                            checked = showLikedPlaylist,
                            onCheckedChange = onShowLikedPlaylistChange
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.show_downloaded_playlist)) },
                            icon = { Icon(painterResource(R.drawable.offline), null) },
                            checked = showDownloadedPlaylist,
                            onCheckedChange = onShowDownloadedPlaylistChange
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.show_top_playlist)) },
                            icon = { Icon(painterResource(R.drawable.trending_up), null) },
                            checked = showTopPlaylist,
                            onCheckedChange = onShowTopPlaylistChange
                        )

                        SwitchPreference(
                            title = { Text(stringResource(R.string.show_cached_playlist)) },
                            icon = { Icon(painterResource(R.drawable.cached), null) },
                            checked = showCachedPlaylist,
                            onCheckedChange = onShowCachedPlaylistChange
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SliderStyleOptionCard(
    sliderStyle: SliderStyle,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var sliderValue by remember {
        mutableFloatStateOf(0.5f)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(24.dp))
            .background(
                if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) 
                else MaterialTheme.colorScheme.surfaceContainerHigh
            )
            .border(
                2.dp,
                if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                RoundedCornerShape(24.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        StyledPlaybackSlider(
            sliderStyle = sliderStyle,
            value = sliderValue,
            valueRange = 0f..1f,
            onValueChange = { sliderValue = it },
            onValueChangeFinished = {},
            activeColor = MaterialTheme.colorScheme.primary,
            isPlaying = true,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )

        Text(
            text = sliderStyleLabel(sliderStyle),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun sliderStyleLabel(sliderStyle: SliderStyle): String {
    return when (sliderStyle) {
        SliderStyle.Standard -> stringResource(R.string.slider_style_standard)
        SliderStyle.Wavy -> stringResource(R.string.slider_style_wavy)
        SliderStyle.Thick -> stringResource(R.string.slider_style_thick)
        SliderStyle.Circular -> stringResource(R.string.slider_style_circular)
        SliderStyle.Simple -> stringResource(R.string.slider_style_simple)
    }
}

enum class DarkMode {
    ON, OFF, AUTO
}

enum class NavigationTab {
    HOME, SEARCH, LIBRARY
}

enum class LyricsPosition {
    LEFT, CENTER, RIGHT
}

enum class PlayerTextAlignment {
    SIDED, CENTER
}
