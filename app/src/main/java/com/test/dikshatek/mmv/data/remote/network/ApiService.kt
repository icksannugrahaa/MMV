package com.test.dikshatek.mmv.data.remote.network

import com.test.dikshatek.mmv.data.remote.response.BaseResponse
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseItemPopularMv
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseMovieGenre
import com.test.dikshatek.mmv.data.remote.response.movie.credits.ResponseDetailMovieCredits
import com.test.dikshatek.mmv.data.remote.response.movie.detail.ResponseDetailMovie
import com.test.dikshatek.mmv.data.remote.response.movie.reviews.ResponseDetailMovieReview
import com.test.dikshatek.mmv.data.remote.response.movie.videos.ResponseDetailMovieVideos
import com.test.dikshatek.mmv.utils.Constants.API_DETAIL_MOVIE
import com.test.dikshatek.mmv.utils.Constants.API_LIST_MOVIE
import com.test.dikshatek.mmv.utils.Constants.API_LIST_MOVIE_GENRE
import com.test.dikshatek.mmv.utils.Constants.API_LIST_MOVIE_POPULAR
import com.test.dikshatek.mmv.utils.Constants.API_LIST_MOVIE_TOP_RATED
import com.test.dikshatek.mmv.utils.Constants.API_LIST_MOVIE_UPCOMING
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(API_LIST_MOVIE_POPULAR)
    suspend fun listMvPopular(
        @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json",
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("region") region: String
    ): BaseResponse<List<ResponseItemPopularMv>>

    @GET(API_LIST_MOVIE_TOP_RATED)
    suspend fun listMvTopRate(
        @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json",
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("region") region: String
    ): BaseResponse<List<ResponseItemPopularMv>>

    @GET(API_LIST_MOVIE_UPCOMING)
    suspend fun listMvUpComing(
        @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json",
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("region") region: String
    ): BaseResponse<List<ResponseItemPopularMv>>

    @GET(API_LIST_MOVIE)
    suspend fun listMv(
        @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json",
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_genres") withGenres: String,
        @Query("vote_count.gte") voteCount: Int,
        @Query("include_video") includeVideo: Boolean,
        @Query("include_adult") includeAdult: Boolean
    ): BaseResponse<List<ResponseItemPopularMv>>

    @GET(API_LIST_MOVIE_GENRE)
    suspend fun listMvGenre(
        @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json"
    ): ResponseMovieGenre

    @GET("$API_DETAIL_MOVIE{movie_id}")
    suspend fun detailMv(
        @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json",
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): ResponseDetailMovie

    @GET("$API_DETAIL_MOVIE{movie_id}/credits")
    suspend fun detailMvCredits(
        @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json",
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): ResponseDetailMovieCredits

    @GET("$API_DETAIL_MOVIE{movie_id}/videos")
    suspend fun detailMvVideos(
        @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json",
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): ResponseDetailMovieVideos

    @GET("$API_DETAIL_MOVIE{movie_id}/reviews")
    suspend fun detailMvReviews(
        @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json",
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): ResponseDetailMovieReview
}