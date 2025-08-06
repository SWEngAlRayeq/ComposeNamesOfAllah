package app.names_of_allah.di

import app.names_of_allah.data.api.AllahNamesApiService
import app.names_of_allah.data.repo_impl.AllahNamesRepositoryImpl
import app.names_of_allah.domain.repository.AllahNamesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.aladhan.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    fun provideAllahNamesApi(retrofit: Retrofit): AllahNamesApiService =
        retrofit.create(AllahNamesApiService::class.java)

    @Provides
    fun provideAllahNamesRepository(api: AllahNamesApiService): AllahNamesRepository =
        AllahNamesRepositoryImpl(api)

}