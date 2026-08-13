/*
 * Rhythemic - Modern Music Player
 *
 * Copyright (c) 2026 JAY01-CYBER
 * Signature: Rhythemic::GENERAL::V1
 */

package com.j.rhythemic.viewmodels
 
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.j.rhythemic.db.MusicDatabase
import com.j.rhythemic.utils.reportException
import com.j.rhythemic.innertube.YouTube
import com.j.rhythemic.innertube.models.AlbumItem
import com.j.rhythemic.innertube.models.PlaylistItem
import com.j.rhythemic.innertube.models.YTItem
import com.j.rhythemic.innertube.utils.completed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
 
@HiltViewModel
class BrowseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val browseId: String? = savedStateHandle.get<String>("browseId")
 
    val items = MutableStateFlow<List<YTItem>?>(emptyList())
    val title = MutableStateFlow<String?>("Loading...") // Initial state
 
    init {
        viewModelScope.launch {
            browseId?.let { id ->
                title.value = "Fetching data..." // UI progress update
                
                YouTube.browse(id, null).onSuccess { result ->
                    val allItems = result.items.flatMap { it.items }
                    items.value = allItems
                    
                    if (allItems.isEmpty()) {
                        // Agar parsing successful rahi par list khali mili
                        title.value = "Data Empty: Layout mismatch"
                    } else {
                        // Success par normal title set karega
                        title.value = result.title ?: "No Title"
                    }
                }.onFailure { error ->
                    reportException(error)
                    // Yahan par actual exception app screen par dikhega
                    title.value = "Crash: ${error.javaClass.simpleName} - ${error.message?.take(50)}"
                }
            } ?: run {
                title.value = "Error: browseId is null"
            }
        }
    }
}
