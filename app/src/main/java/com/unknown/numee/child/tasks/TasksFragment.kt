package com.unknown.numee.child.tasks

import android.os.Bundle
import android.support.constraint.Group
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.unknown.numee.R
import com.unknown.numee.child.story.ChildStoryActivity
import com.unknown.numee.child.subtasks.SubTasksActivity
import com.unknown.numee.util.widget.ButtonView


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

    private lateinit var welcomeTxtView: TextView
    private lateinit var progressView: ProgressBar
    private lateinit var finishView: Group
    private lateinit var goodNightView: ButtonView
    private lateinit var collectedNumsView: TextView
    private lateinit var tasksListView: RecyclerView
    private lateinit var tasksListAdapter: TasksAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcomeTxtView = view.findViewById(R.id.fragment_tasks__txt_welcome)
        progressView = view.findViewById(R.id.fragment_tasks__progress)
        finishView = view.findViewById(R.id.fragment_tasks__cont_finish)
        goodNightView = view.findViewById(R.id.fragment_tasks__btn_good_night)
        collectedNumsView = view.findViewById(R.id.fragment_tasks__txt_collected_nums)

        goodNightView.setOnClickListener { presenter.onGoodNightClicked() }

        tasksListAdapter = TasksAdapter(object : TasksAdapter.OnItemClickListener {
            override fun onItemClicked(item: ViewContract.Item) {
                presenter.onItemClicked(item)
            }
        })
        tasksListView = view.findViewById(R.id.fragment_tasks__list_tasks)
        tasksListView.setHasFixedSize(true)
        tasksListView.layoutManager = LinearLayoutManager(context)
        tasksListView.adapter = tasksListAdapter

        initPresenter()
        presenter.onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroy()
    }

    override fun setWelcomeText(text: String) {
        welcomeTxtView.text = text
    }

    override fun setTasksProgress(progress: Int) {
        progressView.progress = progress
    }

    override fun setCollectedNums(numCount: Int) {
        collectedNumsView.text = String.format(getString(R.string.you_have_x_num), numCount)
    }

    override fun setItemList(itemList: List<ViewContract.Item>) {
        tasksListAdapter.setItemList(itemList)
    }

    override fun setContentVisibility(isVisible: Boolean) {
        tasksListView.visibility = if (isVisible) View.VISIBLE else View.GONE
        progressView.visibility = if (isVisible) View.VISIBLE else View.GONE
        welcomeTxtView.visibility = if (isVisible) View.VISIBLE else View.GONE
        finishView.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun startSubTasksActivity(taskID: String, scheduleID: String) {
	    context?.let {
		    SubTasksActivity.startActivity(it, taskID, scheduleID)
	    }
    }

	override fun startStoryActivity(taskID: String, scheduleID: String) {
		context?.let {
			ChildStoryActivity.startActivity(it, taskID, scheduleID)
		}
	}

	override fun finish() {
        activity?.finish()
    }

    private fun initPresenter() {
        val model = TasksModel(context!!)
        val tasksPresenter = TasksPresenter(model, TaskItemListCreator(context!!))
        tasksPresenter.setView(this)
        model.presenter = tasksPresenter
        presenter = tasksPresenter
    }
}