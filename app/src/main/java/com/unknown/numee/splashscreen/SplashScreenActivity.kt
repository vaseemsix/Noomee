package com.unknown.numee.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.unknown.numee.MainActivity
import com.unknown.numee.R
import com.unknown.numee.login.LoginActivity
import com.unknown.numee.parent.template.ParentActivity
import com.unknown.numee.registration.RegistrationActivity
import com.unknown.numee.switcher.SwitcherActivity
import com.unknown.numee.util.GlideApp
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.event.EventManager

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Preferences.initialize(this)
        EventManager.initialize(this)

        if (isUserLoggedIn()) {
            Log.d("VLAD", Preferences.userType)
            if (isUserTypeSet()) {
                if (isChildUserType()) {
                    startMainActivity()
                } else {
                    startParentActivity()
                }
            } else {
                startUserSwitcherActivity()
            }
//            startRegistrationActivity()
        } else {
            startLoginActivity()
        }

        val iconView = findViewById<ImageView>(R.id.splash_screen__icon)
        GlideApp
                .with(this).asGif()
                .load(R.raw.logo_animation)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .into(iconView)
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    private fun startMainActivity() {
        val handler = Handler()
        handler.postDelayed({
            MainActivity.startActivity(this@SplashScreenActivity)
        }, 5000)
    }

    private fun startLoginActivity() {
        val handler = Handler()
        handler.postDelayed({
            LoginActivity.startActivity(this@SplashScreenActivity)
        }, 5000)
    }

    private fun startParentActivity() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity, ParentActivity::class.java)
            this@SplashScreenActivity.startActivity(intent)
        }, 6000)
    }

    private fun startUserSwitcherActivity() {
        val handler = Handler()
        handler.postDelayed({
            SwitcherActivity.startActivity(this@SplashScreenActivity)
        }, 6000)
    }

    private fun startRegistrationActivity() {
        val handler = Handler()
        handler.postDelayed({
            RegistrationActivity.startActivity(this@SplashScreenActivity)
        }, 5000)
    }

    private fun isUserLoggedIn(): Boolean {
        return Preferences.userID.isNotEmpty()
    }

    private fun isUserTypeSet(): Boolean {
        return Preferences.userType.isNotEmpty()
    }

    private fun isChildUserType(): Boolean {
        return Preferences.userType == "CHILD"
    }
}
