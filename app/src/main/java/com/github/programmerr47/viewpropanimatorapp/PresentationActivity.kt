package com.github.programmerr47.viewpropanimatorapp

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
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
    if (visibility != VISIBLE || alpha == 0f) {
        visibility = GONE
        return
    }

    newAnimate()
            .alpha(0f)
            .setStartDelay(delay)
            .setDuration(ANIM_DURATION)
            .setEndListener { visibility = GONE }
            .start()
}

fun View.fadeIn(endAction: () -> Unit = {}) {
    if (visibility == VISIBLE && alpha == 1f) {
        endAction.invoke()
        return
    }

    if (visibility != VISIBLE) {
        visibility = VISIBLE
        alpha = 0f
    }

    newAnimate()
            .alpha(1f)
            .setDuration(ANIM_DURATION)
            .setEndListener(endAction)
            .start()
}

@SuppressLint("NewApi")
private fun View.newAnimate(): ViewPropertyAnimator {
    animate()
            .setStartDelay(0)
            .setDuration(300)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setListener(null)
            .applyWhen({ isJellyBean() }) {
                withEndAction(null)
                withStartAction(null) }
            .applyWhen({ isKitKat() }) { setUpdateListener(null) }
            .cancel()
    return animate()
}

private inline fun <T> T.applyWhen(predicate: () -> Boolean, action: T.() -> Unit): T {
    if (predicate()) {
        action(this)
    }
    return this
}

fun isJellyBean() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
fun isKitKat() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

private fun ViewPropertyAnimator.setEndListener(listener: () -> Unit) =
        this.setListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                listener.invoke()
            }
        })
