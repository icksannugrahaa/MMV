package com.test.dikshatek.mmv.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.test.dikshatek.mmv.data.remote.request.RequestMv
import com.test.dikshatek.mmv.domain.repository.IMvRepository

class MvViewModel(private val repository: IMvRepository) : ViewModel() {
    fun getListMvPopular(token: String, request: RequestMv) = repository.getListMvPopular(token, request).asLiveData()
    fun getListMvTopRate(token: String, request: RequestMv) = repository.getListMvTopRate(token, request).asLiveData()
    fun getListMvUpComing(token: String, request: RequestMv) = repository.getListMvUpComing(token, request).asLiveData()
    fun getListMv(token: String, request: RequestMv) = repository.getListMv(token, request).asLiveData()
    fun getListMvGenre(token: String) = repository.getListMvGenre(token).asLiveData()
    fun getDetailMv(token: String, id: Int) = repository.getDetailMv(token, id).asLiveData()
    fun getDetailMvCredits(token: String, id: Int) = repository.getDetailMvCredits(token, id).asLiveData()
    fun getDetailMvVideos(token: String, id: Int) = repository.getDetailMvVideos(token, id).asLiveData()
    fun getDetailMvReviews(token: String, id: Int) = repository.getDetailMvReviews(token, id).asLiveData()
}