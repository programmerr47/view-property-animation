package com.github.programmerr47.viewpropanimatorapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_presenation.*

const val ANIM_DURATION = 2000L

class PresentationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presenation)
        b_hide.setOnClickListener { _ -> iv_image.fadeOut() }
        b_show.setOnClickListener { _ -> iv_image.fadeIn() }
    }
}

fun View.fadeOut() = this.animate().alpha(0f).start()
fun View.fadeIn() = this.animate().alpha(1f).setDuration(ANIM_DURATION).start()
