package com.unknown.numee.switcher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.login.LoginActivity
import com.unknown.numee.main.MainActivity
import com.unknown.numee.util.Preferences

class SwitcherActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, SwitcherActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switcher)
    }

    fun useAsParent(view: View) {
        Preferences.userType = "PARENT"
        startMainActivity()
    }

    fun useAsChild(view: View) {
        Preferences.userType = "CHILD"
        startMainActivity()
    }

    private fun startMainActivity() {
        finish()
        MainActivity.startActivity(this@SwitcherActivity)
    }

    public fun logOut(view: View) {
        FirebaseAuth.getInstance().signOut()
        finish()
        LoginActivity.startActivity(this)
    }
}