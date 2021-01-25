package com.project.surveyapps.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    fun apiRequest(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://apis.jaylangkung.co.id/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}