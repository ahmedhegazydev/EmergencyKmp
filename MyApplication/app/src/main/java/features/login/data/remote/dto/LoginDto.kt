package features.login.data.remote.dto

import features.login.domain.model.Login

data class LoginDto(
    val id: Long?,
    val title: String?,
    val description: String?
) {
    fun toDomain(): Login = Login(
        id = id ?: 0L,
        title = title.orEmpty(),
        description = description.orEmpty()
    )
}