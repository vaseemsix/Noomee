package com.unknown.numee.child.tasks


interface ViewContract {
    interface View {
        fun setWelcomeText(text: String)
        fun setTasksProgress(progress: Int)
        fun setItemList(itemList: List<Item>)
        fun showError(message: String)
        fun startSubTasksActivity(ID: String)
    }

    interface Listener {
        fun onViewCreated()
        fun onViewDestroy()
        fun onItemClicked(item: Item)
    }

    interface Item {
        val time: String
        val name: String
        val numCount: String
        val statusOrdinal: Int
    }
}