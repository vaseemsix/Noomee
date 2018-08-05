package com.unknown.numee.child.tasks

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unknown.numee.R


class TasksFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance(): Fragment {
            val templateFragment = TasksFragment()
            templateFragment.arguments = Bundle()
            return templateFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }
}