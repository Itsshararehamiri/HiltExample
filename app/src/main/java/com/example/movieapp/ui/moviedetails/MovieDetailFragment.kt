package com.example.movieapp.ui.moviedetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.entities.Movie
import com.example.movieapp.di.s
import com.example.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import kotlinx.android.synthetic.main.movie_detail_fragment.view.*

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var container: ConstraintLayout
    private lateinit var titleTv: TextView
    private lateinit var descriptionTv: TextView
    private lateinit var trialerTv: TextView
    private lateinit var voteCountTv: TextView
    private lateinit var originalLanguageTv: TextView
    private lateinit var popularityTv: TextView
    private lateinit var releaseDateTv: TextView
    private lateinit var originalTitleTv: TextView
    private lateinit var voteAverage: TextView
    private lateinit var image: ImageView



    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_bar)
        container = view.findViewById(R.id.movie_cl)
        originalTitleTv = view.findViewById(R.id.original_title_tv)
        titleTv = view.findViewById(R.id.title)
        originalLanguageTv = view.findViewById(R.id.original_language_tv)
        popularityTv = view.findViewById(R.id.popularity_tv)
        descriptionTv = view.findViewById(R.id.description_tv)
        trialerTv = view.findViewById(R.id.trialer_tv)
        voteCountTv = view.findViewById(R.id.vote_count_tv)
        voteAverage = view.findViewById(R.id.vote_average_tv)
        releaseDateTv = view.findViewById(R.id.release_date_tv)
        image = view.findViewById(R.id.image)

        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.character.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindCharacter(it.data!!)
                    progressBar.visibility = View.GONE
                    container.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    container.visibility = View.GONE
                }
            }
        })
    }

    private fun bindCharacter(character: Movie) {
        originalTitleTv.text = character.original_title
        originalLanguageTv.text = character.original_language
        descriptionTv.text = character.overview
        popularityTv.text = character.popularity.toString()
        releaseDateTv.text = character.release_date
        titleTv.text = character.title
        voteAverage.text = character.vote_average.toString()
        voteCountTv.text = character.vote_count.toString()
        Glide.with(image.rootView)
            .load("$s${character.poster_path}")
            .into(image)
    }
}
