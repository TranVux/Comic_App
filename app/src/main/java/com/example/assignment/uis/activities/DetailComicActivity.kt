package com.example.assignment.uis.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.R
import com.example.assignment.databinding.ActivityDetailComicBinding
import com.example.assignment.utils.AppAnimation

class DetailComicActivity : AppCompatActivity(), View.OnClickListener {
    private val activityDetailComicBinding: ActivityDetailComicBinding by lazy {
        ActivityDetailComicBinding.inflate(layoutInflater)
    }
    private var isExpandedSynopsisLayout = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityDetailComicBinding.root)
        init()
        addListenerEvent()
    }

    fun init() {}
    private fun addListenerEvent() {
        activityDetailComicBinding.expandSynopsisButton.setOnClickListener(this@DetailComicActivity)
        activityDetailComicBinding.readButton.setOnClickListener(this@DetailComicActivity)
        activityDetailComicBinding.favoriteButton.setOnClickListener(this@DetailComicActivity)
    }

    private fun handleExpandSynopsis() {
        if (isExpandedSynopsisLayout) {
            AppAnimation.collapse(
                activityDetailComicBinding.expandSynopsisLayout,
                150,
                activityDetailComicBinding.maskSynopsis
            )
            AppAnimation.rotate(activityDetailComicBinding.expandSynopsisButton, 0f, 500)
        } else {
            AppAnimation.expand(
                activityDetailComicBinding.expandSynopsisLayout,
                activityDetailComicBinding.maskSynopsis
            )
            AppAnimation.rotate(activityDetailComicBinding.expandSynopsisButton, 180f, 500)
        }
        isExpandedSynopsisLayout = !isExpandedSynopsisLayout
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.expand_synopsis_button -> handleExpandSynopsis()
            R.id.read_button -> Log.d(TAG, "onClick: read_button")
            R.id.favorite_button -> Log.d(TAG, "onClick: favorite_button")
        }
    }

    companion object {
        private val TAG = DetailComicActivity::class.java.simpleName
    }
}