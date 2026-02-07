package features.login.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import features.login.domain.model.Login

@Entity(tableName = "login")
data class LoginEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String
) {
    fun toDomain(): Login = Login(
        id = id,
        title = title,
        description = description
    )

    companion object {
        fun fromDomain(item: Login): LoginEntity =
            LoginEntity(
                id = item.id,
                title = item.title,
                description = item.description
            )
    }
}