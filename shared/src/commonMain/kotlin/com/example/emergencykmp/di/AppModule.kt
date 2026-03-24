package com.example.emergencykmp.di

import com.example.emergencykmp.EmergencySettingsRepository
import com.example.emergencykmp.EmergencySettingsRepositoryImpl
import org.koin.dsl.module

val commonModule = module {

    single<EmergencySettingsRepository> {
        EmergencySettingsRepositoryImpl()
    }
}

