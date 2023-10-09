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
import com.test.dikshatek.mmv.databinding.ListItemActorBinding
import com.test.dikshatek.mmv.databinding.ListItemCategoryBinding
import com.test.dikshatek.mmv.databinding.ListItemMovieLandscapeBinding
import com.test.dikshatek.mmv.databinding.ListItemMoviePotraitBinding
import com.test.dikshatek.mmv.domain.model.MvCategory
import com.test.dikshatek.mmv.utils.GlobalUtils.getImageWithSize
import com.test.dikshatek.mmv.utils.ImageViewUtils.loadImageFromServer
import com.test.dikshatek.mmv.utils.ImageViewUtils.loadImageFromServerFullUrl
import com.test.dikshatek.mmv.utils.MvGenres
import com.test.dikshatek.mmv.utils.MvType

class MvCastAdapter : RecyclerView.Adapter<MvCastAdapter.ItemViewHolder>() {

    private var listData: List<CastItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<CastItem>) {
        listData = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_actor, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemActorBinding.bind(itemView)
        fun bind(data: CastItem) {
            with(binding) {
                ivImage.loadImageFromServer(getImageWithSize(1, data.profilePath.toString()))
                tvName.text = data.name
                tvJob.text = data.knownForDepartment
            }
        }
    }
}