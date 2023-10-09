package com.test.dikshatek.mmv.ui.movie.detail.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.button.MaterialButton
import com.test.dikshatek.mmv.R
import com.test.dikshatek.mmv.data.remote.response.movie.GenresItem
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseItemPopularMv
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseMovieGenre
import com.test.dikshatek.mmv.data.remote.response.movie.credits.CastItem
import com.test.dikshatek.mmv.data.remote.response.movie.reviews.ResultsItem
import com.test.dikshatek.mmv.databinding.ListItemActorBinding
import com.test.dikshatek.mmv.databinding.ListItemCategoryBinding
import com.test.dikshatek.mmv.databinding.ListItemMovieLandscapeBinding
import com.test.dikshatek.mmv.databinding.ListItemMoviePotraitBinding
import com.test.dikshatek.mmv.databinding.ListItemReviewBinding
import com.test.dikshatek.mmv.domain.model.MvCategory
import com.test.dikshatek.mmv.utils.GlobalUtils.getImageWithSize
import com.test.dikshatek.mmv.utils.GlobalUtils.stringToDateFormatCustom
import com.test.dikshatek.mmv.utils.ImageViewUtils.loadImageFromServer
import com.test.dikshatek.mmv.utils.ImageViewUtils.loadImageFromServerFullUrl
import com.test.dikshatek.mmv.utils.MvGenres
import com.test.dikshatek.mmv.utils.MvType

class MvReviewAdapter : RecyclerView.Adapter<MvReviewAdapter.ItemViewHolder>() {

    private var listData: List<ResultsItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<ResultsItem>) {
        listData = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_review, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemReviewBinding.bind(itemView)
        fun bind(data: ResultsItem) {
            with(binding) {
                ivUser.loadImageFromServer(getImageWithSize(1, data.authorDetails?.avatarPath.toString()))
                tvName.text = data.authorDetails?.name
                tvReview.text = data.content
                tvDate.text = stringToDateFormatCustom(data.createdAt.toString(), "DD-MM-YYYY")
                rbMvRate.rating = data.authorDetails?.rating?.div(2F) ?: 0F
            }
        }
    }
}