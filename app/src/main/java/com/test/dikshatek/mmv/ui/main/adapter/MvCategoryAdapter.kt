package com.test.dikshatek.mmv.ui.main.adapter

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
import com.test.dikshatek.mmv.databinding.ListItemCategoryBinding
import com.test.dikshatek.mmv.databinding.ListItemMovieLandscapeBinding
import com.test.dikshatek.mmv.databinding.ListItemMoviePotraitBinding
import com.test.dikshatek.mmv.domain.model.MvCategory
import com.test.dikshatek.mmv.utils.GlobalUtils.getImageWithSize
import com.test.dikshatek.mmv.utils.ImageViewUtils.loadImageFromServer
import com.test.dikshatek.mmv.utils.ImageViewUtils.loadImageFromServerFullUrl
import com.test.dikshatek.mmv.utils.MvGenres
import com.test.dikshatek.mmv.utils.MvType

class MvCategoryAdapter : RecyclerView.Adapter<MvCategoryAdapter.ItemViewHolder>() {

    private var listData: List<GenresItem> = ArrayList()
    private lateinit var selectedChip: MaterialButton
    var onItemClick: ((GenresItem) -> Unit?)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<GenresItem>) {
        listData = newData
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSelected(newListData: GenresItem) {
        if(listData.isNotEmpty()) {
            val check = listData.filter { it.name == newListData.name }
            if(check.isNotEmpty()) {
                check[0].isSelected = true
            }
        }
        notifyDataSetChanged()
    }

    private fun clearSelected() {
        if(listData.isNotEmpty())
            listData.forEach { it.isSelected = false }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_category, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemCategoryBinding.bind(itemView)
        fun bind(data: GenresItem) {
            with(binding) {
                btnCategory.text = data.name
                btnCategory.strokeColor = ContextCompat.getColorStateList(itemView.context, if(data.isSelected == true) R.color.black_main else R.color.white)
                btnCategory.setTextColor(ContextCompat.getColorStateList(itemView.context, if(data.isSelected == true) R.color.black_main else R.color.white))
                btnCategory.backgroundTintList = ContextCompat.getColorStateList(itemView.context, if(data.isSelected == true) R.color.white else R.color.black_main)
                btnCategory.setOnClickListener{
                    onItemClick?.invoke(data)
                }
            }
        }
    }
}