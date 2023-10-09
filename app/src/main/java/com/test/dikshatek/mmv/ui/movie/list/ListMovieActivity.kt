package com.test.dikshatek.mmv.ui.movie.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.dikshatek.mmv.BuildConfig
import com.test.dikshatek.mmv.data.Resource
import com.test.dikshatek.mmv.data.remote.request.RequestMv
import com.test.dikshatek.mmv.data.remote.response.movie.GenresItem
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseItemPopularMv
import com.test.dikshatek.mmv.databinding.ActivityListMovieBinding
import com.test.dikshatek.mmv.ui.base.BaseActivity
import com.test.dikshatek.mmv.ui.main.MvViewModel
import com.test.dikshatek.mmv.ui.main.adapter.MvAdapter
import com.test.dikshatek.mmv.ui.main.adapter.MvCategoryAdapter
import com.test.dikshatek.mmv.ui.movie.detail.DetailMovieActivity
import com.test.dikshatek.mmv.utils.Constants
import com.test.dikshatek.mmv.utils.Constants.ARGS_GENRE_MOVIE
import com.test.dikshatek.mmv.utils.Constants.ARGS_TITLE
import com.test.dikshatek.mmv.utils.MvType
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMovieActivity : BaseActivity<ActivityListMovieBinding>() {

    private lateinit var mvAdapter: MvAdapter
    private lateinit var mvCategoryAdapter: MvCategoryAdapter

    private val vm: MvViewModel by viewModel()
    private val listMv = ArrayList<ResponseItemPopularMv>()
    private val listCategory = ArrayList<GenresItem>()
    private var page = 1
    private var totalPage = 1
    private var isLoadMore = false
    private var mvType = 0
    private var genresDump = ""
    private var genres = ""

    fun getStartIntent(
        context: Context,
        type: Int,
        genre: String = "",
        title: String = "Popular Movie"
    ): Intent {
        val intent = Intent(context, ListMovieActivity::class.java)
        intent.putExtra(Constants.ARGS_TYPE_MOVIE, type)
        intent.putExtra(ARGS_GENRE_MOVIE, genre)
        intent.putExtra(ARGS_TITLE, title)
        return intent
    }

    override fun getActivityBinding(): ActivityListMovieBinding = ActivityListMovieBinding.inflate(layoutInflater)

    override fun setUp(savedInstanceState: Bundle?) {
        mvType = intent.getIntExtra(Constants.ARGS_TYPE_MOVIE, 0)
        genresDump = intent.getStringExtra(ARGS_GENRE_MOVIE) ?: ""

        initAdapter()
        getMovieGenreList()

        with(binding.viewHeader) {
            tvBack.visibility = View.VISIBLE
            tvTitle.text = intent.getStringExtra(ARGS_TITLE) ?: "Popular Movie"
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            tvSearch.visibility = View.GONE
            tvBack.setOnClickListener {
                finish()
            }
        }
        binding.layoutSwipe.setOnRefreshListener {
            page = 1
            genresDump = ""
            getMovieList(mvType)
        }
    }

    private fun filter() {
        if(genresDump != "") {
            val check = listCategory.filter { category -> category.id.toString() == genresDump }
            if(check.isNotEmpty()) {
                check[0].isSelected = check[0].isSelected != true
            }
            mvCategoryAdapter.setData(listCategory)
            genresDump = ""
        }
        genres = ""
        val checked = listCategory.filter { it.isSelected == true }
        if(checked.isNotEmpty()) {
            checked.forEachIndexed { index, genresItem ->
                genres += genresItem.id.toString()
                if(index < (checked.size - 1))
                    genres += ", "
            }
        }
        getMovieList(mvType)
    }

    private fun getMovieGenreList() {
        val liveData = vm.getListMvGenre("Bearer ${BuildConfig.ACCESS_TOKEN}")
        liveData.observe(this@ListMovieActivity) { data ->
            if (data != null) {
                when (data) {
                    is Resource.Loading<*> -> {
                        Log.d("TAG_GETC_LOAD", data.toString())
                    }

                    is Resource.Success<*> -> {
                        binding.layoutSwipe.isRefreshing = false
                        if(data.data?.genres?.isNotEmpty() == true) {
                            listCategory.clear()
                            listCategory.addAll(data.data.genres as List<GenresItem>)
                        }
                        if(genresDump != "")
                            filter()
                        else
                            getMovieList(mvType)
                        liveData.removeObservers(this@ListMovieActivity)
                    }

                    is Resource.Error<*> -> {
                        Log.d("TAG_GETC_ERR", data.message.toString())
                        liveData.removeObservers(this@ListMovieActivity)
                    }

                    is Resource.Empty<*> -> {
                        Log.d("TAG_GETC_EMP", data.toString())
                        liveData.removeObservers(this@ListMovieActivity)
                    }
                }
            }
        }
    }

    private fun getMovieList(type: Int) {
        val requestMvPopular = RequestMv(
            language = "en-US", page = page, withGenres = genres
        )
        when(type) {
            0 -> requestMvPopular.sortBy = "popularity.desc"
            1 -> requestMvPopular.sortBy = "vote_average.desc"
            2 -> requestMvPopular.sortBy = "primary_release_date.desc"
            else -> requestMvPopular.sortBy = "popularity.desc"
        }
        val liveData =  vm.getListMv(
            "Bearer ${BuildConfig.ACCESS_TOKEN}", requestMvPopular)
        liveData.observe(this@ListMovieActivity) { data ->
            if (data != null) {
                when (data) {
                    is Resource.Loading<*> -> {
                        Log.d("TAG_GETC_LOAD", data.toString())
                    }

                    is Resource.Success<*> -> {
                        binding.layoutSwipe.isRefreshing = false
                        binding.progressBar.visibility = View.GONE
                        if(data.data?.isNotEmpty() == true) {
                            totalPage = data.totalPages ?: 1
                            var isNewData = true
                            if(listMv.isNotEmpty())
                                isNewData = false

                            if (!isLoadMore) {
                                listMv.clear()
                            }

                            listMv.addAll(data.data)

                            if (!isLoadMore) {
                                if (isNewData)
                                    mvAdapter.setData(listMv, MvType.PORTRAIT.type)
                                else
                                    mvAdapter.updateDataOnly(
                                        listMv,
                                        binding.rvListMv,
                                        MvType.PORTRAIT.type
                                    )
                            } else
                                isLoadMore = false
                            binding.rvListMv.visibility = View.VISIBLE
                            binding.emptyState.visibility = View.GONE
                        } else {
                            binding.rvListMv.visibility = View.GONE
                            binding.emptyState.visibility = View.VISIBLE
                        }
                        liveData.removeObservers(this@ListMovieActivity)
                    }

                    is Resource.Error<*> -> {
                        Log.d("TAG_GETC_ERR", data.message.toString())
                        binding.rvListMv.visibility = View.GONE
                        binding.emptyState.visibility = View.VISIBLE
                        liveData.removeObservers(this@ListMovieActivity)
                    }

                    is Resource.Empty<*> -> {
                        Log.d("TAG_GETC_EMP", data.toString())
                        binding.rvListMv.visibility = View.GONE
                        binding.emptyState.visibility = View.VISIBLE
                        liveData.removeObservers(this@ListMovieActivity)
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        with(binding) {

            mvCategoryAdapter = MvCategoryAdapter()
            mvCategoryAdapter.onItemClick = {
                genresDump = it.id.toString()
                filter()
            }
            rvCategory.apply {
                adapter = mvCategoryAdapter
                layoutManager = LinearLayoutManager(this@ListMovieActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
//            mvCategoryAdapter.setData(generateMovieCategory())

            mvAdapter = MvAdapter()
            mvAdapter.onItemClick = {
                startActivity(DetailMovieActivity().getStartIntent(this@ListMovieActivity, it.id ?: 0))
            }
            rvListMv.apply {
                adapter = mvAdapter
                layoutManager = GridLayoutManager(this@ListMovieActivity, 2, GridLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }

            rvListMv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) { // only when scrolling up
                        val visibleThreshold = 1
                        val layoutManager = recyclerView.layoutManager as GridLayoutManager
                        val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                        val currentTotalCount = layoutManager.itemCount
                        Log.d("TAG_CURRENT", (lastItem + visibleThreshold).toString())
                        Log.d("TAG_CURRENT1", (currentTotalCount).toString())
                        Log.d("TAG_CURRENT2", (listMv.size).toString())
                        if(isLoadMore)
                            binding.progressBar.visibility = View.VISIBLE
                        if ((lastItem + visibleThreshold) == currentTotalCount && !isLoadMore) {
                            if (page < totalPage) {
                                page++
                                isLoadMore = true
                                getMovieList(mvType)
                                binding.progressBar.visibility = View.VISIBLE
                            }
                        }
                    }
                    if(dy < 0)
                        binding.progressBar.visibility = View.GONE
                }
            })
        }
    }

}