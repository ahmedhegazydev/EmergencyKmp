package com.example.emergencykmp.mvi

data class EmergencyState(
  val keyword: String = "",
  val phone: String = "",
  val sms: String = "",
  val enabled: Boolean = false,
  val loading: Boolean = false,
  val error: String? = null
)

sealed interface EmergencyIntent {
  data object Load : EmergencyIntent
  data class SetKeyword(val v: String) : EmergencyIntent
  data class SetPhone(val v: String) : EmergencyIntent
  data class SetSms(val v: String) : EmergencyIntent
  data object Save : EmergencyIntent
  data object StartMode : EmergencyIntent
  data object StopMode : EmergencyIntent
}

sealed interface EmergencyEffect {
  data class Toast(val msg: String) : EmergencyEffect
}
