package com.test.dikshatek.mmv.utils

object Constants {
    //API
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/"

    //IMAGE WIDTH THEMOVIEDB
    const val IMAGE_WIDTH_EXTRA_SMALL = "w92/"
    const val IMAGE_WIDTH_SMALL = "w154/"
    const val IMAGE_WIDTH_NORMAL = "w185/"
    const val IMAGE_WIDTH_BIG = "w342/"
    const val IMAGE_WIDTH_BIGGER = "w500/"
    const val IMAGE_WIDTH_EXTRA_BIGGER = "w780/"

    //SUB URL
    const val API_LIST_MOVIE_POPULAR = "movie/popular"
    const val API_LIST_MOVIE_TOP_RATED = "movie/top_rated"
    const val API_LIST_MOVIE_UPCOMING = "movie/upcoming"
    const val API_LIST_MOVIE = "discover/movie"
    const val API_LIST_MOVIE_GENRE = "genre/movie/list"
    const val API_DETAIL_MOVIE = "movie/"

    //ARGS
    const val ARGS_TYPE_MOVIE = "ARGS_TYPE_MOVIE"
    const val ARGS_GENRE_MOVIE = "ARGS_GENRE_MOVIE"
    const val ARGS_ID_MOVIE = "ARGS_ID_MOVIE"
    const val ARGS_TITLE = "ARGS_TITLE"

}