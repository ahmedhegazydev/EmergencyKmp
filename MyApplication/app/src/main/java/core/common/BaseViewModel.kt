package core.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        onCoroutineError(throwable)
    }

    protected fun launchIo(block: suspend () -> Unit) {
        viewModelScope.launch(dispatcherProvider.io + errorHandler) {
            block()
        }
    }

    protected open fun onCoroutineError(throwable: Throwable) {
        // subclasses can override for global error handling
    }
}