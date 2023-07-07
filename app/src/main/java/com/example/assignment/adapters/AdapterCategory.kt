package com.example.assignment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.assignment.R
import com.example.assignment.adapters.AdapterCategory.CategoryViewHolder
import com.example.assignment.databinding.CategoryLayoutItemBinding
import com.example.assignment.models.Category

class AdapterCategory(
    private val context: Context,
    private var listCategory: ArrayList<Category>,
    private val categoryHandlerListener: CategoryHandlerListener
) : RecyclerView.Adapter<CategoryViewHolder>() {
    @SuppressLint("NotifyDataSetChanged")
    fun setListCategory(listCategory: ArrayList<Category>) {
        this.listCategory = listCategory
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val categoryLayoutItemBinding =
            CategoryLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(categoryLayoutItemBinding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.categoryLayoutItemBinding.apply {
            categoryText.text = listCategory[position].title
            bgCategory.setCardBackgroundColor(
                Color.parseColor(
                    listCategory[position].displayColor
                )
            )
            Glide.with(context).load(listCategory[position].thumbnail)
                .placeholder(R.drawable.placeholder_image).override(100, 100)
                .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true)
                .into(imageCategory)

            itemCategory.setOnClickListener(View.OnClickListener {
                listCategory[position].let { item ->
                    categoryHandlerListener.onItemClick(item)
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    class CategoryViewHolder(val categoryLayoutItemBinding: CategoryLayoutItemBinding) :
        RecyclerView.ViewHolder(
            categoryLayoutItemBinding.root
        )

    interface CategoryHandlerListener {
        fun onItemClick(category: Category)
    }
}