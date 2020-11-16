package hu.daniel.pokedex

import android.app.Application
import com.facebook.stetho.Stetho
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
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
        AppCenter.start(this, BuildConfig.APPCENTER_KEY, Analytics::class.java, Crashes::class.java)
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@PokedexApplication)
            onDebug { androidLogger(Level.ERROR) }
            modules(KoinModule)
        }
    }
}