package cz.chm4.chmtestapp

import android.app.Application
import cz.applifting.mvvmstartingkit.BuildConfig
import timber.log.Timber
import timber.log.Timber.Forest.plant

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
    }
}