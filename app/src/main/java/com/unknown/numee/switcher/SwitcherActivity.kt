package com.unknown.numee.switcher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.main.MainActivity
import com.unknown.numee.parent.template.ParentActivity
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
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SwitcherActivity, ParentActivity::class.java)
            this@SwitcherActivity.startActivity(intent)
        }, 1000)
    }

    fun useAsChild(view: View) {
        Preferences.userType = "CHILD"
        val handler = Handler()
        handler.postDelayed({
            MainActivity.startActivity(this@SwitcherActivity)
        }, 1000)
    }
}