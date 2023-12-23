package com.example.assignment.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.assignment.R
import com.example.assignment.databinding.ItemSliderLayoutBinding
import com.example.assignment.models.Comic
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

class SliderAdapter(
    private val context: Context,
    private var listComic: ArrayList<Comic>,
    private val handleItemClick: HandleItemClick
) : PagerAdapter() {

    fun getList():ArrayList<Comic>{
        return listComic;
    }

    fun setList(list: ArrayList<Comic>){
        listComic = list;
        notifyDataSetChanged()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemSliderLayoutBinding = ItemSliderLayoutBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            false
        )
        setUpBlurView(itemSliderLayoutBinding.blurView)

        itemSliderLayoutBinding.apply {
            title.text = listComic[position].title
            category.text = listComic[position].categories
            synopsis.text = listComic[position].synopsis

            Glide.with(context).load(listComic[position].thumbnail)
                .override(330, 185).placeholder(R.drawable.placeholder_image)
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(backgroundImage)

            Glide.with(context).load(listComic[position].thumbnail)
                .placeholder(R.drawable.placeholder_image)
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(thumbnailImage)

            blurView.setOnClickListener {
                handleItemClick.onItemClick(
                    listComic[position]
                )
            }

            blurView.setOnTouchListener { _, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    handleItemClick.onActionDown(position)
                }
                false
            }
        }

        container.addView(itemSliderLayoutBinding.root)
        return itemSliderLayoutBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return listComic.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    private fun setUpBlurView(blurView: BlurView) {
        // làm mở ảnh
        val radius = 15f
        val decorView = (context as Activity).window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowBackground = decorView.background
        blurView.setupWith(rootView, RenderScriptBlur(context)) // or RenderEffectBlur
            .setFrameClearDrawable(windowBackground) // Optional
            .setBlurRadius(radius)
    }

    interface HandleItemClick {
        fun onItemClick(comic: Comic)
        fun onActionDown(currentPos: Int)
    }
}