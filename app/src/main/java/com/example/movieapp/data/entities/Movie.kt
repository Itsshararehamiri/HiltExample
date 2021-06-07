package com.example.movieapp.data.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "movies" )
data class Movie(
    val adult: Boolean,
    val backdrop_path: String?,
    @PrimaryKey
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,

){
   /* constructor():this(false,"",0,"","","",0.0,"",""
    ,"",false,0.0,0, arrayListOf())*/
   @Ignore
   val genre_ids: List<Int> = arrayListOf()
}