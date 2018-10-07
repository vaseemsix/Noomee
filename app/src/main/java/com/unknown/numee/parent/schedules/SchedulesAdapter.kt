package com.unknown.numee.parent.schedules

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.unknown.numee.R
import com.unknown.numee.business.beans.Schedule


class SchedulesAdapter(
        val onItemClickListener: OnItemClickListener?
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
        holder.textView.text = this.itemList[position].name
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.template_schedule_item_text_view)

        val removeImageView: ImageView = view.findViewById(R.id.remove_icon)
        val editImageView: ImageView = view.findViewById(R.id.edit_icon)

        init {
            removeImageView.setOnClickListener{onItemClickListener?.onItemRemoveClicked(itemList[adapterPosition])}
            editImageView.setOnClickListener{onItemClickListener?.onItemEditClicked(itemList[adapterPosition])}
        }
    }

    interface OnItemClickListener {
        fun onItemEditClicked(item: Schedule)
        fun onItemRemoveClicked(item: Schedule)
    }
}