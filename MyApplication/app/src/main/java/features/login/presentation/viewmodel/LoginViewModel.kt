package features.login.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import core.common.BaseViewModel
import core.common.DispatcherProvider
import core.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import features.login.domain.usecase.GetLoginListUseCase
import features.login.presentation.state.LoginUiState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginListUseCase: GetLoginListUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _uiState = MutableLiveData<LoginUiState>()
    val uiState: LiveData<LoginUiState> = _uiState

    fun loadLoginList() {
        _uiState.value = LoginUiState.Loading

        launchIo {
            getLoginListUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val items = result.data
                        _uiState.postValue(
                            if (items.isEmpty()) LoginUiState.Empty
                            else LoginUiState.Success(items)
                        )
                    }

                    is Resource.Error ->
                        _uiState.postValue(LoginUiState.Error(result.message))

                    is Resource.Loading ->
                        _uiState.postValue(LoginUiState.Loading)
                }
            }
        }
    }
}