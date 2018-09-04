package com.unknown.numee.child.subtasks


interface ViewContract {
    interface View {
        fun getID(): String
        fun setTitle(title: String)
        fun setSubTitle(subTitle: String)
        fun setSubTasksProgress(progress: Int)
        fun setItemList(itemList: List<Item>)
    }

    interface Listener {
        fun onCreate()
        fun onItemClicked()
    }

    interface Item {
        val name: String
        val imageUrl: String
        val status: Int
    }
}