package com.unknown.numee.parent.template

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.unknown.numee.BR
import com.unknown.numee.R


class TemplateAdapter(
        val onClickListener: OnClickListener?
) : RecyclerView.Adapter<TemplateAdapter.ViewHolder>() {

    private var itemList: List<String> = listOf()

    fun setItemList(itemList: List<String>) {
        this.itemList = itemList
        Log.d("Vlad", this.itemList.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_template_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        DataBindingUtil.bind<ViewDataBinding?>(holder.itemView)?.let {
            holder.bind(it, itemList[position])
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var textView: TextView = view.findViewById(R.id.templateNameTextView)

        init {
            view.setOnClickListener { onClickListener?.onItemClicked(itemList[adapterPosition]) }
        }

        fun bind(viewDataBinding: ViewDataBinding, item: String) {
            viewDataBinding.setVariable(BR.item, item)

            Log.d("Vlad", "Setting Text  View " + BR.item)
            textView.setText(BR.item)
            viewDataBinding.executePendingBindings()
        }
    }

    interface OnClickListener {
        fun onItemClicked(item: String)
    }
}