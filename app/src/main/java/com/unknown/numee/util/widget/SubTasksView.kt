package com.unknown.numee.util.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.unknown.numee.R
import com.unknown.numee.child.subtasks.ViewContract
import com.unknown.numee.util.GlideApp
import com.unknown.numee.util.Preferences


class SubTasksView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val currentView: CardView
    private val currentImgView: ImageView
    private val toDoView: CardView
    private val toDoImgView: ImageView

    private var itemList: List<ViewContract.Item> = listOf()

    private var onClickListener: ((item: ViewContract.Item) -> Unit)? = null

    init {
        View.inflate(context, R.layout.view_subtasks, this)

        currentView = findViewById(R.id.view_subtasks__current)
        currentImgView = findViewById(R.id.view_subtasks__img_current)
        toDoView = findViewById(R.id.view_subtasks__to_do)
        toDoImgView = findViewById(R.id.view_subtasks__img_to_do)

        currentView.setOnClickListener { onClickListener?.invoke(itemList[0]) }
    }


    fun setItemList(itemList: List<ViewContract.Item>) {
        this.itemList = itemList
        update()
    }

    fun setCurrentViewVisibility(isVisible: Boolean) {
        currentView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun setToDoViewVisibility(isVisible: Boolean) {
        toDoView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun setOnClickListener(listener: (item: ViewContract.Item) -> Unit) {
        onClickListener = listener
    }

    private fun update() {
        if (itemList.isEmpty()) return

        if (itemList.size == 1) {
            setToDoViewVisibility(false)
            setCurrentViewVisibility(true)
            loadImage(currentImgView, itemList[0].imageUrl)
        }

        if (itemList.size == 2) {
            setToDoViewVisibility(true)
            setCurrentViewVisibility(true)
            loadImage(currentImgView, itemList[0].imageUrl)
            loadImage(toDoImgView, itemList[1].imageUrl)
        }

        if (itemList[0].doAnimation) {
            doBounceAnimation(currentView)
        }
    }

    private fun loadImage(view: ImageView, url: String) {
        val storageReference = getImageStorageReference(url)
        storageReference?.let {
        GlideApp
                .with(view.context)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view)
        }
    }

    // this probably needs to be changed
    private fun getImageStorageReference(url: String): StorageReference? {
        return if (url.isNotEmpty()) {
            val storage = FirebaseStorage.getInstance()
            val urlWithGender = url.replace("gender", Preferences.gender)
            storage.reference.child(urlWithGender)
        } else {
            null
        }
    }

    private fun doBounceAnimation(targetView: View) {
        val animator = ObjectAnimator.ofFloat(targetView, "translationZ", 0f, 30f, 0f)
        animator.interpolator = BounceInterpolator()
        animator.startDelay = 500
        animator.duration = 1500
        animator.repeatCount = Animation.INFINITE
        animator.start()
    }
}