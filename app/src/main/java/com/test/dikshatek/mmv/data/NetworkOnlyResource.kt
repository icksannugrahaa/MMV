package com.test.dikshatek.mmv.data

import com.test.dikshatek.mmv.data.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkOnlyResource<ResultType, RequestType> {
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emitAll(transformData(apiResponse.data).map { it })
            }
            is ApiResponse.Empty -> {
                emit(Resource.Empty<ResultType>("Empty Data!"))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error<ResultType>(apiResponse.message, apiResponse.code))
            }
        }
    }
    protected abstract fun transformData(param : RequestType): Flow<Resource<ResultType>>
    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>
    fun asFlow(): Flow<Resource<ResultType>> = result
}