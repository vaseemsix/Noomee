package com.unknown.numee.child.subtasks


interface ViewContract {
    interface View {
        fun getID(): String
        fun setTitle(title: String)
        fun setSubTitle(subTitle: String)
        fun setItemList(itemList: List<Item>)
    }

    interface Listener {
        fun onCreate()
    }

    interface Item {
        val name: String
        val imageUrl: String
        val status: Int
    }
}