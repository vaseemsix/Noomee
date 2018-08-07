package com.unknown.numee.child.tasks

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unknown.numee.R


class TasksFragment : Fragment(), ViewContract.View {

    companion object {

        @JvmStatic
        fun newInstance(): Fragment {
            val templateFragment = TasksFragment()
            templateFragment.arguments = Bundle()
            return templateFragment
        }
    }

    private lateinit var presenter: ViewContract.Listener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPresenter()
        presenter.onViewCreated()
    }

    private fun initPresenter() {
        val model = TasksModel(context!!)
        val tasksPresenter = TasksPresenter(model)
        tasksPresenter.setView(this)
        model.presenter = tasksPresenter
        presenter = tasksPresenter
    }
}