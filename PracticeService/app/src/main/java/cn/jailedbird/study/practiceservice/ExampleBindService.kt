package cn.jailedbird.study.practiceservice

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ExampleBindService : Service() {

    companion object {
        private const val TAG = "ExampleBindService"
    }

    override fun onCreate() {
        super.onCreate()
        "$TAG onCreate".log()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}