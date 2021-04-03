package com.example.mypictureoftheday.material.ui.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.*
import coil.api.load
import com.example.mypictureoftheday.BuildConfig
import com.example.mypictureoftheday.R
import com.example.mypictureoftheday.material.ui.activity.MainActivity
import com.example.mypictureoftheday.model.entity.MyDate
import com.example.mypictureoftheday.viewmodels.EarthPictureViewModel
import com.example.mypictureoftheday.viewmodels.livedata.LiveDataForEarthPicture
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_earth_picture.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.bottom_app_bar
import kotlinx.android.synthetic.main.fragment_main_earth.*
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
    private var isExpanded = false

    private var activity: MainActivity? = null
    private var tab: TabLayout? = null
    private var fab: FloatingActionButton? = null
    private var params: ViewGroup.LayoutParams? = null
    private var coordinatorLayout: CoordinatorLayout.LayoutParams? = null
    private var bottomBar: BottomAppBar? = null

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProperty()
        initAnimation()
    }

    private fun renderData(liveData: LiveDataForEarthPicture) {
        when (liveData) {
            is LiveDataForEarthPicture.Success -> {
                val apiKey = BuildConfig.NASA_API_KEY
                var uri =
                    "https://api.nasa.gov/EPIC/archive/natural/2019/05/30/png/epic_1b_20190530011359.png?api_key=${apiKey}"
                if (liveData.picturesEarthData.isNotEmpty()) {
                    uri = "https://api.nasa.gov/EPIC/archive/natural/" +
                            "${year}/" +
                            "${mounth}/" +
                            "${day}" +
                            "/png/${liveData.picturesEarthData[0].image}.png?api_key=${apiKey}"
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


    private fun initProperty() {
        activity = getActivity() as MainActivity

        if (activity != null) {
            tab = activity!!.tab_layout
            fab = activity!!.fab_earth_main
            params = image_view_earth_fragment.layoutParams
            coordinatorLayout = CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.WRAP_CONTENT,
                CoordinatorLayout.LayoutParams.WRAP_CONTENT
            )
            bottomBar = activity!!.bottom_app_bar
        }
    }

    private fun initAnimation() {

        image_view_earth_fragment.setOnClickListener {
            isExpanded = !isExpanded
            val imageTransition = ChangeImageTransform().setDuration(300)
            TransitionManager.beginDelayedTransition(
                container_earth_picture_fragment,
                TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(imageTransition)
            )

            if (isExpanded) {
                hideComponents()
            } else {
                showComponent()
            }
        }
    }

    private fun hideComponents() {
        params?.height = activity?.container_earth_main_fragment?.height
        image_view_earth_fragment.scaleType = ImageView.ScaleType.CENTER_CROP
        val tabY = 150f
        tab?.animate()?.setDuration(280)?.y(-tabY)
            ?.setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    image_view_earth_fragment.layoutParams = params
                    fab?.hide()
                    bottomBar?.scrollBarFadeDuration = 1000
                    bottomBar?.performHide()
                    tab?.visibility = TabLayout.GONE
                }
            })
    }

    private fun showComponent() {
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        image_view_earth_fragment.scaleType = ImageView.ScaleType.CENTER_CROP
        tab?.animate()?.setDuration(300)?.y(0f)
            ?.setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    coordinatorLayout?.gravity = Gravity.CENTER
                    image_view_earth_fragment.layoutParams = coordinatorLayout
                    bottomBar?.performShow()
                    fab?.show()
                    tab?.visibility = TabLayout.VISIBLE

                }
            })
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