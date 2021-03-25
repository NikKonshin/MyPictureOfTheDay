package com.example.mypictureoftheday.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypictureoftheday.BuildConfig
import com.example.mypictureoftheday.model.ApiHolder
import com.example.mypictureoftheday.model.Data
import com.example.mypictureoftheday.model.entity.ServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val liveData: MutableLiveData<Data> = MutableLiveData(),
    private val apiHolder: ApiHolder = ApiHolder(),
) : ViewModel() {

    fun getData(): LiveData<Data> {
        sendServerRequest()
        return liveData
    }

    private fun sendServerRequest() {
        liveData.value = Data.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            Data.Error(Throwable("You need API key"))
        } else {
            apiHolder
                .getRetrofitImpl()
                .getPictureOfTheDay(apiKey)
                .enqueue(object :
                    Callback<ServerResponseData> {
                    override fun onResponse(
                        call: Call<ServerResponseData>,
                        response: Response<ServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveData.value = Data.Success(response.body()!!)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveData.value = Data.Error(Throwable())
                            } else {
                                liveData.value =
                                    Data.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<ServerResponseData>, t: Throwable) {
                        liveData.value = Data.Error(t)
                    }
                })
        }
    }
}