package com.example.wanandroid.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.util.Log

object ClipBoardUtil {
    private const val TAG = "ClipBoardUtil"

    @JvmStatic
    fun saveTextToClipBoard(context: Context, text: String) {
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("simple text", text)
        clipboardManager.setPrimaryClip(clipData)
        Log.d(TAG, "text saveToClipBoard")
    }

    @JvmStatic
    fun saveUriToClipBoard(context: Context, uri: Uri) {
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newUri(context.contentResolver, "uri", uri)
        clipboardManager.setPrimaryClip(clipData)
        Log.d(TAG, "uri saveToClipBoard")
    }

    @JvmStatic
    fun saveIntentToClipBoard(context: Context, intent: Intent) {
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newIntent("intent", intent)
        clipboardManager.setPrimaryClip(clipData)
        Log.d(TAG, "intent saveToClipBoard")
    }

    @JvmStatic
    fun getClipData(context: Context): ClipData? {
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        return clipboardManager.primaryClip
    }

}