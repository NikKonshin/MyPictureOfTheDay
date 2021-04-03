package com.example.mypictureoftheday.material.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.mypictureoftheday.material.ui.fragments.EarthPictureFragment

private const val TODAY = "today"
private const val YESTERDAY = "yesterday"
private const val MY_DAY_BEFORE_YESTERDAY = "my day before yesterday"
private const val TODAY_FRAGMENT = 0
private const val YESTERDAY_FRAGMENT = 1
private const val MY_DAY_BEFORE_YESTERDAY_FRAGMENT = 2
private const val TODAY_NAME = "СЕГОДНЯ"
private const val YESTERDAY_NAME = "ВЧЕРА"
private const val MY_DAY_BEFORE_YESTERDAY_NAME = "ПОЗАВЧЕРА"

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(
        EarthPictureFragment.newInstance(TODAY),
        EarthPictureFragment.newInstance(YESTERDAY),
        EarthPictureFragment.newInstance(MY_DAY_BEFORE_YESTERDAY),
    )

    override fun getCount(): Int = fragments.size


    override fun getItem(position: Int): Fragment = when (position) {
        0 -> fragments[TODAY_FRAGMENT]
        1 -> fragments[YESTERDAY_FRAGMENT]
        2 -> fragments[MY_DAY_BEFORE_YESTERDAY_FRAGMENT]
        else -> fragments[MY_DAY_BEFORE_YESTERDAY_FRAGMENT]
    }

    override fun getPageTitle(position: Int): CharSequence? =
        when (position) {
            0 -> TODAY_NAME
            1 -> YESTERDAY_NAME
            2 -> MY_DAY_BEFORE_YESTERDAY_NAME
            else -> TODAY_NAME
        }
}