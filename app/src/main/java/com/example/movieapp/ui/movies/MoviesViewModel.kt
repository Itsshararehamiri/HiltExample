package com.example.movieapp.ui.movies

import androidx.lifecycle.*
import com.example.movieapp.data.entities.Movie
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val queryLiveData = MutableLiveData<String>()
    val movies = repository.getMovies()
    fun searchMovie(queryString: String) {
        queryLiveData.postValue(queryString)
    }
    val movieResult: LiveData<Resource<List<Movie>>> = queryLiveData.switchMap { queryString ->
        liveData(Dispatchers.IO) {
            val repos = repository.getSearchedMovies(queryString)
            emitSource(repos)
        }
    }


}
