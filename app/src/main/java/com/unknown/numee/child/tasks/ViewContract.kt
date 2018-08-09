package com.unknown.numee.child.tasks


interface ViewContract {
    interface View {
        fun setWelcomeText(text: String)
        fun setItemList(itemList: List<Item>)
    }

    interface Listener {
        fun onViewCreated()
    }

    interface Item {
        val time: String
        val name: String
    }
}