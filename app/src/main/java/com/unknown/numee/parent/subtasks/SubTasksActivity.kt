package com.unknown.numee.parent.subtasks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity

class SubTasksActivity : BaseActivity() {


    companion object {

        const val ID = "ID"
        const val NAME = "NAME"

        fun startActivity(context: Context, id: String, name: String) {
            val intent = Intent(context, SubTasksActivity::class.java)
            intent.putExtra(ID, id)
            intent.putExtra(NAME, name)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_tasks)
    }
}
