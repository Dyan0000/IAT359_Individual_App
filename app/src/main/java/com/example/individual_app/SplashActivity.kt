package com.example.individual_app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.animation.AnimationUtils

class SplashActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val appLogo = findViewById<ImageView>(R.id.appLogo)
        val appName = findViewById<TextView>(R.id.appName)
        appName.alpha = 0f
        appLogo.alpha = 0f
        appName.animate().setDuration(1000).alpha(1f)
        appLogo.animate().setDuration(1000).alpha(1f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

//        appLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_in))
//        Handler().postDelayed({
//            appLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_out))
//            Handler().postDelayed({
//                appLogo.visibility = View.GONE
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            }, 500)
//        }, 1500)



    }
}

