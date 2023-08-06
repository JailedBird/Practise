package cn.jailedbird.study.practiceservice

import android.util.Log

private const val TAG = "zjc"
fun String?.log() {
    Log.d(TAG, this ?: "null")
}