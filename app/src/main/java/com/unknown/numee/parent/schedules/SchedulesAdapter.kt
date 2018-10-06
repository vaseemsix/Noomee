package com.unknown.numee.parent.schedules

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unknown.numee.BR
import com.unknown.numee.R
import com.unknown.numee.business.beans.Schedule


class SchedulesAdapter(
        val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SchedulesAdapter.ViewHolder>() {

    private var itemList: List<Schedule> = listOf()

    fun setItemList(itemList: List<Schedule>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_schedule_item, parent, false))
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
        fun bind(viewDataBinding: ViewDataBinding, item: Schedule, onItemClickListener: OnItemClickListener) {
            viewDataBinding.setVariable(BR.item, item)
            viewDataBinding.setVariable(BR.onClickListener, onItemClickListener)
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(item: Schedule)
    }
}