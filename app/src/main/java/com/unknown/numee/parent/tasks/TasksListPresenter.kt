package com.unknown.numee.parent.tasks

import com.unknown.numee.util.mvp.Presenter


class TasksListPresenter(
        val model: ModelContract.Model
) : Presenter<ViewContract.View>(), ViewContract.Listener, ModelContract.Listener {

    override fun onCreate() {
        view.initViews()
    }

    override fun onDayItemClicked(position: Int) {

    }

}