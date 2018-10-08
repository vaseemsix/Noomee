package com.unknown.numee.parent.tasks

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.unknown.numee.R


class WeekDaysAdapter(
        val context: Context,
        val onClickListener: OnClickListener?
) : RecyclerView.Adapter<WeekDaysAdapter.ViewHolder>() {

    private var itemList: MutableList<Boolean> = mutableListOf()
    private var itemNames: Array<String> = arrayOf()

    fun setItemList(itemList: MutableList<Boolean>, itemNamesList: Array<String>) {
        this.itemList = itemList
        this.itemNames = itemNamesList
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
        holder.textView.text = this.itemNames[position]

        if (this.itemList[position]) {
            holder.textView.background = ContextCompat.getDrawable(context, R.drawable.styles__button_type_3_on)
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.colorRewardBg))
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.week_day_button)

        init {
            view.setOnClickListener { onClickListener?.onDaysItemClicked(textView, adapterPosition) }
        }
    }

    interface OnClickListener {
        fun onDaysItemClicked(textView: TextView, position: Int)
    }
}