package com.unknown.numee.child.tasks


interface ViewContract {
    interface View {
        fun setWelcomeText(text: String)
        fun setTasksProgress(progress: Int)
        fun setCollectedNums(numCount: Int)
        fun setItemList(itemList: List<Item>)
        fun setContentVisibility(isVisible: Boolean)
        fun showMessage(message: String)
        fun startSubTasksActivity(taskID: String, scheduleID: String)
        fun finish()
    }

    interface Listener {
        fun onViewCreated()
        fun onViewDestroy()
        fun onItemClicked(item: Item)
        fun onGoodNightClicked()
    }

    interface Item {
        val time: String
        val name: String
        val numCount: String
        val statusOrdinal: Int
    }
}