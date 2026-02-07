package features.login.presentation.state

import features.login.domain.model.Login

sealed class LoginUiState {
    object Loading : LoginUiState()
    data class Success(val items: List<Login>) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
    object Empty : LoginUiState()
}