package io.github.luteoos.quicktype

import android.app.Application
import android.os.StrictMode
import timber.log.Timber

class QTypeStartup : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.e(this.toString())
        if(BuildConfig.DEBUG)
            initDebugStuff()
    }

    private fun initDebugStuff() {
        Timber.plant(Timber.DebugTree())

        Timber.e("initDebugStuff")
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build())
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build())
    }
}