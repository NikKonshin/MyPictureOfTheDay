package com.example.mypictureoftheday.material.ui.activity

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mypictureoftheday.R
import com.example.mypictureoftheday.material.ui.fragments.MainFragment
import com.example.mypictureoftheday.material.ui.fragments.SplashFragment
import com.example.mypictureoftheday.model.theme.CounterTheme

class MainActivity : AppCompatActivity() {
    private var myTheme: Int = CounterTheme.myTheme

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        if (myTheme == 0) {
            myTheme = R.style.Theme_MyPictureOfTheDay
        } else {
            theme.applyStyle(myTheme, true)
        }
        return theme
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SplashFragment())
                .commitNow()
        }
    }

}