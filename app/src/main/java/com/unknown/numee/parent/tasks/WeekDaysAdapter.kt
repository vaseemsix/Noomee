package com.unknown.numee.parent.tasks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.unknown.numee.R


class WeekDaysAdapter(
        val onClickListener: OnClickListener?
) : RecyclerView.Adapter<WeekDaysAdapter.ViewHolder>() {

    private var itemList: List<Boolean> = listOf()
    private var itemNames: Array<String> = arrayOf()

    fun setItemList(itemList: List<Boolean>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        itemNames = parent.context.resources.getStringArray(R.array.week_days)
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_day_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.textView.text = this.itemList[position].split("_'_")[1]
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.week_day_button)

        init {
            view.setOnClickListener { onClickListener?.onDaysItemClicked(adapterPosition) }
        }
    }

    interface OnClickListener {
        fun onDaysItemClicked(item: Int)
    }
}