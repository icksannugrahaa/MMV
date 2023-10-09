package com.test.dikshatek.mmv.data.remote

import android.content.Context
import com.test.dikshatek.mmv.data.remote.network.ApiResponse
import com.test.dikshatek.mmv.data.remote.network.ApiService
import com.test.dikshatek.mmv.data.remote.request.RequestMv
import com.test.dikshatek.mmv.data.remote.response.BaseResponse
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseItemPopularMv
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseMovieGenre
import com.test.dikshatek.mmv.data.remote.response.movie.credits.ResponseDetailMovieCredits
import com.test.dikshatek.mmv.data.remote.response.movie.detail.ResponseDetailMovie
import com.test.dikshatek.mmv.data.remote.response.movie.reviews.ResponseDetailMovieReview
import com.test.dikshatek.mmv.data.remote.response.movie.videos.ResponseDetailMovieVideos
import com.test.dikshatek.mmv.utils.GlobalUtils.isConnectedToInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class RemoteDataSource(private val apiService: ApiService, private val context: Context) {

    suspend fun getListMvPopular(token: String, request: RequestMv): Flow<ApiResponse<BaseResponse<List<ResponseItemPopularMv>>>> =
        flow {
            try {
                if (isConnectedToInternet(context)) {
                    emit(ApiResponse.Success(apiService.listMvPopular(token = token, language = request.language, page = request.page, region = request.region)))
                } else
                    emit(ApiResponse.Error("Connection error", "-1"))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.response()?.errorBody()?.string().toString(), "05"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString(), "-1"))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getListMvTopRate(token: String, request: RequestMv): Flow<ApiResponse<BaseResponse<List<ResponseItemPopularMv>>>> =
        flow {
            try {
                if (isConnectedToInternet(context)) {
                    emit(ApiResponse.Success(apiService.listMvTopRate(token = token, language = request.language, page = request.page, region = request.region)))
                } else
                    emit(ApiResponse.Error("Connection error", "-1"))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.response()?.errorBody()?.string().toString(), "05"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString(), "-1"))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getListMvUpComing(token: String, request: RequestMv): Flow<ApiResponse<BaseResponse<List<ResponseItemPopularMv>>>> =
        flow {
            try {
                if (isConnectedToInternet(context)) {
                    emit(ApiResponse.Success(apiService.listMvUpComing(token = token, language = request.language, page = request.page, region = request.region)))
                } else
                    emit(ApiResponse.Error("Connection error", "-1"))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.response()?.errorBody()?.string().toString(), "05"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString(), "-1"))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getListMv(token: String, request: RequestMv): Flow<ApiResponse<BaseResponse<List<ResponseItemPopularMv>>>> =
        flow {
            try {
                if (isConnectedToInternet(context)) {
                    emit(ApiResponse.Success(apiService.listMv(token = token, language = request.language, page = request.page, includeAdult = request.includeAdult, includeVideo = request.includeVideo, sortBy = request.sortBy, withGenres = request.withGenres, voteCount = request.voteCount)))
                } else
                    emit(ApiResponse.Error("Connection error", "-1"))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.response()?.errorBody()?.string().toString(), "05"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString(), "-1"))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getListMvGenre(token: String): Flow<ApiResponse<ResponseMovieGenre>> =
        flow {
            try {
                if (isConnectedToInternet(context)) {
                    emit(ApiResponse.Success(apiService.listMvGenre(token = token)))
                } else
                    emit(ApiResponse.Error("Connection error", "-1"))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.response()?.errorBody()?.string().toString(), "05"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString(), "-1"))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailMv(token: String, id: Int): Flow<ApiResponse<ResponseDetailMovie>> =
        flow {
            try {
                if (isConnectedToInternet(context)) {
                    emit(ApiResponse.Success(apiService.detailMv(token = token, movieId = id)))
                } else
                    emit(ApiResponse.Error("Connection error", "-1"))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.response()?.errorBody()?.string().toString(), "05"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString(), "-1"))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailMvCredits(token: String, id: Int): Flow<ApiResponse<ResponseDetailMovieCredits>> =
        flow {
            try {
                if (isConnectedToInternet(context)) {
                    emit(ApiResponse.Success(apiService.detailMvCredits(token = token, movieId = id)))
                } else
                    emit(ApiResponse.Error("Connection error", "-1"))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.response()?.errorBody()?.string().toString(), "05"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString(), "-1"))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailMvVideos(token: String, id: Int): Flow<ApiResponse<ResponseDetailMovieVideos>> =
        flow {
            try {
                if (isConnectedToInternet(context)) {
                    emit(ApiResponse.Success(apiService.detailMvVideos(token = token, movieId = id)))
                } else
                    emit(ApiResponse.Error("Connection error", "-1"))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.response()?.errorBody()?.string().toString(), "05"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString(), "-1"))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailMvReviews(token: String, id: Int): Flow<ApiResponse<ResponseDetailMovieReview>> =
        flow {
            try {
                if (isConnectedToInternet(context)) {
                    emit(ApiResponse.Success(apiService.detailMvReviews(token = token, movieId = id, page = 1)))
                } else
                    emit(ApiResponse.Error("Connection error", "-1"))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.response()?.errorBody()?.string().toString(), "05"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString(), "-1"))
            }
        }.flowOn(Dispatchers.IO)

}