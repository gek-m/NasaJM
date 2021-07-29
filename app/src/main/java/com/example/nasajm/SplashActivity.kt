package com.example.nasajm

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        image_view.animate()
            .rotationBy(550f)
            .rotationXBy(500f)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(1000)
            .setListener(object : Animator.AnimatorListener {

                override fun onAnimationEnd(animation: Animator?) {
                    //startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    //finish()
                }

                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })

        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 1800)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}