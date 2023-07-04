package com.example.assignment.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.assignment.R;
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

    public void setListCategory(ArrayList<Category> listCategory) {
        this.listCategory = listCategory;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (listCategory.get(position) == null) return;

        holder.categoryTitle.setText(listCategory.get(position).getTitle());
        holder.categoryBackground.setCardBackgroundColor(Color.parseColor(listCategory.get(position).getDisplayColor()));
        Glide.with(context).load(listCategory.get(position).getThumbnail())
                .placeholder(R.drawable.placeholder_image).override(100, 100)
                .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true)
                .into(holder.categoryImage);

        holder.categoryItem.setOnClickListener(new View.OnClickListener() {
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

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        LinearLayout categoryItem;
        ImageView categoryImage;
        CardView categoryBackground;
        TextView categoryTitle;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryItem = itemView.findViewById(R.id.item_category);
            categoryImage = itemView.findViewById(R.id.image_category);
            categoryBackground = itemView.findViewById(R.id.bg_category);
            categoryTitle = itemView.findViewById(R.id.category_text);
        }
    }

    public interface CategoryHandlerListener {
        void onItemClick(Category category);
    }
}
