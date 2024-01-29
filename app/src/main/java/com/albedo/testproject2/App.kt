package com.albedo.testproject2

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.work.Configuration
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class App : Application(), Configuration.Provider {

    private val TAG = "App"

    override val workManagerConfiguration: Configuration
        get() = Configuration
            .Builder()
            .setMinimumLoggingLevel(android.util.Log.ERROR)
            .build()

}
