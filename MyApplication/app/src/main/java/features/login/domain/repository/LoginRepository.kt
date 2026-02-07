package features.login.domain.repository

import core.common.Resource
import features.login.domain.model.Login
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun getLoginList(): Flow<Resource<List<Login>>>
}