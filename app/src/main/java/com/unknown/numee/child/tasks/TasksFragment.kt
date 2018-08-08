package com.unknown.numee.child.tasks

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

    private lateinit var tasksListView: RecyclerView
    private lateinit var tasksListAdapter: TasksAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasksListAdapter = TasksAdapter()
        tasksListView = view.findViewById(R.id.fragment_tasks__list_tasks)
        tasksListView.setHasFixedSize(true)
        tasksListView.layoutManager = LinearLayoutManager(context)
        tasksListView.adapter = tasksListAdapter

        initPresenter()
        presenter.onViewCreated()
    }

    override fun setItemList(itemList: List<ViewContract.Item>) {
        tasksListAdapter.setItemList(itemList)
    }

    private fun initPresenter() {
        val model = TasksModel(context!!)
        val tasksPresenter = TasksPresenter(model)
        tasksPresenter.setView(this)
        model.presenter = tasksPresenter
        presenter = tasksPresenter
    }
}