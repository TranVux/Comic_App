package com.example.assignment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.assignment.R
import com.example.assignment.adapters.AdapterComicVertical.ComicVerticalViewHolder
import com.example.assignment.databinding.ComicLayoutVerticalBinding
import com.example.assignment.models.Comic

class AdapterComicVertical(
    private val context: Context,
    private var listComic: ArrayList<Comic>,
    private val comicListenerHandler: ComicListenerHandler
) : RecyclerView.Adapter<ComicVerticalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicVerticalViewHolder {
        val comicLayoutVerticalBinding =
            ComicLayoutVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicVerticalViewHolder(comicLayoutVerticalBinding)
    }

    override fun onBindViewHolder(
        holder: ComicVerticalViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.comicLayoutVerticalBinding.apply {
            Glide.with(context).load(listComic[position].thumbnail)
                .override(200, 230).placeholder(R.drawable.placeholder_image)
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageThumbnail)

            comicTitle.text = listComic[position].title

            comicLayout.setOnClickListener(View.OnClickListener {
                comicListenerHandler.onItemClick(listComic[position])
            })
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListComic(listComic: ArrayList<Comic>) {
        this.listComic = listComic
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listComic.size
    }

    class ComicVerticalViewHolder(val comicLayoutVerticalBinding: ComicLayoutVerticalBinding) :
        RecyclerView.ViewHolder(
            comicLayoutVerticalBinding.root
        )

    interface ComicListenerHandler {
        fun onItemClick(comic: Comic):Unit
    }
}