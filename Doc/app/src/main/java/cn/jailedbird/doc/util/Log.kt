package cn.jailedbird.doc.util

import android.util.Log

fun String?.log() {
    Log.d("App", this ?: "null")
}