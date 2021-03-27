package com.example.mypictureoftheday.viewmodels.livedata

import com.example.mypictureoftheday.model.entity.ServerResponseData

sealed class Data {
    data class Success(val serverResponseData: ServerResponseData) : Data()
    data class Error(val error: Throwable) : Data()
    data class Loading(val progress: Int?) : Data()
}