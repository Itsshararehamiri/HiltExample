package com.example.movieapp.di

import android.content.Context
import android.util.Log
import com.example.movieapp.data.local.AppDatabase
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.data.remote.MovieRemoteDataSource
import com.example.movieapp.data.remote.MovieService
import com.example.movieapp.data.repository.MovieRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                return@addInterceptor addApiKeyToRequests(chain)
            }
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(movieService: MovieService) =
        MovieRemoteDataSource(movieService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: MovieRemoteDataSource,
        localDataSource: MovieDao
    ) =
        MovieRepository(remoteDataSource, localDataSource)
}

private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
    val request = chain.request().newBuilder()
    val originalHttpUrl = chain.request().url
    val newUrl = originalHttpUrl.newBuilder()
        .addQueryParameter("api_key", "3721a37a13dfd8c83dc2e8ec18aa3944").build()
    request.url(newUrl)
    return chain.proceed(request.build())
}
public val s = "https://image.tmdb.org/t/p/original"

