/*
 * Rhythemic Utility Module
 *
 * Internal helper functions
 * Signature: Rhythemic::UTILITY::V1
 */

package com.j.rhythemic.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.j.rhythemic.constants.InnerTubeCookieKey
import com.j.rhythemic.constants.YtmSyncKey
import com.j.rhythemic.utils.dataStore
import com.j.rhythemic.utils.get
import com.j.rhythemic.innertube.utils.parseCookieString

fun Context.isSyncEnabled(): Boolean {
    return dataStore.get(YtmSyncKey, true) && isUserLoggedIn()
}

fun Context.isUserLoggedIn(): Boolean {
    val cookie = dataStore[InnerTubeCookieKey] ?: ""
    return "SAPISID" in parseCookieString(cookie) && isInternetConnected()
}

fun Context.isInternetConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
}
