package com.example.emergencykmp.di

import com.example.emergencykmp.AndroidEmergencyServiceController
import com.example.emergencykmp.EmergencyServiceController
import com.example.emergencykmp.mvi.EmergencyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {

    single<EmergencyServiceController> { AndroidEmergencyServiceController(androidContext()) }

    viewModel { EmergencyViewModel(get(), get()) } // repo + service

}
