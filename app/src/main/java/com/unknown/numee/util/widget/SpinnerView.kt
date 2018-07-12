package com.unknown.numee.util.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.unknown.numee.R


class SpinnerView : FrameLayout {

    private val title: TextView
    private val content: Spinner

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClicked(position: Int)
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        View.inflate(context, R.layout.view_spinner, this)

        title = findViewById(R.id.view_spinner__txt_title)
        content = findViewById(R.id.view_spinner__content)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpinnerView)
        try {
            title.text = typedArray.getString(R.styleable.SpinnerView_title)
        } finally {
            typedArray.recycle()
        }

        content.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onItemClickListener?.onItemClicked(position)
            }
        }
    }

    fun setItems(items: Array<String>) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        content.adapter = adapter
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}