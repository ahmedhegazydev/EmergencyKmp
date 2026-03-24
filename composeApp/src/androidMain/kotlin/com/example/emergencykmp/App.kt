package com.example.emergencykmp

import android.app.Application
import com.example.emergencykmp.di.androidModule
import com.example.emergencykmp.di.commonModules
//import com.example.emergencykmp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

//        initKoin(
//            additionalModules = listOf(androidModule)
//        )
        startKoin {
            androidContext(this@App) // ✅ هنا شغال
            modules(commonModules() + androidModule)
        }
    }
}

