package com.test.dikshatek.mmv.data.remote.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<Object>(
    @field:SerializedName("results")
    var data: Object,
    @field:SerializedName("total_pages")
    var totalPage: Int
)