package com.example.movieapp.ui.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.movieapp.data.entities.Movie
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.di.s

class MovieAdapter(private val listener: MovieItemListener) :
    RecyclerView.Adapter<MovieViewHolder>() {
    interface MovieItemListener {
        fun onClickedMovie(movieId: Int)
    }

    private val items = ArrayList<Movie>()

    fun setItems(items: ArrayList<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: ItemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(items[position])
}

class MovieViewHolder(
    private val itemBinding: ItemMovieBinding,
    private val listener: MovieAdapter.MovieItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {
    private lateinit var movie: Movie

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Movie) {
        this.movie = item
        itemBinding.titleTv.text = item.title
        itemBinding.descriptionTv.text = item.overview
        itemBinding.trialerTv.text = item.original_language
        itemBinding.voteCountTv.text = item.vote_count.toString()
        Glide.with(itemBinding.root)
            .load("$s${item.poster_path}")
            .into(itemBinding.imageView)



    }

    override fun onClick(v: View?) {
        listener.onClickedMovie(movie.id)
    }
}

