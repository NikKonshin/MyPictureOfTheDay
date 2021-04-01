package com.example.mypictureoftheday.material.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.example.mypictureoftheday.R
import com.example.mypictureoftheday.model.entity.MyDate
import com.example.mypictureoftheday.viewmodels.EarthPictureViewModel
import com.example.mypictureoftheday.viewmodels.livedata.LiveDataForEarthPicture
import kotlinx.android.synthetic.main.fragment_earth_picture.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*


class EarthPictureFragment : Fragment() {

    companion object {
        private const val TODAY = "today"
        private const val YESTERDAY = "yesterday"
        private const val MY_DAY_BEFORE_YESTERDAY = "my day before yesterday"
        private const val ID_KEY = "id_key"

        fun newInstance(idDay: String) =
            EarthPictureFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_KEY, idDay)
                }
            }
    }

    private var day = ""
    private var mounth = ""
    private var year = ""
    private var data = ""

    private val viewModel: EarthPictureViewModel by lazy {
        ViewModelProviders.of(this).get(EarthPictureViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id: String =
            if (arguments?.getString(ID_KEY) == null) {
                MY_DAY_BEFORE_YESTERDAY
            } else {
                arguments?.getString(ID_KEY)!!
            }

        getDay(id)
        viewModel.setDate(data)
        viewModel.getData()
            .observe(
                this@EarthPictureFragment,
                Observer<LiveDataForEarthPicture> { renderData(it) })
    }

    private fun renderData(liveData: LiveDataForEarthPicture) {
        when (liveData) {
            is LiveDataForEarthPicture.Success -> {
                var uri =
                    "https://api.nasa.gov/EPIC/archive/natural/2019/05/30/png/epic_1b_20190530011359.png?api_key=DEMO_KEY"
                if (liveData.picturesEarthData.isNotEmpty()) {
                    uri = "https://api.nasa.gov/EPIC/archive/natural/" +
                            "${year}/" +
                            "${mounth}/" +
                            "${day}" +
                            "/png/${liveData.picturesEarthData[0].image}.png?api_key=DEMO_KEY"
                    Log.v("TAGGG", uri)
                }

                image_view_earth_fragment.load(uri) {
                    lifecycle(this@EarthPictureFragment)
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_no_photo_vector)
                }
            }
            is LiveDataForEarthPicture.Loading -> {
                Log.v("TAGGG", "${liveData.progress}")
            }
            is LiveDataForEarthPicture.Error -> {
                Log.v("TAGGG", "${liveData.error.message}")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    private fun getDay(id: String) {
        when (id) {
            TODAY -> {
                day = MyDate.MyToday.day
                mounth = MyDate.MyToday.month
                year = MyDate.MyToday.year
                data = MyDate.MyToday.dateString
            }
            YESTERDAY -> {
                day = MyDate.MyYesterday.day
                mounth = MyDate.MyYesterday.month
                year = MyDate.MyYesterday.year
                data = MyDate.MyYesterday.dateString
            }
            MY_DAY_BEFORE_YESTERDAY -> {
                day = MyDate.MyDayBeforeYesterday.day
                mounth = MyDate.MyDayBeforeYesterday.month
                year = MyDate.MyDayBeforeYesterday.year
                data = MyDate.MyDayBeforeYesterday.dateString
            }
        }
    }
}