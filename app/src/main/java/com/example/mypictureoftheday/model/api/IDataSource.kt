package com.example.mypictureoftheday.model.api
import com.example.mypictureoftheday.model.entity.earth.PicturesEarthData
import com.example.mypictureoftheday.model.entity.ServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IDataSource {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<ServerResponseData>

    @GET("EPIC/api/natural/date/{date}")
    fun getPicturesData(@Path("date") date: String, @Query("api_key") apiKey: String) : Call<List<PicturesEarthData>>
}