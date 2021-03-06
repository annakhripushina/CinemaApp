package com.example.cinema_app.dagger.module

import com.example.cinema_app.BuildConfig
import com.example.cinema_app.data.CinemaRepository
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.domain.CinemaListInteractor
import com.example.cinema_app.domain.ICinemaListInteractor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class RetrofitModule {
    companion object {
        var BASE_URL = "https://api.themoviedb.org/3/"
    }

    @Reusable
    @Provides
    open fun provideOkHttpClient(): OkHttpClient {
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
        return okHttpClient
    }

    @Reusable
    @Provides
    open fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Reusable
    @Provides
    open fun provideCinemaService(retrofit: Retrofit): CinemaService =
        retrofit.create(CinemaService::class.java)

    @Reusable
    @Provides
    open fun provideCinemaListInteractor(cinemaRepository: CinemaRepository): ICinemaListInteractor =
        CinemaListInteractor(cinemaRepository)
}