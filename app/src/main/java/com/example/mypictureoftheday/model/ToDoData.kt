package com.example.mypictureoftheday.model

data class ToDoData(
    var title: String,
    var task: String,
    var isComplete: Boolean = false,
    var isOpenETTask: Boolean = false,
) {
}