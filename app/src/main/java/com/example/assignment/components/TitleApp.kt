package com.example.assignment.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.assignment.R
import com.example.assignment.components.TitleApp

class TitleApp : LinearLayout, View.OnClickListener {
    private lateinit var title: TextView
    private var subTitle: TextView? = null
    private var moreButton: LinearLayout? = null
    private var titleButtonHandler: TitleButtonHandler? = null

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.title_app_layout, this)
        val style = getContext().theme.obtainStyledAttributes(attrs, R.styleable.TitleApp, 0, 0)
        init(style)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        LayoutInflater.from(context).inflate(R.layout.title_app_layout, this)
        val style = getContext().theme.obtainStyledAttributes(attrs, R.styleable.TitleApp, 0, 0)
        init(style)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        LayoutInflater.from(context).inflate(R.layout.title_app_layout, this)
        val style = getContext().theme.obtainStyledAttributes(attrs, R.styleable.TitleApp, 0, 0)
        init(style)
    }

    fun init(style: TypedArray?) {
        title = findViewById(R.id.title)
        subTitle = findViewById(R.id.subTitle)
        moreButton = findViewById(R.id.moreButton)

        moreButton!!.setOnClickListener(this@TitleApp)

        //init style for component
        if (style == null) return
        try {
            val textTitle = style.getString(R.styleable.TitleApp_app_title)
            val textSubTitle = style.getString(R.styleable.TitleApp_app_subTitle)
            val isMore = style.getBoolean(R.styleable.TitleApp_app_isMore, true)
            Log.d(TAG, "init: has handle: $textSubTitle $textTitle")

            //handle text title
            title.text = textTitle ?: ""

            //handle text sub title
            if (textSubTitle != null) {
                subTitle!!.text = textSubTitle
            }
            subTitle!!.visibility = if (isMore) VISIBLE else GONE

            //handle more button
            moreButton!!.visibility = if (isMore) VISIBLE else GONE
        } catch (e: Exception) {
            Log.d(TAG, "init: " + e.message)
        } finally {
            style.recycle()
        }
    }

    fun setTextTitle(text: String) {
        title.text = text
        requestLayout()
    }

    fun setTextSubTitle(text: String) {
        subTitle!!.text = text
        requestLayout()
    }

    fun setVisibleMoreButton(visible: Boolean) {
        moreButton!!.visibility = if (visible) VISIBLE else GONE
    }

    fun setTitleButtonHandler(titleButtonHandler: TitleButtonHandler) {
        this.titleButtonHandler = titleButtonHandler
        requestLayout()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.moreButton && titleButtonHandler != null) {
            titleButtonHandler!!.onMoreButtonClick()
        }
    }

    interface TitleButtonHandler {
        fun onMoreButtonClick()
    }

    companion object {
        private val TAG = TitleApp::class.java.simpleName
    }
}