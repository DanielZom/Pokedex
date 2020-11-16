package hu.daniel.pokedex

fun onDebug(runInDebugMode: () -> Unit) {
    if (BuildConfig.DEBUG) runInDebugMode.invoke()
}