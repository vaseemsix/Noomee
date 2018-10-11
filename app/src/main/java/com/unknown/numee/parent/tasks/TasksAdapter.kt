package com.unknown.numee.parent.tasks

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SwitchCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.unknown.numee.R
import com.unknown.numee.business.beans.Task
import android.widget.CompoundButton




class TasksAdapter(
        val context: Context,
        val onClickListener: OnClickListener?
) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var itemList: MutableList<Task> = mutableListOf()

    fun setItemList(itemList: MutableList<Task>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_schedule_task_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = this.itemList[position].name
        holder.taskTime.text = this.itemList[position].time

        holder.switcher.isChecked = this.itemList[position].enable == 1
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.schedule_task_name)
        val taskTime: TextView = view.findViewById(R.id.schedule_task_time)
        val switcher: SwitchCompat = view.findViewById(R.id.task_switcher)
        private val imageView: ImageView = view.findViewById(R.id.edit_task_icon)

        init {
            imageView.setOnClickListener { onClickListener?.onTaskItemClicked(adapterPosition) }
            switcher.setOnCheckedChangeListener { _, isChecked ->
                onClickListener?.onSwitchClicked(isChecked, adapterPosition)
            }
        }
    }

    interface OnClickListener {
        fun onTaskItemClicked(position: Int)
        fun onSwitchClicked(isChecked: Boolean, position: Int)
    }
}