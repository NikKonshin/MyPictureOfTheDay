package com.example.mypictureoftheday.model.entity

import com.google.gson.annotations.Expose

data class ServerResponseData(
    @Expose val copyright: String?,
    @Expose val date: String?,
    @Expose val explanation: String?,
    @Expose val mediaType: String?,
    @Expose val title: String?,
    @Expose val url: String?,
    @Expose val hdurl: String?
)