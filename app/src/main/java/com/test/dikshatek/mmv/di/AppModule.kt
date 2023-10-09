package com.test.dikshatek.mmv.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.test.dikshatek.mmv.data.remote.RemoteDataSource
import com.test.dikshatek.mmv.data.remote.network.ApiService
import com.test.dikshatek.mmv.data.repository.MvRepository
import com.test.dikshatek.mmv.domain.repository.IMvRepository
import com.test.dikshatek.mmv.ui.main.MvViewModel
import com.test.dikshatek.mmv.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppModule {


    val viewModelModule = module {
        viewModel { MvViewModel(get()) }
    }

    val networkModule = module {
        single {
            OkHttpClient.Builder().also { client ->
//                if(BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client
                        .addInterceptor(logging)
                        .addInterceptor(
                            ChuckerInterceptor.Builder(get())
                                .collector(ChuckerCollector(get()))
                                .maxContentLength(250000L)
                                .redactHeaders(emptySet())
                                .alwaysReadResponseBody(false)
                                .build()
                        )
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
//                }
            }.build()
        }
        single {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
            retrofit.create(ApiService::class.java)
        }
    }

    val repositoryModule = module {
        single { RemoteDataSource(get(), get()) }
        single<IMvRepository> {
            MvRepository(
                get()
            )
        }
    }

}