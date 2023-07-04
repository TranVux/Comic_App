package com.example.assignment.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class AppAnimation {
    public static int expand(final View v, final View maskView) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
//        v.getLayoutParams().height = 1;
//        v.setVisibility(View.VISIBLE);

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        maskView.animate().alpha(0)
                .setDuration(200)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        maskView.setVisibility(View.GONE);
                    }
                });
        // Expansion speed of 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setInterpolator(new AccelerateDecelerateInterpolator());
        v.startAnimation(a);

        return (int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) * 10;
    }

    public static int collapse(final View v, final int targetHeight, final View maskView) {
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                int height = interpolatedTime <= 1
                        ? targetHeight
                        : (int) (targetHeight * interpolatedTime);
                v.getLayoutParams().height = (int) (height * v.getContext().getResources().getDisplayMetrics().density);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        maskView.animate().alpha(1)
                .setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) * 10)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        maskView.setVisibility(View.VISIBLE);
                    }
                });

        // Collapse speed of 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setInterpolator(new AccelerateDecelerateInterpolator());
        v.startAnimation(a);

        return (int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) * 10;
    }

    public static void rotate(final View view, float angle, int duration) {
        view.animate().rotation(angle)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(duration)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        view.setRotation(angle);
                    }
                })
                .start();
    }

    public static void fadeIn(final View view) {
        view.animate().alpha(0).setDuration(200).withEndAction(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        });
    }

    public static void fadeOut(final View view) {
        view.animate().alpha(1).setDuration(200).withStartAction(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.VISIBLE);
            }
        });
    }
}
