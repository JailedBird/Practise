package cn.jailedbird.study.practiceservice

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ExampleStartService : Service() {
    companion object {
        private const val TAG = "ExampleStartService"
    }

    override fun onCreate() {
        super.onCreate()
        "$TAG onCreate".log()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}