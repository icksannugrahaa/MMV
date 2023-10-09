package com.test.dikshatek.mmv.ui.movie.detail

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.test.dikshatek.mmv.BuildConfig
import com.test.dikshatek.mmv.R
import com.test.dikshatek.mmv.data.Resource
import com.test.dikshatek.mmv.data.remote.response.movie.GenresItem
import com.test.dikshatek.mmv.data.remote.response.movie.credits.CastItem
import com.test.dikshatek.mmv.data.remote.response.movie.credits.ResponseDetailMovieCredits
import com.test.dikshatek.mmv.data.remote.response.movie.detail.ResponseDetailMovie
import com.test.dikshatek.mmv.data.remote.response.movie.reviews.ResultsItem
import com.test.dikshatek.mmv.databinding.ActivityDetailMovieBinding
import com.test.dikshatek.mmv.databinding.ActivityListMovieBinding
import com.test.dikshatek.mmv.ui.base.BaseActivity
import com.test.dikshatek.mmv.ui.main.MvViewModel
import com.test.dikshatek.mmv.ui.movie.detail.adapter.MvCastAdapter
import com.test.dikshatek.mmv.ui.movie.detail.adapter.MvReviewAdapter
import com.test.dikshatek.mmv.ui.movie.list.ListMovieActivity
import com.test.dikshatek.mmv.utils.Constants
import com.test.dikshatek.mmv.utils.GlobalUtils
import com.test.dikshatek.mmv.utils.ImageViewUtils.loadImageFromServer
import com.test.dikshatek.mmv.utils.MvGenres
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieActivity : BaseActivity<ActivityDetailMovieBinding>() {

    private lateinit var castAdapter: MvCastAdapter
    private lateinit var reviewAdapter: MvReviewAdapter
    private lateinit var bottomSheets: BottomSheetBehavior<CardView>

    private val listMovieActor = ArrayList<CastItem>()
    private val listMovieReviews = ArrayList<ResultsItem>()
    private val vm: MvViewModel by viewModel()
    private var data: ResponseDetailMovie? = null
    private var movieId: Int = 0
    private var primaryUrl = ""

    fun getStartIntent(
        context: Context,
        id: Int
    ): Intent {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(Constants.ARGS_ID_MOVIE, id)
        return intent
    }

    override fun getActivityBinding(): ActivityDetailMovieBinding = ActivityDetailMovieBinding.inflate(layoutInflater)

    override fun setUp(savedInstanceState: Bundle?) {
        movieId = intent.getIntExtra(Constants.ARGS_ID_MOVIE, 0)
        initAdapter()
        initClick()
        getDetailMovie()


        with(binding) {
            with(viewHeader) {
                tvBack.visibility = View.VISIBLE
                tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                tvSearch.visibility = View.GONE
            }
            with(bottomSheet) {
                bottomSheets = BottomSheetBehavior.from(bottomSheetRefferal)
                bottomSheets.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        binding.layoutScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            val contentHeight = binding.viewSpace.height + 1
            val marginHeaderLayout =
                binding.viewSpace.layoutParams as ViewGroup.MarginLayoutParams
            if (scrollY in 0..contentHeight) {
                marginHeaderLayout.topMargin = -scrollY
                binding.viewSpace.layoutParams = marginHeaderLayout
            }
            if (scrollY > contentHeight) {
                marginHeaderLayout.topMargin = -contentHeight
                binding.viewSpace.layoutParams = marginHeaderLayout
            }
        })
    }

    private fun initClick() {
        with(binding) {
            ivBackground.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(primaryUrl)))
            }
            ivPlay.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(primaryUrl)))
            }
            viewHeader.tvBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun initAdapter() {
        with(binding) {
            castAdapter = MvCastAdapter()
            rvCredit.apply {
                adapter = castAdapter
                layoutManager = LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }

            reviewAdapter = MvReviewAdapter()
                bottomSheet.rvReview.apply {
                adapter = reviewAdapter
                layoutManager = LinearLayoutManager(this@DetailMovieActivity)
            }
        }
    }

    private fun getDetailMovie() {
        val liveData = vm.getDetailMv("Bearer ${BuildConfig.ACCESS_TOKEN}", movieId)
        liveData.observe(this@DetailMovieActivity) { data ->
            if (data != null) {
                when (data) {
                    is Resource.Loading<*> -> {
                        Log.d("TAG_GETC_LOAD", data.toString())
                    }

                    is Resource.Success<*> -> {
                        this.data = data.data
                        initDetailMovie()
                        getDetailMovieCredits()
                        getDetailMovieVideos()
                        getDetailMovieReviews()
                        liveData.removeObservers(this@DetailMovieActivity)
                    }

                    is Resource.Error<*> -> {
                        Log.d("TAG_GETC_ERR", data.message.toString())
                        liveData.removeObservers(this@DetailMovieActivity)
                    }

                    is Resource.Empty<*> -> {
                        Log.d("TAG_GETC_EMP", data.toString())
                        liveData.removeObservers(this@DetailMovieActivity)
                    }
                }
            }
        }
    }

    private fun getDetailMovieCredits() {
        val liveData = vm.getDetailMvCredits("Bearer ${BuildConfig.ACCESS_TOKEN}", movieId)
        liveData.observe(this@DetailMovieActivity) { data ->
            if (data != null) {
                when (data) {
                    is Resource.Loading<*> -> {
                        Log.d("TAG_GETC_LOAD", data.toString())
                    }

                    is Resource.Success<*> -> {
                        if(data.data?.cast?.isNotEmpty() == true) {
                            listMovieActor.clear()
                            listMovieActor.addAll(data.data.cast as List<CastItem>)
                        }
                        castAdapter.setData(listMovieActor)
                        liveData.removeObservers(this@DetailMovieActivity)
                    }

                    is Resource.Error<*> -> {
                        Log.d("TAG_GETC_ERR", data.message.toString())
                        liveData.removeObservers(this@DetailMovieActivity)
                    }

                    is Resource.Empty<*> -> {
                        Log.d("TAG_GETC_EMP", data.toString())
                        liveData.removeObservers(this@DetailMovieActivity)
                    }
                }
            }
        }
    }

    private fun getDetailMovieVideos() {
        val liveData = vm.getDetailMvVideos("Bearer ${BuildConfig.ACCESS_TOKEN}", movieId)
        liveData.observe(this@DetailMovieActivity) { data ->
            if (data != null) {
                when (data) {
                    is Resource.Loading<*> -> {
                        Log.d("TAG_GETC_LOAD", data.toString())
                    }

                    is Resource.Success<*> -> {
                        if(data.data?.results?.isNotEmpty() == true)
                            primaryUrl = "https://www.youtube.com/watch?v=${data.data.results[0]?.key}"
                        liveData.removeObservers(this@DetailMovieActivity)
                    }

                    is Resource.Error<*> -> {
                        Log.d("TAG_GETC_ERR", data.message.toString())
                        liveData.removeObservers(this@DetailMovieActivity)
                    }

                    is Resource.Empty<*> -> {
                        Log.d("TAG_GETC_EMP", data.toString())
                        liveData.removeObservers(this@DetailMovieActivity)
                    }
                }
            }
        }
    }

    private fun getDetailMovieReviews() {
        val liveData = vm.getDetailMvReviews("Bearer ${BuildConfig.ACCESS_TOKEN}", movieId)
        liveData.observe(this@DetailMovieActivity) { data ->
            if (data != null) {
                when (data) {
                    is Resource.Loading<*> -> {
                        Log.d("TAG_GETC_LOAD", data.toString())
                    }

                    is Resource.Success<*> -> {
                        if(data.data?.results?.isNotEmpty() == true) {
                            listMovieReviews.clear()
                            listMovieReviews.addAll(data.data.results as List<ResultsItem>)
                        }
                        reviewAdapter.setData(listMovieReviews)
                        binding.bottomSheet.layoutEmpty.visibility = View.GONE
                        binding.bottomSheet.rvReview.visibility = View.VISIBLE
                        liveData.removeObservers(this@DetailMovieActivity)
                    }

                    is Resource.Error<*> -> {
                        Log.d("TAG_GETC_ERR", data.message.toString())
                        binding.bottomSheet.layoutEmpty.visibility = View.VISIBLE
                        binding.bottomSheet.rvReview.visibility = View.GONE
                        liveData.removeObservers(this@DetailMovieActivity)
                    }

                    is Resource.Empty<*> -> {
                        Log.d("TAG_GETC_EMP", data.toString())
                        binding.bottomSheet.layoutEmpty.visibility = View.VISIBLE
                        binding.bottomSheet.rvReview.visibility = View.GONE
                        liveData.removeObservers(this@DetailMovieActivity)
                    }
                }
            }
        }
    }

    private fun initDetailMovie() {
        with(binding) {
            if(data != null) {
                var genresText = ""
                data?.genres?.forEachIndexed { index, genre ->
                    for (color in MvGenres.values()) {
                        if (genre?.id == color.genre) {
                            genresText += "${
                                color.name.lowercase().replaceFirstChar(Char::uppercase)
                                    .replace("_", " ")
                            }"
                            if (index < ((data?.genres?.size ?: 1) - 1))
                                genresText += ", "
                        }
                    }
                }
                var productionCompaniesText = ""
                data?.productionCompanies?.forEachIndexed { index, item ->
                    productionCompaniesText += item?.name
                    if (index < ((data?.productionCompanies?.size ?: 1) - 1))
                        productionCompaniesText += ", "
                }
                var languageText = ""
                data?.spokenLanguages?.forEachIndexed { index, item ->
                    languageText += item?.name
                    if (index < ((data?.spokenLanguages?.size ?: 1) - 1))
                        languageText += ", "
                }
                viewHeader.tvTitle.text = data?.title
                tvTitleMv.text = data?.title
                tvDuration.text = "${data?.runtime} Menit"
                tvGenre.text = genresText
                tvProductionCompany.text = productionCompaniesText
                tvLanguage.text = languageText
                rbMvRate.rating = data?.voteAverage?.div(2F) ?: 0F
                tvRate.text = "${(data?.voteAverage?.div(2F) ?: 0F).toInt()} dari 5"
                tvSinopsis.text = "${data?.overview}"
                ivBackground.loadImageFromServer(
                    GlobalUtils.getImageWithSize(
                        4,
                        data?.posterPath.toString()
                    ))
                ivImage.loadImageFromServer(GlobalUtils.getImageWithSize(
                    2,
                    data?.posterPath.toString()
                ))
            }
        }
    }

}