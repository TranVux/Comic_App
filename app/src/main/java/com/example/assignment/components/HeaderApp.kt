package com.example.assignment.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.assignment.R
import com.example.assignment.components.HeaderApp
import java.sql.Date
import java.text.SimpleDateFormat

class HeaderApp : RelativeLayout, View.OnClickListener {
    private var headerButtonHandler: HeaderButtonHandler? = null
    private var leftContainer: LinearLayout? = null
    private var centerContainer: LinearLayout? = null
    private var rightContainer: LinearLayout? = null
    private var userContainer: LinearLayout? = null
    private var parentContainer: RelativeLayout? = null
    private var buttonLeft: ImageView? = null
    private var buttonRight: ImageView? = null
    private var avatar: ImageView? = null
    private var username: TextView? = null
    private var textSession: TextView? = null
    private var titleHeader: TextView? = null
    private var subTitleHeader: TextView? = null
    private var isUserHeader = false
    private val hasSubTitle = false

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.header_component, this)
        val style = context.theme.obtainStyledAttributes(attrs, R.styleable.HeaderApp, 0, 0)
        init(style)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        LayoutInflater.from(context).inflate(R.layout.header_component, this)
        val style = context.theme.obtainStyledAttributes(attrs, R.styleable.HeaderApp, 0, 0)
        init(style)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        LayoutInflater.from(context).inflate(R.layout.header_component, this)
        val style = context.theme.obtainStyledAttributes(attrs, R.styleable.HeaderApp, 0, 0)
        init(style)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        clickListener()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun init(style: TypedArray) {
        parentContainer = findViewById(R.id.parentContainer)
        leftContainer = findViewById(R.id.left_container)
        rightContainer = findViewById(R.id.right_container)
        centerContainer = findViewById(R.id.center_container)
        userContainer = findViewById(R.id.user_container)
        buttonLeft = findViewById(R.id.button_left)
        buttonRight = findViewById(R.id.button_right)
        avatar = findViewById(R.id.avatar_image)
        titleHeader = findViewById(R.id.title_header)
        subTitleHeader = findViewById(R.id.sub_title_header)
        username = findViewById(R.id.user_name)
        textSession = findViewById(R.id.text_session)
        try {
            //handle left container
            isUserHeader = style.getBoolean(R.styleable.HeaderApp_app_is_user_container, false)
            if (isUserHeader) {
                //handle get init variable
                userContainer!!.visibility = VISIBLE
                val userName = style.getString(R.styleable.HeaderApp_app_user_name_text)
                val image_avatar = style.getDrawable(R.styleable.HeaderApp_app_avatar_src)
                username!!.text = userName ?: "Guest!"
                avatar!!.setImageDrawable(
                    image_avatar ?: resources.getDrawable(
                        R.drawable.avtw,
                        context.theme
                    )
                )
                textSession!!.text = createSessionText()

                //handle button left
                buttonLeft!!.visibility = GONE

                //handle title header
                titleHeader!!.visibility = GONE
                subTitleHeader!!.visibility = GONE
            } else {
                val image_button_left_src =
                    style.getDrawable(R.styleable.HeaderApp_app_button_left_src)
                buttonLeft!!.setImageDrawable(
                    image_button_left_src ?: resources.getDrawable(
                        R.drawable.ic_back,
                        context.theme
                    )
                )
                buttonLeft!!.visibility = VISIBLE
                userContainer!!.visibility = GONE
            }
            //end handle left container

            //handler center container
            val titleText = style.getString(R.styleable.HeaderApp_app_header_title)
            val hasSubTitle =
                style.getBoolean(R.styleable.HeaderApp_app_has_sub_header_title, false)
            titleHeader!!.text = titleText ?: ""
            if (hasSubTitle) {
                val subText = style.getString(R.styleable.HeaderApp_app_sub_header_title)
                subTitleHeader!!.visibility = VISIBLE
                subTitleHeader!!.text = subText ?: ""
            } else {
                subTitleHeader!!.visibility = GONE
            }

            //end handle center container

            //handle right container
            val isEnable = style.getBoolean(R.styleable.HeaderApp_app_enable_right_button, true)
            Log.d(TAG, "init: $isEnable")
            if (isEnable) {
                val image_button_right_src =
                    style.getDrawable(R.styleable.HeaderApp_app_button_right_src)
                buttonRight!!.setImageDrawable(
                    image_button_right_src ?: resources.getDrawable(
                        R.drawable.ic_search,
                        context.theme
                    )
                )
            } else {
                buttonRight!!.visibility = GONE
            }
        } catch (e: Exception) {
            Log.d(TAG, "init: $e")
        } finally {
            style.recycle()
        }
    }

    private fun clickListener() {
        buttonLeft!!.setOnClickListener(this@HeaderApp)
        buttonRight!!.setOnClickListener(this@HeaderApp)
        userContainer!!.setOnClickListener(this@HeaderApp)
        centerContainer!!.setOnClickListener(this@HeaderApp)
    }

    fun setHeaderAppVisibility(visibility: Int) {
        parentContainer!!.visibility = visibility
        requestLayout()
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_left -> {
                if (!isUserHeader && headerButtonHandler != null) {
                    headerButtonHandler!!.onLeftButtonClick()
                }
            }
            R.id.button_right -> {
                if (headerButtonHandler != null) {
                    headerButtonHandler!!.onRightButtonClick()
                }
            }
            R.id.user_container -> {
                if (isUserHeader && headerButtonHandler != null) {
                    headerButtonHandler!!.onUserContainerClick()
                }
            }
            R.id.center_container -> if (!isUserHeader) {
                headerButtonHandler!!.onCenterContainerClick()
            }
        }
    }

    fun setHeaderButtonHandler(headerButtonHandler: HeaderButtonHandler?) {
        this.headerButtonHandler = headerButtonHandler
    }

    fun setUserAvatar(url: String?) {
        if (isUserHeader) {
            Glide.with(context).load(url)
                .override(70, 70).placeholder(R.drawable.placeholder_image)
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(avatar!!)
            requestLayout()
        }
    }

    fun setEnableButtonRight(isEnable: Boolean) {
        buttonRight!!.visibility = if (isEnable) VISIBLE else GONE
        requestLayout()
    }

    fun setUserAvatar(resID: Int) {
        if (isUserHeader) {
            avatar!!.setImageResource(resID)
            requestLayout()
        }
    }

    fun setTitle(title: String) {
        titleHeader!!.text = title
        requestLayout()
    }

    fun setSubTitle(subTitle: String) {
        subTitleHeader!!.text = subTitle
        requestLayout()
    }

    private fun createSessionText(): String {
        @SuppressLint("SimpleDateFormat") val hours =
            SimpleDateFormat("HH").format(Date(System.currentTimeMillis())).toInt()
        return if (hours in 1..10) {
            "Chào buổi sáng"
        } else if (hours in 10..18) {
            "Chào buổi chiều"
        } else {
            "Chào buổi tối"
        }
    }

    interface HeaderButtonHandler {
        fun onLeftButtonClick()
        fun onRightButtonClick()
        fun onUserContainerClick()
        fun onCenterContainerClick()
    }

    companion object {
        private val TAG = HeaderApp::class.java.simpleName
    }
}