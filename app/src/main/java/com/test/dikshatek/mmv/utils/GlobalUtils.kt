package com.test.dikshatek.mmv.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.test.dikshatek.mmv.domain.model.MvCategory
import com.test.dikshatek.mmv.utils.Constants.BASE_URL_IMAGE
import com.test.dikshatek.mmv.utils.Constants.IMAGE_WIDTH_BIG
import com.test.dikshatek.mmv.utils.Constants.IMAGE_WIDTH_BIGGER
import com.test.dikshatek.mmv.utils.Constants.IMAGE_WIDTH_EXTRA_BIGGER
import com.test.dikshatek.mmv.utils.Constants.IMAGE_WIDTH_EXTRA_SMALL
import com.test.dikshatek.mmv.utils.Constants.IMAGE_WIDTH_NORMAL
import com.test.dikshatek.mmv.utils.Constants.IMAGE_WIDTH_SMALL
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

object GlobalUtils {

    fun generateMovieCategory() : List<MvCategory> {
        return MvGenres.values().map {
            MvCategory(
                name = it.name,
                data = it.genre,
                isSelected = false
            )
        }
    }

    fun getImageWithSize(size: Int = 2, url: String): String {
        var newUrl = BASE_URL_IMAGE
        when(size) {
            0 -> newUrl += IMAGE_WIDTH_EXTRA_SMALL
            1 -> newUrl += IMAGE_WIDTH_SMALL
            2 -> newUrl += IMAGE_WIDTH_NORMAL
            3 -> newUrl += IMAGE_WIDTH_BIG
            4 -> newUrl += IMAGE_WIDTH_BIGGER
            5 -> newUrl += IMAGE_WIDTH_EXTRA_BIGGER
        }
        return "$newUrl$url"
    }

    fun isConnectedToInternet(context: Context): Boolean {
        val connectivity = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null) for (i in info.indices) if (info[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }

    fun showToastShort(context: Context, message:String){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }

    fun showToastLong(context: Context, message:String){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }

    @SuppressLint("SimpleDateFormat")
    fun stringToDateFormatCustom(stringDate: String, format: String): String { //date time
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale("ID"))
        val formatter = SimpleDateFormat(format, Locale("ID"))
        return formatter.format(parser.parse(stringDate)).toString()
    }

}