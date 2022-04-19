package com.example.wanandroid.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {

    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}