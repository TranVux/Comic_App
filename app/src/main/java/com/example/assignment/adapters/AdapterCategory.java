package com.example.assignment.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.assignment.R;
import com.example.assignment.databinding.CategoryLayoutItemBinding;
import com.example.assignment.models.Category;

import java.util.ArrayList;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.CategoryViewHolder> {
    private Context context;
    private ArrayList<Category> listCategory;
    private CategoryHandlerListener categoryHandlerListener;

    public AdapterCategory(Context context, ArrayList<Category> listCategory, CategoryHandlerListener categoryHandlerListener) {
        this.context = context;
        this.listCategory = listCategory;
        this.categoryHandlerListener = categoryHandlerListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListCategory(ArrayList<Category> listCategory) {
        this.listCategory = listCategory;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryLayoutItemBinding categoryLayoutItemBinding = CategoryLayoutItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(categoryLayoutItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (listCategory.get(position) == null) return;

        holder.categoryLayoutItemBinding.categoryText.setText(listCategory.get(position).getTitle());
        holder.categoryLayoutItemBinding.bgCategory.setCardBackgroundColor(Color.parseColor(listCategory.get(position).getDisplayColor()));
        Glide.with(context).load(listCategory.get(position).getThumbnail())
                .placeholder(R.drawable.placeholder_image).override(100, 100)
                .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true)
                .into(holder.categoryLayoutItemBinding.imageCategory);

        holder.categoryLayoutItemBinding.itemCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryHandlerListener.onItemClick(listCategory.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listCategory == null) return 0;
        return listCategory.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final CategoryLayoutItemBinding categoryLayoutItemBinding;

        public CategoryViewHolder(CategoryLayoutItemBinding categoryLayoutItemBinding) {
            super(categoryLayoutItemBinding.getRoot());
            this.categoryLayoutItemBinding = categoryLayoutItemBinding;
        }
    }

    public interface CategoryHandlerListener {
        void onItemClick(Category category);
    }
}
