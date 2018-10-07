package com.unknown.numee.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.child.tasks.TasksFragment
import com.unknown.numee.main.password.PasswordFragment
import com.unknown.numee.parent.schedules.SchedulesFragment
import com.unknown.numee.switcher.SwitcherActivity
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.event.Event
import com.unknown.numee.util.event.EventCallback
import com.unknown.numee.util.event.EventManager
import com.unknown.numee.util.event.events.WrongPasswordEvent

class MainActivity : BaseActivity() {

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var switchView: View

    private val tasksFragment = TasksFragment.newInstance()
    private val passwordFragment = PasswordFragment.newInstance()
    private val schedulesFragment = SchedulesFragment.newInstance()

    private var isPasswordOpened = false
    private val wrongPasswordCallback = object : EventCallback {
        override fun onReceived(event: Event) {
            if (event is WrongPasswordEvent) {
                closePasswordScreen()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initReferences()

        if (isChildUserType()) {
            useChildView()
        } else {
            useParentView()
        }

        EventManager.register(WrongPasswordEvent::class.java, wrongPasswordCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventManager.unregister(WrongPasswordEvent::class.java, wrongPasswordCallback)
    }

    override fun onBackPressed() {
        if (isPasswordOpened) {
            isPasswordOpened = false
            closePasswordScreen()
        } else {
            super.onBackPressed()
        }
    }

    private fun isChildUserType(): Boolean {
        return Preferences.userType == "CHILD"
    }

    private fun initReferences() {
        switchView = findViewById(R.id.activity_main__view_switch)

        switchView.setOnLongClickListener {
            if (isPasswordOpened) {
                isPasswordOpened = false
                closePasswordScreen()
            } else {
                if (isChildUserType()) {
                    isPasswordOpened = true
                    openPasswordScreen()
                } else {
                    startSwitcherActivity()
                }
            }
            true
        }
    }

    private fun openPasswordScreen() {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_up_in, R.anim.slide_down_out)
                .add(R.id.activity_main__content, passwordFragment)
                .commit()
    }

    private fun closePasswordScreen() {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_up_in, R.anim.slide_down_out)
                .remove(passwordFragment)
                .commit()
    }

    private fun useParentView() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main__content, schedulesFragment)
                .commit()
    }

    private fun useChildView() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main__content, tasksFragment)
                .commit()
    }

    private fun startSwitcherActivity() {
        SwitcherActivity.startActivity(applicationContext)
    }
}
