package com.example.assignment.utils

import android.util.Log

object Utils {
    fun logD(mes: String) {
        Log.d("${Utils::class.java.simpleName}_Debug", mes)
    }

    fun logE(err: String) {
        Log.e("${Utils::class.java.simpleName}_Error", err)
    }
}