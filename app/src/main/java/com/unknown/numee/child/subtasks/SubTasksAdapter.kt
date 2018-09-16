package com.unknown.numee.child.subtasks

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.unknown.numee.BR
import com.unknown.numee.R
import com.unknown.numee.util.GlideApp


class SubTasksAdapter(
        val onClickListener: OnClickListener?
) : RecyclerView.Adapter<SubTasksAdapter.ViewHolder>() {

    private var itemList: List<ViewContract.Item> = listOf()

    fun setItemList(itemList: List<ViewContract.Item>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_item_subtask, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        DataBindingUtil.bind<ViewDataBinding?>(holder.itemView)?.let {
            holder.bind(it, itemList[position])
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var imageView: ImageView = view.findViewById(R.id.view_item_subtask__img)

        init {
            view.setOnClickListener { onClickListener?.onItemClicked(itemList[adapterPosition]) }
        }

        fun bind(viewDataBinding: ViewDataBinding, item: ViewContract.Item) {
            viewDataBinding.setVariable(BR.item, item)
            GlideApp
                    .with(imageView.context)
                    .load(item.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)
            viewDataBinding.executePendingBindings()
        }
    }

    interface OnClickListener {
        fun onItemClicked(item: ViewContract.Item)
    }
}