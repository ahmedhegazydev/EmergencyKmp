package core.common

import retrofit2.HttpException
import java.io.IOException

object ErrorHandler {

    fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is IOException -> "Network error, please check your connection."
            is HttpException -> "Server error: ${throwable.code()}"
            else -> throwable.localizedMessage ?: "Unexpected error occurred."
        }
    }
}