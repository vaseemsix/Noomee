package com.unknown.numee.splashscreen

import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.language.LanguageSelectionActivity
import com.unknown.numee.login.LoginActivity
import com.unknown.numee.main.MainActivity
import com.unknown.numee.registration.RegistrationActivity
import com.unknown.numee.switcher.SwitcherActivity
import com.unknown.numee.util.GlideApp
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.event.EventManager
import java.util.*

class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Preferences.initialize(this)
        changeLanguage()

        EventManager.initialize(this)

        if (isUserLoggedIn()) {
            if (FirebaseAuth.getInstance().currentUser!!.isEmailVerified) {
                if (isChildInfoExist()) {
                    if (isUserTypeSet()) {
                        startMainActivity()
                    } else {
                        startUserSwitcherActivity()
                    }
                } else {
                    startRegistrationActivity()
                }
            } else {
                startLoginActivity()
            }
        } else {
            if (!isLanguageSelected()) {
                startLanguageSelectorActivity()
            } else {
                startLoginActivity()
            }
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

    private fun startLanguageSelectorActivity() {
        val handler = Handler()
        handler.postDelayed({
            LanguageSelectionActivity.startActivity(this@SplashScreenActivity)
        }, 5000)
    }

    private fun isUserLoggedIn(): Boolean {
        return Preferences.userID.isNotEmpty()
    }

    private fun isUserTypeSet(): Boolean {
        return Preferences.userType.isNotEmpty()
    }

    private fun isLanguageSelected(): Boolean {
        return Preferences.language.isNotEmpty()
    }

    private fun isChildInfoExist(): Boolean {
        return Preferences.childInfo
    }

    private fun changeLanguage() {
        if (languageIsSet()) {
            updateResource()
        }
    }

    fun languageIsSet(): Boolean {
        return Preferences.language.isNotEmpty()
    }

    fun updateResource() {
        val myLocale = Locale(Preferences.language)
        val res = resources
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, res.displayMetrics)
    }
}
