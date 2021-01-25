package com.project.surveyapps.retrofit

import com.project.surveyapps.response.ResponseSurvey
import retrofit2.Call
import retrofit2.http.*

interface DataService {

    @GET("pertanyaan/?key=test")
    fun getQuestion(): Call<ResponseSurvey>

    @FormUrlEncoded
    @POST("jawaban/?key=test")
    fun postSurvey(
        @Field("idpertanyaan") idpertanyaan: String,
        @Field("idjawaban") idjawaban: String,
        @Field("komen") komen: String
    ): Call<ResponseSurvey>


}

