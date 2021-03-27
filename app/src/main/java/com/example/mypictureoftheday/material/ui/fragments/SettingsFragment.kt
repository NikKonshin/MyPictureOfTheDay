package com.example.mypictureoftheday.material.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<Chip>(checkedId).let {
                Toast.makeText(context, "Выбран ${it.id}", Toast.LENGTH_SHORT).show()

                when(it.text){
                    "Первая"-> {
                        Toast.makeText(context, "Выбран ${it.id}", Toast.LENGTH_SHORT).show()
                        CounterTheme.setTheme(R.style.Theme_MyPictureOfTheDay)
                    }

                    "Вторая"-> {
                        CounterTheme.setTheme(R.style.Theme_MyCustom)
                        Toast.makeText(context, "Выбран ${it.id}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
       val activity = activity as MainActivity
        activity.recreate()
    }

}