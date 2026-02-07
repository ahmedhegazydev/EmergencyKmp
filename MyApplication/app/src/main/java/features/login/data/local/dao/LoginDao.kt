package features.login.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import features.login.data.local.entity.LoginEntity

@Dao
interface LoginDao {

    @Query("SELECT * FROM login")
    suspend fun getAll(): List<LoginEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<LoginEntity>)

    @Query("DELETE FROM login")
    suspend fun clearAll()
}