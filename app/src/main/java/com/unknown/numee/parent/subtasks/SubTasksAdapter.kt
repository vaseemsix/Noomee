package com.unknown.numee.parent.subtasks

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SwitchCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.storage.FirebaseStorage
import com.unknown.numee.R
import com.unknown.numee.business.beans.SubTask
import com.unknown.numee.util.GlideApp
import com.unknown.numee.util.Preferences

class SubTasksAdapter(
        val context: Context,
        val onClickListener: OnClickListener?
) : RecyclerView.Adapter<SubTasksAdapter.ViewHolder>() {

    private var itemList: List<SubTask> = mutableListOf()
    private val storageRef = FirebaseStorage.getInstance().reference

    fun setItemList(itemList: List<SubTask>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_sub_task_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textName.text = this.itemList[position].name
        GlideApp
                .with(holder.taskImage.context)
                .load(storageRef.child(this.itemList[position].imageUrl.replace("gender", Preferences.gender)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.taskImage)

        holder.switcher.isChecked = this.itemList[position].enable == 1
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.findViewById(R.id.sub_task_name_text_view)
        val taskImage: ImageView = view.findViewById(R.id.task_image_view)

        val switcher: SwitchCompat = view.findViewById(R.id.sub_task_switcher)
//        private val imageView: ImageView = view.findViewById(R.id.edit_task_icon)
//
        init {
//            imageView.setOnClickListener { onClickListener?.onTaskItemClicked(adapterPosition) }
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