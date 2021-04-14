package com.example.mypictureoftheday.material.ui.fragments

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.os.Handler
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.mypictureoftheday.R
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : Fragment() {
    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image_view_splash_fragment.animate().rotationBy(550f)
            .setInterpolator(AccelerateDecelerateInterpolator()).setDuration(3000)
            .setListener (object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    fragmentManager?.beginTransaction()?.replace(R.id.container, MainFragment.newInstance())
                        ?.commitNow()
                }

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationRepeat(animation: Animator?) {}

            })

        handler.postDelayed({
            fragmentManager?.beginTransaction()?.replace(R.id.container, MainFragment.newInstance())
                ?.commitNow()
        }, 3000)
    }
}