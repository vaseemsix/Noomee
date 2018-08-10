package com.unknown.numee.util.extensions

import android.databinding.BindingAdapter
import android.support.v7.widget.CardView


@BindingAdapter("android:layout_height")
fun CardView.setLayoutHeight(height: Float) {
    val layoutParams = this.layoutParams
    layoutParams.height = height.toInt()
    this.layoutParams = layoutParams
}

@BindingAdapter("android:layout_width")
fun CardView.setLayoutWidth(width: Float) {
    val layoutParams = this.layoutParams
    layoutParams.width = width.toInt()
    this.layoutParams = layoutParams
}