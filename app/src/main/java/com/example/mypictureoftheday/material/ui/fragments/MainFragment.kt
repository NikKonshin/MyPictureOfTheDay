package com.example.mypictureoftheday.material.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import com.example.mypictureoftheday.R
import com.example.mypictureoftheday.material.ui.activity.MainActivity
import com.example.mypictureoftheday.viewmodels.MainViewModel
import com.example.mypictureoftheday.viewmodels.livedata.Data
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
    private var isExpanded = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData()
            .observe(this@MainFragment, Observer<Data> { renderData(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))

        image_view_main_fragment.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                container_main_fragment,
                TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams = image_view_main_fragment.layoutParams
            params.width =
                if (isExpanded) container_main_fragment.layoutParams.height
                else ViewGroup.LayoutParams.WRAP_CONTENT

            image_view_main_fragment.layoutParams = params
            image_view_main_fragment.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }

        input_layout_main_fragment.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(URI_WIKI + input_edit_text_main_fragment.text.toString())
            })
        }
        setBottomAppBar(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> toast("Favourite")
            R.id.app_bar_settings -> activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, SettingsFragment())
                ?.addToBackStack(null)
                ?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                bottom_app_bar.navigationIcon = null
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                bottom_app_bar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    companion object {
        private const val URI_WIKI = "https://ru.wikipedia.org/wiki/"
        fun newInstance() = MainFragment()
        private var isMain = true
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    private fun renderData(data: Data) {
        when (data) {
            is Data.Success -> {
                val serverResponseData = data.serverResponseData
                val uri = serverResponseData.url
                val description = serverResponseData.explanation
                val title = serverResponseData.title
                val contentType = serverResponseData.mediaType
                if (uri.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {
                    if (contentType == "image") {
                        image_view_main_fragment.visibility = ImageView.VISIBLE
                        video_view_main_fragment.visibility = VideoView.GONE
                        image_view_main_fragment.load(uri) {
                            lifecycle(this@MainFragment)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo_vector)
                        }
                    } else if (contentType == "video") {
                        video_view_main_fragment.visibility = VideoView.VISIBLE
                        image_view_main_fragment.visibility = ImageView.GONE
                        video_view_main_fragment.clearCache(true)
                        video_view_main_fragment.isClickable = true
                        image_view_main_fragment.isClickable = false
                        video_view_main_fragment.settings.javaScriptEnabled = true
                        video_view_main_fragment.settings.javaScriptCanOpenWindowsAutomatically =
                            true
                        video_view_main_fragment.loadUrl(uri)
                    }

                    if (description == null) {
                        toast("Description is empty")
                    } else {
                        val spannableDescription = SpannableString(description)
                        spannableDescription.setSpan(
                            context?.getColor(R.color.color_gray)?.let { BackgroundColorSpan(it) },
                            0,description.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )

                        spannableDescription.setSpan(
                            ForegroundColorSpan(Color.WHITE),
                            0,description.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        val spannableTitle = SpannableString(title)
                        spannableTitle.setSpan(
                            ForegroundColorSpan(Color.RED),
                            0,title.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        bottom_sheet_description.text = spannableDescription
                        bottom_sheet_description_header.text = spannableTitle
                    }
                }
            }
            is Data.Loading -> {

            }
            is Data.Error -> {
                toast(data.error.message)
            }
        }
    }
}