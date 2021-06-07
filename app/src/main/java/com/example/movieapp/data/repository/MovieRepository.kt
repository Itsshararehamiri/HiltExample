package com.example.movieapp.data.repository


import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.data.remote.MovieRemoteDataSource
import com.example.movieapp.utils.performGetOperation
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) {

    fun getMovie(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getMovie(id) },
        networkCall = { remoteDataSource.getMovie(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getMovies() = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies() },
        networkCall = { remoteDataSource.getMovies() },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )

    fun getSearchedMovies(query:String) = performGetOperation(
        databaseQuery = {
            localDataSource.getSearchedMovies(query) },
        networkCall = { remoteDataSource.getSearchedMovies(query) },
       saveCallResult = { localDataSource.insertAll(it.results) }
    )
}