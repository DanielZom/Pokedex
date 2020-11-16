package hu.daniel.pokedex.util

import hu.daniel.pokedex.BuildConfig
import timber.log.Timber

fun onDebug(runInDebugMode: () -> Unit) {
    if (BuildConfig.DEBUG) runInDebugMode.invoke()
}

fun noop(logMessage: String? = null) = logMessage?.let { Timber.e(it) }