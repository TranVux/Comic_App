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
import com.example.assignment.databinding.ItemSliderLayoutBinding;
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
        ItemSliderLayoutBinding itemSliderLayoutBinding = ItemSliderLayoutBinding.inflate(LayoutInflater.from(container.getContext()), container, false);
        setUpBlurView(itemSliderLayoutBinding.blurView);

        if (listComic.get(position) != null) {
            itemSliderLayoutBinding.title.setText(listComic.get(position).getTitle());
            itemSliderLayoutBinding.category.setText(listComic.get(position).getCategories());
            itemSliderLayoutBinding.synopsis.setText(listComic.get(position).getSynopsis());

            Glide.with(context).load(listComic.get(position).getThumbnail())
                    .override(330, 185).placeholder(R.drawable.placeholder_image)
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemSliderLayoutBinding.backgroundImage);

            Glide.with(context).load(listComic.get(position).getThumbnail())
                    .placeholder(R.drawable.placeholder_image)
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemSliderLayoutBinding.thumbnailImage);

            itemSliderLayoutBinding.blurView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleItemClick.onItemClick(listComic.get(position));
                }
            });

            itemSliderLayoutBinding.blurView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        handleItemClick.onActionDown(position);
                    }
                    return false;
                }
            });
        }

        container.addView(itemSliderLayoutBinding.getRoot());

        return itemSliderLayoutBinding.getRoot();
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
