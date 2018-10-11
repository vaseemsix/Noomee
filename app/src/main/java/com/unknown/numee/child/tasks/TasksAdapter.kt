package com.unknown.numee.child.tasks

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unknown.numee.BR
import com.unknown.numee.R


class TasksAdapter(
        private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var itemList: List<ViewContract.Item> = listOf()

    fun setItemList(itemList: List<ViewContract.Item>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_item_task, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        DataBindingUtil.bind<ViewDataBinding?>(holder.itemView)?.let {
            holder.bind(it, itemList[position], onItemClickListener)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(viewDataBinding: ViewDataBinding, item: ViewContract.Item, onItemClickListener: OnItemClickListener) {
            viewDataBinding.setVariable(BR.item, item)
            viewDataBinding.setVariable(BR.onClickListener, onItemClickListener)
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(item: ViewContract.Item)
    }
}