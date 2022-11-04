package com.mambo.poetree.android

import android.app.Application
import com.mambo.poetree.android.presentation.utils.NetworkMonitor
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import timber.log.Timber

class PoetreeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        setupNetworkMonitor()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Napier.base(DebugAntilog())
        }
    }

    private fun setupNetworkMonitor() {
        NetworkMonitor(context = this).register()
    }

}