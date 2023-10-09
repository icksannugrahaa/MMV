package com.test.dikshatek.mmv.data.repository

import com.test.dikshatek.mmv.data.NetworkOnlyResource
import com.test.dikshatek.mmv.data.Resource
import com.test.dikshatek.mmv.data.remote.RemoteDataSource
import com.test.dikshatek.mmv.data.remote.network.ApiResponse
import com.test.dikshatek.mmv.data.remote.request.RequestMv
import com.test.dikshatek.mmv.data.remote.response.BaseResponse
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseItemPopularMv
import com.test.dikshatek.mmv.data.remote.response.movie.ResponseMovieGenre
import com.test.dikshatek.mmv.data.remote.response.movie.credits.ResponseDetailMovieCredits
import com.test.dikshatek.mmv.data.remote.response.movie.detail.ResponseDetailMovie
import com.test.dikshatek.mmv.data.remote.response.movie.reviews.ResponseDetailMovieReview
import com.test.dikshatek.mmv.data.remote.response.movie.videos.ResponseDetailMovieVideos
import com.test.dikshatek.mmv.domain.repository.IMvRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MvRepository(
    private val remoteDataSource: RemoteDataSource
) : IMvRepository {
    override fun getListMvPopular(
        token: String,
        request: RequestMv
    ): Flow<Resource<List<ResponseItemPopularMv>>> =
        object: NetworkOnlyResource<List<ResponseItemPopularMv>, BaseResponse<List<ResponseItemPopularMv>>>() {
            override suspend fun createCall(): Flow<ApiResponse<BaseResponse<List<ResponseItemPopularMv>>>> = remoteDataSource.getListMvPopular(token, request)
            override fun transformData(param: BaseResponse<List<ResponseItemPopularMv>>): Flow<Resource<List<ResponseItemPopularMv>>> = flow {
                emit(Resource.Success(param.data, "success", param.totalPage))
            }
        }.asFlow()

    override fun getListMvTopRate(
        token: String,
        request: RequestMv
    ): Flow<Resource<List<ResponseItemPopularMv>>> =
        object: NetworkOnlyResource<List<ResponseItemPopularMv>, BaseResponse<List<ResponseItemPopularMv>>>() {
            override suspend fun createCall(): Flow<ApiResponse<BaseResponse<List<ResponseItemPopularMv>>>> = remoteDataSource.getListMvTopRate(token, request)
            override fun transformData(param: BaseResponse<List<ResponseItemPopularMv>>): Flow<Resource<List<ResponseItemPopularMv>>> = flow {
                emit(Resource.Success(param.data, "success", param.totalPage))
            }
        }.asFlow()

    override fun getListMvUpComing(
        token: String,
        request: RequestMv
    ): Flow<Resource<List<ResponseItemPopularMv>>> =
        object: NetworkOnlyResource<List<ResponseItemPopularMv>, BaseResponse<List<ResponseItemPopularMv>>>() {
            override suspend fun createCall(): Flow<ApiResponse<BaseResponse<List<ResponseItemPopularMv>>>> = remoteDataSource.getListMvUpComing(token, request)
            override fun transformData(param: BaseResponse<List<ResponseItemPopularMv>>): Flow<Resource<List<ResponseItemPopularMv>>> = flow {
                emit(Resource.Success(param.data, "success", param.totalPage))
            }
        }.asFlow()

    override fun getListMv(
        token: String,
        request: RequestMv
    ): Flow<Resource<List<ResponseItemPopularMv>>> =
        object: NetworkOnlyResource<List<ResponseItemPopularMv>, BaseResponse<List<ResponseItemPopularMv>>>() {
            override suspend fun createCall(): Flow<ApiResponse<BaseResponse<List<ResponseItemPopularMv>>>> = remoteDataSource.getListMv(token, request)
            override fun transformData(param: BaseResponse<List<ResponseItemPopularMv>>): Flow<Resource<List<ResponseItemPopularMv>>> = flow {
                emit(Resource.Success(param.data, "success", param.totalPage))
            }
        }.asFlow()

    override fun getListMvGenre(token: String): Flow<Resource<ResponseMovieGenre>> =
        object: NetworkOnlyResource<ResponseMovieGenre, ResponseMovieGenre>() {
            override suspend fun createCall(): Flow<ApiResponse<ResponseMovieGenre>> = remoteDataSource.getListMvGenre(token)
            override fun transformData(param: ResponseMovieGenre): Flow<Resource<ResponseMovieGenre>> = flow {
                emit(Resource.Success(param, "success", 1))
            }
        }.asFlow()

    override fun getDetailMv(token: String, id: Int): Flow<Resource<ResponseDetailMovie>> =
        object: NetworkOnlyResource<ResponseDetailMovie, ResponseDetailMovie>() {
            override suspend fun createCall(): Flow<ApiResponse<ResponseDetailMovie>> = remoteDataSource.getDetailMv(token, id)
            override fun transformData(param: ResponseDetailMovie): Flow<Resource<ResponseDetailMovie>> = flow {
                emit(Resource.Success(param, "success", 1))
            }
        }.asFlow()

    override fun getDetailMvCredits(
        token: String,
        id: Int
    ): Flow<Resource<ResponseDetailMovieCredits>> =
        object: NetworkOnlyResource<ResponseDetailMovieCredits, ResponseDetailMovieCredits>() {
            override suspend fun createCall(): Flow<ApiResponse<ResponseDetailMovieCredits>> = remoteDataSource.getDetailMvCredits(token, id)
            override fun transformData(param: ResponseDetailMovieCredits): Flow<Resource<ResponseDetailMovieCredits>> = flow {
                emit(Resource.Success(param, "success", 1))
            }
        }.asFlow()

    override fun getDetailMvVideos(
        token: String,
        id: Int
    ): Flow<Resource<ResponseDetailMovieVideos>> =
        object: NetworkOnlyResource<ResponseDetailMovieVideos, ResponseDetailMovieVideos>() {
            override suspend fun createCall(): Flow<ApiResponse<ResponseDetailMovieVideos>> = remoteDataSource.getDetailMvVideos(token, id)
            override fun transformData(param: ResponseDetailMovieVideos): Flow<Resource<ResponseDetailMovieVideos>> = flow {
                emit(Resource.Success(param, "success", 1))
            }
        }.asFlow()

    override fun getDetailMvReviews(
        token: String,
        id: Int
    ): Flow<Resource<ResponseDetailMovieReview>> =
        object: NetworkOnlyResource<ResponseDetailMovieReview, ResponseDetailMovieReview>() {
            override suspend fun createCall(): Flow<ApiResponse<ResponseDetailMovieReview>> = remoteDataSource.getDetailMvReviews(token, id)
            override fun transformData(param: ResponseDetailMovieReview): Flow<Resource<ResponseDetailMovieReview>> = flow {
                emit(Resource.Success(param, "success", 1))
            }
        }.asFlow()
}