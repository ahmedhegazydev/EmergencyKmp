data class EmergencySettings(
    val keyword: String = "",
    val phoneNumber: String = "",
    val smsText: String = "Emergency! Please help.",
    val includeLocation: Boolean = false
) {
    fun isValid(): Boolean = keyword.isNotBlank() && phoneNumber.isNotBlank()
}
