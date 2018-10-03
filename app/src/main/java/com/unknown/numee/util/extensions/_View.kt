package com.unknown.numee.util.extensions

import android.databinding.BindingAdapter
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewGroup


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