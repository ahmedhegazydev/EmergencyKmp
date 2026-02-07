package features.login.data.repository

import core.common.ErrorHandler
import core.common.Resource
import features.login.data.local.dao.LoginDao
import features.login.data.local.entity.LoginEntity
import features.login.data.remote.LoginApiService
import features.login.domain.model.Login
import features.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: LoginApiService,
    private val dao: LoginDao
) : LoginRepository {

    override fun getLoginList(): Flow<Resource<List<Login>>> = flow {
        emit(Resource.Loading)

        try {
            val remote = api.getLoginList().map { it.toDomain() }

            dao.clearAll()
            dao.insertAll(remote.map { LoginEntity.fromDomain(it) })

            emit(Resource.Success(remote))
        } catch (e: Throwable) {
            val cached = dao.getAll().map { it.toDomain() }

            if (cached.isNotEmpty()) {
                emit(Resource.Success(cached))
            } else {
                emit(Resource.Error(ErrorHandler.getErrorMessage(e), e))
            }
        }
    }
}