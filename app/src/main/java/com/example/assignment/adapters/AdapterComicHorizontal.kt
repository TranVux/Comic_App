package com.example.assignment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.assignment.R
import com.example.assignment.adapters.AdapterComicHorizontal.ComicHorizontalViewHolder
import com.example.assignment.databinding.ComicLayoutHorizontalBinding
import com.example.assignment.models.Comic

class AdapterComicHorizontal(
    private val context: Context,
    private var listComic: ArrayList<Comic>,
    private val comicListenerHandler: ComicListenerHandler
) : RecyclerView.Adapter<ComicHorizontalViewHolder>() {
    @SuppressLint("NotifyDataSetChanged")
    fun setListComic(listComic: ArrayList<Comic>) {
        this.listComic = listComic
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicHorizontalViewHolder {
        val comicLayoutHorizontalBinding =
            ComicLayoutHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicHorizontalViewHolder(comicLayoutHorizontalBinding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(
        holder: ComicHorizontalViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.comicLayoutHorizontalBinding.apply {
            Glide.with(context).load(listComic[position].thumbnail)
                .override(230, 210).placeholder(R.drawable.placeholder_image)
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageThumbnail)

            comicTitle.text = listComic[position].title
            comicCategory.text = listComic[position].categories.toString()

            comicLayout.setOnClickListener {
                comicListenerHandler.onItemClick(
                    listComic[position]
                )
            }

            comicLayout.setOnTouchListener { _: View?, motionEvent: MotionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    comicListenerHandler.onTouchStart()
                }
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return listComic.size
    }

    class ComicHorizontalViewHolder(val comicLayoutHorizontalBinding: ComicLayoutHorizontalBinding) :
        RecyclerView.ViewHolder(
            comicLayoutHorizontalBinding.root
        )

    interface ComicListenerHandler {
        fun onItemClick(comic: Comic)
        fun onTouchStart()
    }
}