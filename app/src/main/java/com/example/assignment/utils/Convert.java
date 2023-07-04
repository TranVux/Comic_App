package com.example.assignment.utils;

import android.content.Context;
import android.view.View;

public class Convert {
    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public static int dpToPixel(View v, int dp) {
        return (int) (dp * v.getContext().getResources().getDisplayMetrics().density);
    }

    public static int pixelToDp(View v, int pixel) {
        return (int) (pixel / v.getContext().getResources().getDisplayMetrics().density);
    }

}
