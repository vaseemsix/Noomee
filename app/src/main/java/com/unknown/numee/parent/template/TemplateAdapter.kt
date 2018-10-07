package com.unknown.numee.parent.template

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.unknown.numee.R


class TemplateAdapter(
        val onClickListener: OnClickListener?
) : RecyclerView.Adapter<TemplateAdapter.ViewHolder>() {

    private var itemList: List<String> = listOf()

    fun setItemList(itemList: List<String>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_template_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = this.itemList[position].split("_'_")[1]
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.templateNameTextView)

        init {
            view.setOnClickListener { onClickListener?.onItemClicked(itemList[adapterPosition]) }
        }
    }

    interface OnClickListener {
        fun onItemClicked(item: String)
    }
}