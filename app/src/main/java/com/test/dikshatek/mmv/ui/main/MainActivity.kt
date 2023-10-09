package com.test.dikshatek.mmv.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.test.dikshatek.mmv.BuildConfig
import com.test.dikshatek.mmv.R
import com.test.dikshatek.mmv.data.Resource
import com.test.dikshatek.mmv.data.remote.request.RequestMv
import com.test.dikshatek.mmv.data.remote.response.movie.GenresItem
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseItemPopularMv
import com.test.dikshatek.mmv.databinding.ActivityMainBinding
import com.test.dikshatek.mmv.databinding.ListItemCarouselBinding
import com.test.dikshatek.mmv.ui.base.BaseActivity
import com.test.dikshatek.mmv.ui.main.adapter.MvAdapter
import com.test.dikshatek.mmv.ui.main.adapter.MvCategoryAdapter
import com.test.dikshatek.mmv.ui.movie.detail.DetailMovieActivity
import com.test.dikshatek.mmv.ui.movie.list.ListMovieActivity
import com.test.dikshatek.mmv.utils.GlobalUtils.getImageWithSize
import com.test.dikshatek.mmv.utils.MvType
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var mvPopularAdapter: MvAdapter
    private lateinit var mvTopRateAdapter: MvAdapter
    private lateinit var mvUpComingAdapter: MvAdapter
    private lateinit var mvCategoryAdapter: MvCategoryAdapter

    private val vm: MvViewModel by viewModel()
    private val listMvPopular = ArrayList<ResponseItemPopularMv>()
    private val listMvTopRate = ArrayList<ResponseItemPopularMv>()
    private val listMvUpComing = ArrayList<ResponseItemPopularMv>()

    override fun getActivityBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun setUp(savedInstanceState: Bundle?) {
        initClick()
        initAdapter()
        getMovieGenreList()
        getMovieList(0)
        getMovieList(1)
        getMovieList(2)
    }

    private fun initAdapter() {
        with(binding) {
            rvCarousel.apply {
                setIndicator(rvIndicator)
                carouselListener = object : CarouselListener {
                    override fun onCreateViewHolder(
                        layoutInflater: LayoutInflater,
                        parent: ViewGroup
                    ): ViewBinding {
                        return ListItemCarouselBinding.inflate(layoutInflater, parent, false)
                    }

                    override fun onBindViewHolder(
                        binding: ViewBinding,
                        item: CarouselItem,
                        position: Int
                    ) {
                        val currentBinding = binding as ListItemCarouselBinding

                        currentBinding.ivImage.apply {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImage(item, R.drawable.ic_no_image_grey_24)
                        }

                    }

                    override fun onClick(position: Int, carouselItem: CarouselItem) {
                        startActivity(DetailMovieActivity().getStartIntent(this@MainActivity, listMvPopular[position].id ?: 0))
                        super.onClick(position, carouselItem)
                    }
                }

                registerLifecycle(lifecycle)
            }

            mvCategoryAdapter = MvCategoryAdapter()
            mvCategoryAdapter.onItemClick = {
                startActivity(ListMovieActivity().getStartIntent(this@MainActivity, 0, it.id.toString(), "Popular Movie"))
            }
            rvCategory.apply {
                adapter = mvCategoryAdapter
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }

            mvPopularAdapter = MvAdapter()
            mvPopularAdapter.onItemClick = {
                startActivity(DetailMovieActivity().getStartIntent(this@MainActivity, it.id ?: 0))
            }
            rvMvPopular.apply {
                adapter = mvPopularAdapter
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }

            mvTopRateAdapter = MvAdapter()
            mvTopRateAdapter.onItemClick = {
                startActivity(DetailMovieActivity().getStartIntent(this@MainActivity, it.id ?: 0))
            }
            rvMvTopRate.apply {
                adapter = mvTopRateAdapter
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }

            mvUpComingAdapter = MvAdapter()
            mvUpComingAdapter.onItemClick = {
                startActivity(DetailMovieActivity().getStartIntent(this@MainActivity, it.id ?: 0))
            }
            rvMvUpcoming.apply {
                adapter = mvUpComingAdapter
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }
    }

    private fun getMovieList(type: Int) {
        val requestMvPopular = RequestMv(
            language = "en-US", page = 1, withGenres = ""
        )
        when(type) {
            0 -> requestMvPopular.sortBy = "popularity.desc"
            1 -> requestMvPopular.sortBy = "vote_average.desc"
            2 -> requestMvPopular.sortBy = "primary_release_date.desc"
            else -> requestMvPopular.sortBy = "popularity.desc"
        }
        val liveData =  vm.getListMv(
            "Bearer ${BuildConfig.ACCESS_TOKEN}", requestMvPopular
        )
        liveData.observe(this@MainActivity) { data ->
            if (data != null) {
                when (data) {
                    is Resource.Loading<*> -> {
                        Log.d("TAG_GETC_LOAD", data.toString())
                    }

                    is Resource.Success<*> -> {
                        if(data.data?.isNotEmpty() == true) {
                            when(type) {
                                0 -> {
                                    listMvPopular.clear()
                                    listMvPopular.addAll(data.data)
                                    mvPopularAdapter.setData(listMvPopular, MvType.LANDSCAPE.type)

                                    val listImage = ArrayList<CarouselItem>()
                                    listMvPopular.forEach {
                                        listImage.add(CarouselItem(imageUrl = getImageWithSize(3, it.posterPath.toString()), caption = it.title))
                                    }
                                    binding.rvCarousel.setData(listImage)
                                }
                                1 -> {
                                    listMvTopRate.clear()
                                    listMvTopRate.addAll(data.data)
                                    mvTopRateAdapter.setData(listMvTopRate, MvType.LANDSCAPE.type)
                                }
                                2 -> {
                                    listMvUpComing.clear()
                                    listMvUpComing.addAll(data.data)
                                    mvUpComingAdapter.setData(listMvUpComing, MvType.LANDSCAPE.type)
                                }
                            }
                        }
                        liveData.removeObservers(this@MainActivity)
                    }

                    is Resource.Error<*> -> {
                        Log.d("TAG_GETC_ERR", data.message.toString())
                        liveData.removeObservers(this@MainActivity)
                    }

                    is Resource.Empty<*> -> {
                        Log.d("TAG_GETC_EMP", data.toString())
                        liveData.removeObservers(this@MainActivity)
                    }
                }
            }
        }
    }

    private fun getMovieGenreList() {
        val liveData = vm.getListMvGenre("Bearer ${BuildConfig.ACCESS_TOKEN}")
        liveData.observe(this@MainActivity) { data ->
            if (data != null) {
                when (data) {
                    is Resource.Loading<*> -> {
                        Log.d("TAG_GETC_LOAD", data.toString())
                    }

                    is Resource.Success<*> -> {
                        if(data.data?.genres?.isNotEmpty() == true) {
                            mvCategoryAdapter.setData(data.data.genres as List<GenresItem>)
                        }
                        Log.d("TAG_GET_GEN", data.data.toString())

                        liveData.removeObservers(this@MainActivity)
                    }

                    is Resource.Error<*> -> {
                        Log.d("TAG_GETC_ERR", data.message.toString())
                        liveData.removeObservers(this@MainActivity)
                    }

                    is Resource.Empty<*> -> {
                        Log.d("TAG_GETC_EMP", data.toString())
                        liveData.removeObservers(this@MainActivity)
                    }
                }
            }
        }
    }

    private fun initClick() {
        with(binding) {
            tvSeePopular.setOnClickListener {
                startActivity(ListMovieActivity().getStartIntent(this@MainActivity, 0, title = "Popular Movie"))
            }
            tvSeeTopRate.setOnClickListener {
                startActivity(ListMovieActivity().getStartIntent(this@MainActivity, 1, title = "Top Rate Movie"))
            }
            tvSeeUpcoming.setOnClickListener {
                startActivity(ListMovieActivity().getStartIntent(this@MainActivity, 2, title = "Up Coming Movie"))
            }
        }
    }

}