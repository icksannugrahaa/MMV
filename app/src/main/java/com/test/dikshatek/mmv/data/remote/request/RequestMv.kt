package com.test.dikshatek.mmv.data.remote.request

import com.google.gson.annotations.SerializedName

data class RequestMv (
    @SerializedName("language")
    var language: String = "en-US",
    @SerializedName("page")
    var page: Int = 1,
    @SerializedName("region")
    var region: String = "",
    @SerializedName("sort_by")
    var sortBy: String = "popularity.desc",
    @SerializedName("with_genres")
    var withGenres: String = "",
    @SerializedName("include_adult")
    var includeAdult: Boolean = true,
    @SerializedName("include_video")
    var includeVideo: Boolean = true,
    @SerializedName("vote_count.gte")
    var voteCount: Int = 20
)