package com.example.mypictureoftheday.material.ui.activity

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mypictureoftheday.R
import com.example.mypictureoftheday.material.ui.fragments.MainFragment
import com.example.mypictureoftheday.model.theme.CounterTheme
import java.security.Key

private const val CUSTOM_THEME = "CUSTOM_THEME"
private const val STANDARD_THEME = "STANDARD_THEME"

class MainActivity : AppCompatActivity() {
    private var myTheme: Int = CounterTheme.myTheme

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        if (myTheme == 0){
            myTheme = R.style.Theme_MyPictureOfTheDay
        } else{
            theme.applyStyle(myTheme, true)
        }
        return theme
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }


//    mapOf<String, Int>(
//    STANDARD_THEME to R.style.Theme_MyPictureOfTheDay,
//    CUSTOM_THEME to R.style.Theme_MyCustom,
}