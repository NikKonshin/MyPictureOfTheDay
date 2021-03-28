package com.example.mypictureoftheday.model.api
import com.example.mypictureoftheday.model.entity.ServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSource {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<ServerResponseData>

}