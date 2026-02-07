package features.login.domain.usecase

import core.common.Resource
import features.login.domain.model.Login
import features.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoginListUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    operator fun invoke(): Flow<Resource<List<Login>>> =
        repository.getLoginList()
}