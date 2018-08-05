package com.unknown.numee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.child.tasks.TasksFragment

class MainActivity : BaseActivity() {

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val tasksFragment = TasksFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main__content, tasksFragment)
                .commitNow()
    }
}
