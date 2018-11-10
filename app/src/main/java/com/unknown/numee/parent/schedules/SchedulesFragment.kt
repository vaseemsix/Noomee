package com.unknown.numee.parent.schedules

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.unknown.numee.R
import com.unknown.numee.business.beans.Schedule
import com.unknown.numee.parent.tasks.TasksListActivity
import com.unknown.numee.parent.template.TemplateActivity


class SchedulesFragment : Fragment(), ViewContract.View {

    companion object {

        fun newInstance(): SchedulesFragment {
            return SchedulesFragment()
        }
    }

    private lateinit var presenter: ViewContract.Listener
    private lateinit var scheduleListAdapter: SchedulesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
    }

    internal lateinit var view: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.fragment_schedules, container, false)
        presenter.onCreate()
        return view
    }

    private fun initPresenter() {
        val schedulesModel = SchedulesModel(context!!)
        val schedulesPresenter = SchedulesPresenter(schedulesModel)
        schedulesPresenter.setView(this)
        schedulesModel.presenter = schedulesPresenter
        presenter = schedulesPresenter
    }

    private lateinit var schedulesListView: RecyclerView
    private lateinit var newTemplateButton: Button

    override fun initViews() {
        schedulesListView = view.findViewById(R.id.templatesListView)
        newTemplateButton = view.findViewById(R.id.newTemplateImageView)

        newTemplateButton.setOnClickListener { openSchedulesListActivity() }
    }

    override fun setupSchedulesListView() {
        schedulesListView.setHasFixedSize(true)
        schedulesListView.layoutManager = GridLayoutManager(context, 2)
        scheduleListAdapter = SchedulesAdapter(object : SchedulesAdapter.OnItemClickListener {
            override fun onItemRemoveClicked(item: Schedule) {
                presenter.onScheduleItemRemoved(item)
            }

            override fun onItemEditClicked(item: Schedule) {
                presenter.onScheduleItemClicked(item)
            }
        })

        schedulesListView.adapter = scheduleListAdapter
    }

    override fun showMessage(message: String) {
	    context?.let {
		    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
	    }
    }

    override fun updateSchedules(schedule: List<Schedule>) {
        scheduleListAdapter.setItemList(schedule)
    }

    private fun openSchedulesListActivity() {
	    context?.let {
		    TemplateActivity.startActivity(it)
	    }
    }

    override fun openTasksActivity(scheduleId: String, scheduleName: String) {
	    context?.let {
		    TasksListActivity.startActivity(it, scheduleId, scheduleName)
	    }
    }
}