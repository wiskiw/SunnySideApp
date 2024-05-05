package dev.wiskiw.sunnysideapp.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SunnySideApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SunnySideApp)
            allowOverride(false)

            modules(
                sunnySideModule,
            )
        }
    }

}
