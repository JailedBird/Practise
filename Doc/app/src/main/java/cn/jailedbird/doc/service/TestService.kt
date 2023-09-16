package cn.jailedbird.doc.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import cn.jailedbird.doc.util.log

class TestService : Service() {
    private val binder = ZjcBinder()

    class ZjcBinder() : Binder() {
        fun log() {
            "ZjcBinder".log()
        }
    }

    override fun onCreate() {
        super.onCreate()
        "onCreate".log()
    }

    override fun onBind(intent: Intent?): IBinder? {
        "onBind".log()
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        "onStartCommand".log()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy".log()
    }
}