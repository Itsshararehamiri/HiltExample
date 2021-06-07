package com.example.movieapp.data.local



import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.entities.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {


    @Query("SELECT * FROM movies")
    fun getAllMovies() : LiveData<List<Movie>>




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("SELECT * FROM movies WHERE title = :query")
    fun getSearchedMovies(query:String) : LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovie(id: Int): LiveData<Movie>







}