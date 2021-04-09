package com.example.mypictureoftheday.material.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mypictureoftheday.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_navigation_layout.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_navigation_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> {
                    fragmentManager?.beginTransaction()
                        ?.replace(R.id.container, MainEarthFragment())?.commit()
                    Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                }
                R.id.navigation_two -> {
                    fragmentManager?.beginTransaction()?.replace(R.id.container, MainFragment())
                        ?.commit()
                }
                R.id.navigation_three -> {
                    fragmentManager?.beginTransaction()?.replace(R.id.container, ToDoListFragment())
                        ?.commit()
                }
            }
            true
        }
    }
}