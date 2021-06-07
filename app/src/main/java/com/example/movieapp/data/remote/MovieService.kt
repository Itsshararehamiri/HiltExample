package com.example.movieapp.data.remote

import com.example.movieapp.data.entities.Movie
import com.example.movieapp.data.entities.MovieList
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    suspend fun getAllMovies() : Response<MovieList>



    @GET("search/movie")
    suspend fun getSearchedMovies(@Query("query")  query:String): Response<MovieList>



    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Int): Response<Movie>




}