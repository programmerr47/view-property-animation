package com.github.programmerr47.viewpropanimatorapp

import android.animation.Animator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewPropertyAnimator
import kotlinx.android.synthetic.main.activity_presenation.*

const val ANIM_DURATION = 300L
const val SHOW_DURATION = 2000L

class PresentationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presenation)
        b_hide.setOnClickListener { iv_image.fadeOut() }
        b_show_hide.setOnClickListener { iv_image.fadeInOut(SHOW_DURATION) }
        b_show.setOnClickListener { iv_image.fadeIn() }
    }
}

fun View.fadeInOut(showDuration: Long) = fadeIn { fadeOut(showDuration) }

fun View.fadeOut(delay: Long = 0) {
    if (this.visibility != VISIBLE || this.alpha == 0f) {
        this.visibility = GONE
        return
    }

    this.animate()
            .alpha(0f)
            .setStartDelay(delay)
            .setDuration(ANIM_DURATION)
            .setEndListener { this.visibility = GONE }
            .start()
}

fun View.fadeIn(endAction: () -> Unit = {}) {
    if (this.visibility == VISIBLE && this.alpha == 1f) {
        endAction.invoke()
        return
    }

    if (this.visibility != VISIBLE) {
        this.visibility = VISIBLE
        this.alpha = 0f
    }

    this.animate()
            .alpha(1f)
            .setDuration(ANIM_DURATION)
            .setEndListener(endAction)
            .start()
}

fun ViewPropertyAnimator.setEndListener(listener: () -> Unit) =
        this.setListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                listener.invoke()
            }
        })
