package com.unknown.numee.util.extensions

import android.databinding.BindingAdapter
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.storage.FirebaseStorage
import com.unknown.numee.util.GlideApp
import com.unknown.numee.util.Preferences


@BindingAdapter("android:layout_height")
fun View.setLayoutHeight(height: Float) {
    val layoutParams = this.layoutParams
    layoutParams.height = height.toInt()
    this.layoutParams = layoutParams
}

@BindingAdapter("android:layout_width")
fun View.setLayoutWidth(width: Float) {
    val layoutParams = this.layoutParams
    layoutParams.width = width.toInt()
    this.layoutParams = layoutParams
}

@BindingAdapter("app:contentPadding")
fun CardView.setContentPadding(padding: Float) {
    this.setContentPadding(padding.toInt(), padding.toInt(), padding.toInt(), padding.toInt())
}

@BindingAdapter("android:layout_marginStart")
fun View.setMarginStart(margin: Float) {
    val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.marginStart = margin.toInt()
    this.layoutParams = layoutParams
}

@BindingAdapter("android:layout_marginEnd")
fun View.setMarginEnd(margin: Float) {
    val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.marginEnd = margin.toInt()
    this.layoutParams = layoutParams
}

fun ImageView.loadImage(url: String) {
	GlideApp
			.with(this.context)
			.load(url)
			.diskCacheStrategy(DiskCacheStrategy.ALL)
			.into(this)
}

// this probably needs to be changed
fun ImageView.loadImageWithReference(url: String) {
	val storageReference = if (url.isNotEmpty()) {
		val storage = FirebaseStorage.getInstance()
		val urlWithGender = url.replace("gender", Preferences.gender)
		storage.reference.child(urlWithGender)
	} else {
		null
	}

	val view = this
	storageReference?.let {
		GlideApp
				.with(view.context)
				.load(it)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.into(view)
	}
}