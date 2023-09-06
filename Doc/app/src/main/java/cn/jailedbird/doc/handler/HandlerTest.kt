package cn.jailedbird.doc.handler

import android.os.Handler
import android.os.Looper
import kotlin.concurrent.thread

object HandlerTest {
    private val threadLoader = ThreadLocal<Boolean>()
    fun test() {
        println(threadLoader.get() ?: "null")
        thread {
            Looper.prepare()
            Handler(Looper.myLooper()!!)
            Looper.loop()

        }
    }

}