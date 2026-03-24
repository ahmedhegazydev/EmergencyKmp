package com.example.emergencykmp.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emergencykmp.EmergencyServiceController
import com.example.emergencykmp.EmergencySettings
import com.example.emergencykmp.EmergencySettingsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmergencyViewModel(
    private val repo: EmergencySettingsRepository,
    private val service: EmergencyServiceController
) : ViewModel() {

    private val _state = MutableStateFlow(EmergencyState())
    val state: StateFlow<EmergencyState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EmergencyEffect>()
    val effect = _effect.asSharedFlow()

    fun onIntent(i: EmergencyIntent) {
        when (i) {
            EmergencyIntent.Load -> load()
            is EmergencyIntent.SetKeyword -> _state.update { it.copy(keyword = i.v) }
            is EmergencyIntent.SetPhone -> _state.update { it.copy(phone = i.v) }
            is EmergencyIntent.SetSms -> _state.update { it.copy(sms = i.v) }
            EmergencyIntent.Save -> save()
            EmergencyIntent.StartMode -> startMode()
            EmergencyIntent.StopMode -> stopMode()
        }
    }

    private fun load() = viewModelScope.launch {
        _state.update { it.copy(loading = true, error = null) }
        val s = repo.load()
        _state.update {
            it.copy(
                keyword = s.keyword,
                phone = s.phoneNumber,
                sms = s.smsText,
                loading = false
            )
        }
    }

    private fun save() = viewModelScope.launch {
        val st = state.value
        val s = EmergencySettings(st.keyword.trim(), st.phone.trim(), st.sms.trim())
        if (!s.isValid()) {
            _effect.emit(EmergencyEffect.Toast("Please enter keyword + phone"))
            return@launch
        }
        repo.save(s)
        _effect.emit(EmergencyEffect.Toast("Saved ✅"))
    }

    private fun startMode() = viewModelScope.launch {
        service.start()
        _state.update { it.copy(enabled = true) }
        _effect.emit(EmergencyEffect.Toast("Emergency Mode ON"))
    }

    private fun stopMode() = viewModelScope.launch {
        service.stop()
        _state.update { it.copy(enabled = false) }
        _effect.emit(EmergencyEffect.Toast("Emergency Mode OFF"))
    }
}
