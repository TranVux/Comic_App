package com.example.assignment.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.assignment.R;
import com.example.assignment.models.Comic;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class SliderAdapter extends PagerAdapter {
    private ArrayList<Comic> listComic;
    private Context context;

    private HandleItemClick handleItemClick;

    public SliderAdapter(Context context, ArrayList<Comic> listComic, HandleItemClick handleItemClick) {
        this.context = context;
        this.listComic = listComic;
        this.handleItemClick = handleItemClick;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_slider_layout, container, false);

        BlurView blurView = view.findViewById(R.id.blur_view);
        TextView title = view.findViewById(R.id.title);
        TextView categories = view.findViewById(R.id.category);
        TextView synopsis = view.findViewById(R.id.synopsis);
        ImageView background = view.findViewById(R.id.background_image);
        ImageView thumbnail = view.findViewById(R.id.thumbnail_image);

        setUpBlurView(blurView);

        if (listComic.get(position) != null) {
            title.setText(listComic.get(position).getTitle());
            categories.setText(listComic.get(position).getCategories());
            synopsis.setText(listComic.get(position).getSynopsis());

            Glide.with(context).load(listComic.get(position).getThumbnail())
                    .override(330, 185).placeholder(R.drawable.placeholder_image)
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(background);

            Glide.with(context).load(listComic.get(position).getThumbnail())
                    .placeholder(R.drawable.placeholder_image)
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(thumbnail);

            blurView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleItemClick.onItemClick(listComic.get(position));
                }
            });

            blurView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        handleItemClick.onActionDown(position);
                    }
                    return false;
                }
            });
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (listComic == null) return 0;
        return listComic.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void setUpBlurView(BlurView blurView) {
        // làm mở ảnh
        float radius = 15f;
        View decorView = ((Activity) context).getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView, new RenderScriptBlur(context)) // or RenderEffectBlur
                .setFrameClearDrawable(windowBackground) // Optional
                .setBlurRadius(radius);

    }

    public interface HandleItemClick {
        void onItemClick(Comic comic);

        void onActionDown(int currentPos);
    }
}
