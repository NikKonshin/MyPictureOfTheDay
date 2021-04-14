package com.example.mypictureoftheday.material.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mypictureoftheday.R
import com.example.mypictureoftheday.material.ui.activity.MainActivity
import com.example.mypictureoftheday.model.theme.CounterTheme
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(context, "onViewCreated", Toast.LENGTH_SHORT).show()

        initChip(CounterTheme.myTheme)

        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<Chip>(checkedId).let {
                if (it != null) {
                    when (it.id) {
                        R.id.chip_standard_theme -> {
                            Toast.makeText(context, "Выбран ${it.id}", Toast.LENGTH_SHORT).show()
                            CounterTheme.setTheme(R.style.Theme_MyPictureOfTheDay)
                        }

                        R.id.chip_night_theme -> {
                            CounterTheme.setTheme(R.style.Theme_MyCustomNight)
                            Toast.makeText(context, "Выбран ${it.id}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun initChip(theme: Int) {
        when (theme) {
            R.style.Theme_MyPictureOfTheDay -> {
                chip_standard_theme.isChecked = true
                chip_night_theme.isChecked = false
            }
            R.style.Theme_MyCustomNight -> {
                chip_night_theme.isChecked = true
                chip_standard_theme.isChecked = false

            }

            else -> {
                chip_standard_theme.isChecked = true
                chip_night_theme.isChecked = false
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val activity = activity as MainActivity
        activity.recreate()
    }
}