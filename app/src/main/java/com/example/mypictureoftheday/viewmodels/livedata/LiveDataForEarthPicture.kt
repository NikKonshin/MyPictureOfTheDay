package com.example.mypictureoftheday.viewmodels.livedata

import com.example.mypictureoftheday.model.entity.earth.PicturesEarthData

sealed class LiveDataForEarthPicture {
    data class Success(val picturesEarthData: List<PicturesEarthData>) : LiveDataForEarthPicture()
    data class Error(val error: Throwable) : LiveDataForEarthPicture()
    data class Loading(val progress: Int?) : LiveDataForEarthPicture()
}