package com.unknown.numee.child.subtasks


interface ViewContract {
    interface View {
        fun getTaskID(): String
        fun getScheduleID(): String
        fun setTitle(title: String)
        fun setSubTitle(subTitle: String)
        fun setSubTasksProgress(progress: Int)
        fun setSubTasksTime(time: String)
        fun setItemList(itemList: List<Item>)
        fun showRewardActivity(numCount: Int)
    }

    interface Listener {
        fun onCreate()
        fun onItemClicked(item: Item)
    }

    interface Item {
        val id: String
        val name: String
        val imageUrl: String
        val status: Int
        val index: String
    }
}