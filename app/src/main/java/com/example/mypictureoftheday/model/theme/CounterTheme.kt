package com.example.mypictureoftheday.model.theme

object CounterTheme{
    var myTheme = 0
        private set

    fun setTheme(theme: Int) {
        myTheme = theme
    }
}