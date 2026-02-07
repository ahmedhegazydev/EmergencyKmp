import com.russhwolf.settings.Settings

class SettingsRepo(private val settings: Settings) {
    fun save(s: EmergencySettings) {
        settings.putString("keyword", s.keyword)
        settings.putString("phone", s.phoneNumber)
        settings.putString("sms", s.smsText)
        settings.putBoolean("loc", s.includeLocation)
    }

    fun load(): EmergencySettings = EmergencySettings(
        keyword = settings.getString("keyword", ""),
        phoneNumber = settings.getString("phone", ""),
        smsText = settings.getString("sms", "Emergency! Please help."),
        includeLocation = settings.getBoolean("loc", false)
    )
}
