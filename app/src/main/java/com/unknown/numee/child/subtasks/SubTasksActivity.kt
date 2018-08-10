package com.unknown.numee.child.subtasks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity


class SubTasksActivity : BaseActivity() {

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, SubTasksActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subtasks)
    }
}