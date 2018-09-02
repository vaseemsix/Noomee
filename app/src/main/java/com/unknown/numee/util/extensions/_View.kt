package com.unknown.numee.util.extensions

import android.databinding.BindingAdapter
import android.view.View


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