package com.example.cinema_app.retrofit.dagger

import com.example.cinema_app.dagger.module.RetrofitModule
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.domain.ICinemaListInteractor
import io.mockk.mockk
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class TestRetrofitModule : RetrofitModule() {
    override fun provideOkHttpClient(): OkHttpClient = mockk()
    override fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit = mockk()
    override fun provideCinemaService(retrofit: Retrofit): CinemaService = mockk()
    override fun provideCinemaListInteractor(cinemaService: CinemaService): ICinemaListInteractor = mockk()
}