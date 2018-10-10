package com.unknown.numee.splashscreen

import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
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

class SplashScreenActivity : BaseActivity(), ViewContract.View {

    private lateinit var presenter: ViewContract.Listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Preferences.initialize(this)
        EventManager.initialize(this)

        initPresenter()
        presenter.onCreate()

        val iconView = findViewById<ImageView>(R.id.splash_screen__icon)
        GlideApp
                .with(this)
                .asGif()
                .load(R.raw.logo_animation)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .into(iconView)
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    private fun initPresenter() {
        val model = SplashScreenModel(this)
        val splashScreenPresenter = SplashScreenPresenter(model)
        splashScreenPresenter.setView(this)
        model.presenter = splashScreenPresenter

        presenter = splashScreenPresenter
    }

    override fun startMainActivity() {
        val handler = Handler()
        handler.postDelayed({
            MainActivity.startActivity(this@SplashScreenActivity)
        }, 5000)
    }

    override fun startLoginActivity() {
        val handler = Handler()
        handler.postDelayed({
            LoginActivity.startActivity(this@SplashScreenActivity)
        }, 5000)
    }

    override fun startUserSwitcherActivity() {
        val handler = Handler()
        handler.postDelayed({
            SwitcherActivity.startActivity(this@SplashScreenActivity)
        }, 6000)
    }

    override fun startRegistrationActivity() {
        val handler = Handler()
        handler.postDelayed({
            RegistrationActivity.startActivity(this@SplashScreenActivity)
        }, 5000)
    }

    override fun startLanguageSelectorActivity() {
        val handler = Handler()
        handler.postDelayed({
            LanguageSelectionActivity.startActivity(this@SplashScreenActivity)
        }, 5000)
    }

    override fun setLanguage(language: String) {
        val myLocale = Locale(language)
        val res = resources
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, res.displayMetrics)
    }
}
