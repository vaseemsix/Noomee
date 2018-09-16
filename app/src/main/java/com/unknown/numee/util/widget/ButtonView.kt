package com.unknown.numee.util.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.unknown.numee.R


class ButtonView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val textView: TextView

    init {
        View.inflate(context, R.layout.view_button, this)
        textView = findViewById(R.id.view_button__text)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonView)
        try {
            textView.text = typedArray.getString(R.styleable.SpinnerView_title) ?: ""
        } finally {
            typedArray.recycle()
        }
    }
}