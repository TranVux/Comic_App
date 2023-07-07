package com.example.assignment.utils

import android.content.Context
import android.view.View

object Convert {
    fun pixelsToSp(context: Context, px: Float): Float {
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        return px / scaledDensity
    }

    fun dpToPixel(v: View, dp: Int): Int {
        return (dp * v.context.resources.displayMetrics.density).toInt()
    }

    fun pixelToDp(v: View, pixel: Int): Int {
        return (pixel / v.context.resources.displayMetrics.density).toInt()
    }
}