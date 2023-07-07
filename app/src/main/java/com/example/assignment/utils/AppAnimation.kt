package com.example.assignment.utils

import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation

object AppAnimation {
    fun expand(v: View, maskView: View): Int {
        val matchParentMeasureSpec =
            View.MeasureSpec.makeMeasureSpec((v.parent as View).width, View.MeasureSpec.EXACTLY)
        val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST)
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
        val targetHeight = v.measuredHeight

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
//        v.getLayoutParams().height = 1;
//        v.setVisibility(View.VISIBLE);
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                v.layoutParams.height =
                    if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        maskView.animate().alpha(0f)
            .setDuration(200)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction { maskView.visibility = View.GONE }
        // Expansion speed of 1dp/ms
        a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        a.interpolator = AccelerateDecelerateInterpolator()
        v.startAnimation(a)
        return (targetHeight / v.context.resources.displayMetrics.density).toInt() * 10
    }

    fun collapse(v: View, targetHeight: Int, maskView: View): Int {
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                val height =
                    if (interpolatedTime <= 1) targetHeight else (targetHeight * interpolatedTime).toInt()
                v.layoutParams.height =
                    (height * v.context.resources.displayMetrics.density).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        maskView.animate().alpha(1f)
            .setDuration(((targetHeight / v.context.resources.displayMetrics.density).toInt() * 10).toLong())
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withStartAction { maskView.visibility = View.VISIBLE }

        // Collapse speed of 1dp/ms
        a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        a.interpolator = AccelerateDecelerateInterpolator()
        v.startAnimation(a)
        return (targetHeight / v.context.resources.displayMetrics.density).toInt() * 10
    }

    fun rotate(view: View, angle: Float, duration: Int) {
        view.animate().rotation(angle)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(duration.toLong())
            .withEndAction { view.rotation = angle }
            .start()
    }

    fun fadeIn(view: View) {
        view.animate().alpha(0f).setDuration(200).withEndAction { view.visibility = View.GONE }
    }

    fun fadeOut(view: View) {
        view.animate().alpha(1f).setDuration(200).withStartAction { view.visibility = View.VISIBLE }
    }
}