package features.login.di

import core.database.AppDatabase
import core.common.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import features.login.data.remote.LoginApiService
import features.login.data.repository.LoginRepositoryImpl
import features.login.domain.repository.LoginRepository

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApiService =
        retrofit.create(LoginApiService::class.java)

    @Provides
    @Singleton
    fun provideLoginRepository(
        db: AppDatabase,
        api: LoginApiService,
        dispatcherProvider: DispatcherProvider
    ): LoginRepository =
        LoginRepositoryImpl(db, api, dispatcherProvider)
}