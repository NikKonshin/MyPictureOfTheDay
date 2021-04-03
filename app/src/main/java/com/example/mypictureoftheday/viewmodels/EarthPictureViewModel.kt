package com.example.mypictureoftheday.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypictureoftheday.BuildConfig
import com.example.mypictureoftheday.model.ApiHolder
import com.example.mypictureoftheday.model.entity.earth.PicturesEarthData
import com.example.mypictureoftheday.viewmodels.livedata.Data
import com.example.mypictureoftheday.viewmodels.livedata.LiveDataForEarthPicture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EarthPictureViewModel(
    private val liveData: MutableLiveData<LiveDataForEarthPicture> = MutableLiveData(),
    private val apiHolder: ApiHolder = ApiHolder(),
) : ViewModel() {

    fun getData(): LiveData<LiveDataForEarthPicture> {
        sendServerRequest()
        return liveData
    }

    private var data: String = ""

    fun setDate(date: String) {
        data = date
    }

    private fun sendServerRequest() {
        liveData.value = LiveDataForEarthPicture.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            Data.Error(Throwable("You need API key"))
        } else {
            apiHolder
                .getRetrofitImpl()
                .getPicturesData(data, apiKey)
                .enqueue(object :
                    Callback<List<PicturesEarthData>> {
                    override fun onResponse(
                        call: Call<List<PicturesEarthData>>,
                        response: Response<List<PicturesEarthData>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveData.value = LiveDataForEarthPicture.Success(response.body()!!)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveData.value = LiveDataForEarthPicture.Error(Throwable())
                            } else {
                                liveData.value =
                                    LiveDataForEarthPicture.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<PicturesEarthData>>, t: Throwable) {
                        liveData.value = LiveDataForEarthPicture.Error(t)
                    }
                })
        }
    }
}