package com.github.ndex.messenger.demo_module

import android.app.Application
import com.github.ndex.messenger.demo_module.di.AppComponent
import com.github.ndex.messenger.demo_module.di.AppModule
import com.github.ndex.messenger.demo_module.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger()
    }

    private fun initDagger(): AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(applicationContext))
                    .build()
}