package com.example.assignment.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
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
import com.example.assignment.models.Comic;

import java.util.ArrayList;

public class AdapterComicVertical extends RecyclerView.Adapter<AdapterComicVertical.ComicVerticalViewHolder> {
    private ArrayList<Comic> listComic;
    private Context context;
    private ComicListenerHandler comicListenerHandler;

    public AdapterComicVertical(Context context, ArrayList<Comic> listComic, ComicListenerHandler comicListenerHandler) {
        this.listComic = listComic;
        this.context = context;
        this.comicListenerHandler = comicListenerHandler;
    }

    @NonNull
    @Override
    public ComicVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_layout_vertical, parent, false);
        return new ComicVerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicVerticalViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (listComic.get(position) == null) return;

        Glide.with(context).load(listComic.get(position).getThumbnail())
                .override(120, 150).placeholder(R.drawable.placeholder_image)
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);

        holder.comicTitle.setText(listComic.get(position).getTitle());

        holder.comicLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comicListenerHandler.onItemClick(listComic.get(position));
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListComic(ArrayList<Comic> listComic) {
        this.listComic = listComic;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (listComic == null) return 0;
        return listComic.size();
    }

    public class ComicVerticalViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView comicTitle;
        LinearLayout comicLayout;

        public ComicVerticalViewHolder(@NonNull View itemView) {
            super(itemView);

            comicLayout = itemView.findViewById(R.id.comic_layout);
            thumbnail = itemView.findViewById(R.id.image);
            comicTitle = itemView.findViewById(R.id.comic_title);
        }
    }

    public interface ComicListenerHandler {
        void onItemClick(Comic comic);
    }
}
