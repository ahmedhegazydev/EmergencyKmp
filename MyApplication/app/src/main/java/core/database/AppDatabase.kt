package core.database

import features.login.data.local.entity.LoginEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ LoginEntity::class ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // TODO: add DAOs, e.g.:
    // abstract fun featureNameDao(): FeatureNameDao

    abstract fun loginDao(): LoginDao
}