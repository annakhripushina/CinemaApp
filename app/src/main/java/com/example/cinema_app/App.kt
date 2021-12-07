package com.example.cinema_app

import android.app.Application
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.domain.CinemaListInteractor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {
        var BASE_URL = "https://api.themoviedb.org/3/"

        lateinit var instance: App
            private set
    }

    lateinit var cinemaInteractor: CinemaListInteractor
    lateinit var cinemaService: CinemaService


    override fun onCreate() {
        super.onCreate()
        instance = this

        initRetrofit()
        initInteractor()
    }

    private fun initRetrofit() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                return@addInterceptor chain.proceed(
                    chain
                        .request()
                        .newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NDIyYWM2NTk3N2UxMzM0OWM4YjVlYzg3OTIwNjQ3YSIsInN1YiI6IjYxYTc5Y2MxMTVjNjM2MDA0MzgwN2NhMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4_KgqDuRFn5emtljoAe5PYGEtcn3L4gYW2l6TlL1GEE"
                        )
                        .build()
                )
            }
            .addInterceptor(HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                })
            .build()

        cinemaService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(CinemaService::class.java)

    }

    private fun initInteractor() {
        cinemaInteractor = CinemaListInteractor(cinemaService)
    }

}



