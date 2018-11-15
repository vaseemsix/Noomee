package com.unknown.numee.util.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.unknown.numee.R


class NoomeeProgressView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val progressView: ProgressBar

    init {
        View.inflate(context, R.layout.view_noomee_progress, this)
        progressView = findViewById(R.id.view_noomee_progress__progress)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NoomeeProgressView)
        try {
            progressView.progress = typedArray.getInt(R.styleable.NoomeeProgressView_progress, 0)
        } finally {
            typedArray.recycle()
        }
    }

	fun setProgress(progress: Int) {
		progressView.progress = progress
	}
}