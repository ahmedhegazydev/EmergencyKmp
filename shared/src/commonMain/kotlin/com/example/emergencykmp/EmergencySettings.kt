package com.example.emergencykmp

import com.russhwolf.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class EmergencySettings(
    val keyword: String = "",
    val phoneNumber: String = "",
    val smsText: String = "Emergency! Please help.",
    val includeLocation: Boolean = false
) {
    fun isValid(): Boolean = keyword.isNotBlank() && phoneNumber.isNotBlank()
}

interface EmergencySettingsRepository {
    suspend fun load(): EmergencySettings
    suspend fun save(s: EmergencySettings)
}

class EmergencySettingsRepositoryImpl(
    private val settings: Settings = Settings()
) : EmergencySettingsRepository {

    override suspend fun load(): EmergencySettings = withContext(Dispatchers.Default) {
        EmergencySettings(
            keyword = settings.getString("keyword", ""),
            phoneNumber = settings.getString("phone", ""),
            smsText = settings.getString("sms", "Emergency! Please help.")
        )
    }

    override suspend fun save(s: EmergencySettings) = withContext(Dispatchers.Default) {
        settings.putString("keyword", s.keyword)
        settings.putString("phone", s.phoneNumber)
        settings.putString("sms", s.smsText)
    }
}
