package com.test.dikshatek.mmv.domain.repository

import com.test.dikshatek.mmv.data.Resource
import com.test.dikshatek.mmv.data.remote.request.RequestMv
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseItemPopularMv
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseMovieGenre
import com.test.dikshatek.mmv.data.remote.response.movie.credits.ResponseDetailMovieCredits
import com.test.dikshatek.mmv.data.remote.response.movie.detail.ResponseDetailMovie
import com.test.dikshatek.mmv.data.remote.response.movie.reviews.ResponseDetailMovieReview
import com.test.dikshatek.mmv.data.remote.response.movie.videos.ResponseDetailMovieVideos
import kotlinx.coroutines.flow.Flow

interface IMvRepository {
    fun getListMvPopular(token: String, request: RequestMv): Flow<Resource<List<ResponseItemPopularMv>>>
    fun getListMvTopRate(token: String, request: RequestMv): Flow<Resource<List<ResponseItemPopularMv>>>
    fun getListMvUpComing(token: String, request: RequestMv): Flow<Resource<List<ResponseItemPopularMv>>>
    fun getListMv(token: String, request: RequestMv): Flow<Resource<List<ResponseItemPopularMv>>>
    fun getListMvGenre(token: String): Flow<Resource<ResponseMovieGenre>>
    fun getDetailMv(token: String, id: Int): Flow<Resource<ResponseDetailMovie>>
    fun getDetailMvCredits(token: String, id: Int): Flow<Resource<ResponseDetailMovieCredits>>
    fun getDetailMvVideos(token: String, id: Int): Flow<Resource<ResponseDetailMovieVideos>>
    fun getDetailMvReviews(token: String, id: Int): Flow<Resource<ResponseDetailMovieReview>>
}