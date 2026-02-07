package features.login.data.remote

import features.login.data.remote.dto.LoginDto
import retrofit2.http.GET

interface LoginApiService {

    @GET("login")
    suspend fun getLoginList(): List<LoginDto>
}