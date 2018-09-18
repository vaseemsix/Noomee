package com.unknown.numee.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.child.tasks.TasksFragment

class MainActivity : BaseActivity() {

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var switchImgView: ImageView

    private val tasksFragment = TasksFragment.newInstance()
    private val passwordFragment = PasswordFragment.newInstance()

    private var isPasswordOpened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initReferences()

        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main__content, tasksFragment)
                .commit()
    }

    private fun initReferences() {
        switchImgView = findViewById(R.id.activity_main__img_switch)

        switchImgView.setOnClickListener {
            if (isPasswordOpened) {
                isPasswordOpened = false
                switchImgView.rotation = 0f
                supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_up_in, R.anim.slide_down_out)
                        .remove(passwordFragment)
                        .commit()
            } else {
                isPasswordOpened = true
                switchImgView.rotation = 180f
                supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_up_in, R.anim.slide_down_out)
                        .add(R.id.activity_main__content, passwordFragment)
                        .commit()
            }
        }
    }
}
