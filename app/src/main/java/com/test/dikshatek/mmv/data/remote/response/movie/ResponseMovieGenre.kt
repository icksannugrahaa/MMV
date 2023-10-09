package com.test.dikshatek.mmv.data.remote.response.movie

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseMovieGenre(

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null

) : Parcelable