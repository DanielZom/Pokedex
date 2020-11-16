package hu.daniel.pokedex

import android.app.Application
import com.facebook.stetho.Stetho
import hu.daniel.pokedex.util.onDebug
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class PokedexApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        Stetho.initializeWithDefaults(this)
        onDebug { Timber.plant(Timber.DebugTree()) }
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@PokedexApplication)
            onDebug { androidLogger(Level.ERROR) }
            modules(KoinModule)
        }
    }
}