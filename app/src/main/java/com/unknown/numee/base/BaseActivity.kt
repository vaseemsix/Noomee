package com.unknown.numee.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.widget.FrameLayout
import com.unknown.numee.R

open class BaseActivity : AppCompatActivity() {

    private lateinit var containerView: CoordinatorLayout
    private lateinit var contentView: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)

        containerView = findViewById(R.id.activity_base__container)
        contentView = findViewById(R.id.activity_base__content)
    }

    override fun setContentView(layoutResID: Int) {
        View.inflate(this, layoutResID, contentView)
    }
}
