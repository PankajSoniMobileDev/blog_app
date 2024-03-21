package com.myblog.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-dev.rupyz.com/") //base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
