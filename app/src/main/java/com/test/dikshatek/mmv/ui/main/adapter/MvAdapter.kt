package com.test.dikshatek.mmv.ui.main.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.test.dikshatek.mmv.R
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseItemPopularMv
import com.test.dikshatek.mmv.databinding.ListItemMovieLandscapeBinding
import com.test.dikshatek.mmv.databinding.ListItemMoviePotraitBinding
import com.test.dikshatek.mmv.utils.GlobalUtils.getImageWithSize
import com.test.dikshatek.mmv.utils.ImageViewUtils.loadImageFromServer
import com.test.dikshatek.mmv.utils.ImageViewUtils.loadImageFromServerFullUrl
import com.test.dikshatek.mmv.utils.MvGenres
import com.test.dikshatek.mmv.utils.MvType

class MvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClick: ((ResponseItemPopularMv) -> Unit)? = null
    private var listData: List<ResponseItemPopularMv> = ArrayList()
    private var layoutType: Int = MvType.LANDSCAPE.type

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<ResponseItemPopularMv>, type: Int) {
        layoutType = type
        listData = newData
        notifyDataSetChanged()
    }

    fun updateDataOnly(newData: List<ResponseItemPopularMv>, recyclerView: RecyclerView, type: Int) {
        layoutType = type
        val oldData = this.listData
        this.listData = newData

        if(newData.size > oldData.size){
            insertData(oldData.size,newData.size)
        }

        if(newData.size < oldData.size){
            deleteData(oldData.size,newData.size)
        }

        for (i in this.listData.indices) {
            when (type) {
                MvType.PORTRAIT.type -> {
                    if (recyclerView.findViewHolderForAdapterPosition(i) is ViewHolderPortraitMovie) {
                        val h = recyclerView.findViewHolderForAdapterPosition(i) as ViewHolderPortraitMovie
                        h.onBind(listData[i])
                    }
                }
                MvType.LANDSCAPE.type -> {
                    if (recyclerView.findViewHolderForAdapterPosition(i) is ViewHolderLandscapeMovie) {
                        val h = recyclerView.findViewHolderForAdapterPosition(i) as ViewHolderLandscapeMovie
                        h.onBind(listData[i])
                    }
                }
            }
        }
    }

    private fun insertData(old: Int, new: Int){
        notifyItemRangeInserted(old, new)
    }

    private fun deleteData(old: Int, new: Int){
        notifyItemRangeRemoved(new,old)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MvType.PORTRAIT.type -> {
                ViewHolderPortraitMovie(LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie_potrait, parent, false))
            }
            MvType.LANDSCAPE.type -> {
                ViewHolderLandscapeMovie(LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie_landscape, parent, false))
            }
            else -> throw IllegalArgumentException("Invalid type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MvType.PORTRAIT.type -> (holder as ViewHolderPortraitMovie).onBind(listData[position])
            MvType.LANDSCAPE.type -> (holder as ViewHolderLandscapeMovie).onBind(listData[position])
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (layoutType) {
            0 -> MvType.PORTRAIT.type
            1 -> MvType.LANDSCAPE.type
            else -> MvType.PORTRAIT.type
        }
    }

    inner class ViewHolderPortraitMovie(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemMoviePotraitBinding.bind(itemView)

        fun onBind(data: ResponseItemPopularMv) {
            with(binding) {
                var genresText = ""
                data.genreIds?.forEachIndexed { index, genre ->
                    for (color in MvGenres.values()) {
                        if(genre == color.genre) {
                            genresText += "${
                                color.name.lowercase().replaceFirstChar(Char::uppercase).replace("_", " ")
                            }"
                            if(index < (data.genreIds.size - 1))
                                genresText += ", "
                        }
                    }
                }
                Log.d("TAG_GENRE", data.genreIds.toString())
                Log.d("TAG_GENRE1", MvGenres.values().toString())
                ivImage.loadImageFromServerFullUrl(
                    getImageWithSize(2, data.posterPath.toString()), progressbar
                )
                tvMvTitle.text = data.title
                tvMvGenre.text = genresText
                rbMvRate.rating = data.voteAverage?.div(2F) ?: 0F
                tvMvRateTotal.text = "${data.voteCount} votes"

                root.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    inner class ViewHolderLandscapeMovie(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemMovieLandscapeBinding.bind(itemView)

        fun onBind(data: ResponseItemPopularMv) {
            with(binding) {
                var genresText = ""
                data.genreIds?.forEachIndexed { index, genre ->
                    for (color in MvGenres.values()) {
                        if(genre == color.genre) {
                            genresText += "${
                                color.name.lowercase().replaceFirstChar(Char::uppercase).replace("_", " ")
                            }"
                            if(index < (data.genreIds.size - 1))
                                genresText += ", "
                        }
                    }
                }
                ivImage.loadImageFromServerFullUrl(
                    getImageWithSize(2, data.posterPath.toString()), progressbar
                )
                tvMvTitle.text = data.title
                tvMvGenre.text = genresText
                rbMvRate.rating = data.voteAverage?.div(2F) ?: 0F
                tvMvRateTotal.text = "${data.voteCount} votes"

                root.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }
}