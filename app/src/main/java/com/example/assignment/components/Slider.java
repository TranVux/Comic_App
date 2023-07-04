package com.example.assignment.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class Slider extends ViewPager {
    private float startDragX;
    private SliderHandler sliderHandler;

    public void setSliderHandler(SliderHandler sliderHandler) {
        this.sliderHandler = sliderHandler;
    }

    public Slider(@NonNull Context context) {
        super(context);
    }

    public Slider(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sliderHandler.onSwipeStart();
                break;
            case MotionEvent.ACTION_UP:
                sliderHandler.onSwipeEnd();
                break;
        }

        return super.onInterceptHoverEvent(event);
    }

    public interface SliderHandler {
        void onSwipeStart();

        void onSwipeEnd();
    }
}
