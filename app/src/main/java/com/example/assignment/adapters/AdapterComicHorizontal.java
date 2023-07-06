package com.example.assignment.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.assignment.R;
import com.example.assignment.databinding.ComicLayoutHorizontalBinding;
import com.example.assignment.models.Comic;

import java.util.ArrayList;

public class AdapterComicHorizontal extends RecyclerView.Adapter<AdapterComicHorizontal.ComicHorizontalViewHolder> {

    private ArrayList<Comic> listComic;
    private Context context;
    private ComicListenerHandler comicListenerHandler;

    public AdapterComicHorizontal(Context context, ArrayList<Comic> listComic, ComicListenerHandler comicListenerHandler) {
        this.listComic = listComic;
        this.context = context;
        this.comicListenerHandler = comicListenerHandler;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListComic(ArrayList<Comic> listComic) {
        this.listComic = listComic;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComicHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ComicLayoutHorizontalBinding comicLayoutHorizontalBinding =ComicLayoutHorizontalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ComicHorizontalViewHolder(comicLayoutHorizontalBinding);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ComicHorizontalViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (listComic.get(position) == null) return;

        Glide.with(context).load(listComic.get(position).getThumbnail())
                .override(230, 210).placeholder(R.drawable.placeholder_image)
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.comicLayoutHorizontalBinding.imageThumbnail);

        holder.comicLayoutHorizontalBinding.comicTitle.setText(listComic.get(position).getTitle());
        holder.comicLayoutHorizontalBinding.comicCategory.setText(listComic.get(position).getCategories());

        holder.comicLayoutHorizontalBinding.comicLayout.setOnClickListener(view -> comicListenerHandler.onItemClick(listComic.get(position)));

        holder.comicLayoutHorizontalBinding.comicLayout.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                comicListenerHandler.onTouchStart();
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        if (listComic == null) return 0;
        return listComic.size();
    }

    public static class ComicHorizontalViewHolder extends RecyclerView.ViewHolder {
        private final ComicLayoutHorizontalBinding comicLayoutHorizontalBinding;
        public ComicHorizontalViewHolder(ComicLayoutHorizontalBinding comicLayoutHorizontalBinding) {
            super(comicLayoutHorizontalBinding.getRoot());
            this.comicLayoutHorizontalBinding = comicLayoutHorizontalBinding;
        }
    }

    public interface ComicListenerHandler {
        void onItemClick(Comic comic);

        void onTouchStart();
    }
}
