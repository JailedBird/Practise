package cn.jailedbird.doc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.FrameMetrics
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.jailedbird.doc.service.TestService
import cn.jailedbird.doc.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


data class ZjcEvent(val message: String)

class MainActivity : AppCompatActivity() {
    // Message 持有Handler的target Handler持有匿名内部类的Activity
//    如果存在延迟 从消息队列取出之前就finish
//    使用静态内部类+ 软应用的方式获取Activity的访问权限而不是直接通过匿名内部类
    private val handle = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }

    private fun handleTask() {
        handle.sendMessage(Message.obtain())
    }

    val lazyValue: String by lazy {
        println("computed!") //第一次调用输出，第二次调用不执行
        "Hello"
    }

    interface P {
        fun print()
    }

    class A(private val p: P) : P by p {

    }

    fun test() {
        A(object : P {
            override fun print() {
                "fuck".log()
            }

        }).print()
    }


    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            (service as? TestService.ZjcBinder)?.let {
                "Main bind you".log()
                lifecycleScope.launch {
                    while (true) {
                        delay(1000)
                        it.log()
                    }
                }
                it.log()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            "Main unbind you".log()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        renderingPerformanceMeasurement()
        super.onCreate(savedInstanceState)
//        EventBus.getDefault().register(this)
//        "onCreate".log()
//        EventBus.getDefault().post(ZjcEvent("Fuck"))
        setContentView(R.layout.activity_main)
//        val viewGroup: ViewGroup? = null
//        viewGroup?.visibility  = View.GONE
//        viewGroup?.dispatchTouchEvent(null)

        okhttpRequest()
        initService()

        lifecycleScope.launch {
            while (true) {
                delay(2000)
            }
        }
    }


//    @Subscribe(threadMode = ThreadMode.BACKGROUND)
//    fun log(event: ZjcEvent) {
//        Thread.currentThread().name.log()
//        event.message.log()
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        EventBus.getDefault().unregister(this)
//    }


    private fun initService() {
        findViewById<Button>(R.id.startService).setOnClickListener {
            startService(Intent(this, TestService::class.java))
        }

        findViewById<Button>(R.id.stopService).setOnClickListener {
            stopService(Intent(this, TestService::class.java))
        }
        findViewById<Button>(R.id.bindService).setOnClickListener {
            bindService(
                Intent(this, TestService::class.java),
                serviceConnection,
                Context.BIND_AUTO_CREATE
            )
        }
        findViewById<Button>(R.id.unbindService).setOnClickListener {
            unbindService(serviceConnection)
        }
    }

    private fun okhttpRequest() {
        val request = Request.Builder().url("https://www.baidu.com").build()
        val okHttpClient = OkHttpClient()
        val call: Call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {  // 内部类 持有外部类的引用
            override fun onFailure(call: Call, e: IOException) {
                "Net failure".log()
            }

            override fun onResponse(call: Call, response: Response) {
                "Net success".log()
                response.body()?.string().log()
            }

        })

        // 取消任务 避免内存泄露
//        call.cancel()
    }

    /**
     * 渲染性能测量
     */
    fun renderingPerformanceMeasurement() {
        val handlerThread = HandlerThread("FrameMetrics")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        // 24 版本以后的 API 才能支持该选项
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 整个渲染过程测量都在 HandlerThread 线程中执行
            window.addOnFrameMetricsAvailableListener(
                { window, frameMetrics, dropCountSinceLastInvocation -> // 帧渲染测量完毕后, 会回调该方法
                    // 渲染性能参数都封装在 FrameMetrics frameMetrics 参数中

                    // 先拷贝一份, 之后从拷贝的数据中获取对应渲染时间参数
                    val fm = FrameMetrics(frameMetrics)

                    // 1. 动画执行回调时间, 单位纳秒
                    Log.i(
                        "FrameMetrics", "ANIMATION_DURATION : " +
                                fm.getMetric(FrameMetrics.ANIMATION_DURATION)
                    )

                    // 2. 向 GPU 发送绘制命令花费的时间, 单位纳秒
                    Log.i(
                        "FrameMetrics", "COMMAND_ISSUE_DURATION : " +
                                fm.getMetric(FrameMetrics.COMMAND_ISSUE_DURATION)
                    )

                    // 3. 将组件树 ( View Hierarchy ) 转为显示列表 ( DisplayLists )
                    // 计算过程所花费的时间, 单位纳秒
                    Log.i(
                        "FrameMetrics", "DRAW_DURATION : " +
                                fm.getMetric(FrameMetrics.DRAW_DURATION)
                    )

                    // 4. 绘制的该帧是否是第一帧, 0 是, 1 不是
                    // 第一帧渲染会慢一些
                    // 第一帧不会引发动画中的跳帧问题, 这些问题都会被窗口动画隐藏
                    // 不必进行显示过程中的 jank 计算
                    Log.i(
                        "FrameMetrics", "FIRST_DRAW_FRAME : " +
                                fm.getMetric(FrameMetrics.FIRST_DRAW_FRAME)
                    )

                    // 5. 处理输入事件花费的时间, 单位纳秒
                    Log.i(
                        "FrameMetrics", "INPUT_HANDLING_DURATION : " +
                                fm.getMetric(FrameMetrics.INPUT_HANDLING_DURATION)
                    )

                    // 6. 该值是个时间戳, 表示该帧的 vsync 信号发出时间
                    // 这个时间是当前帧的预期开始时间
                    // 如果该时间与 VSYNC_TIMESTAMP 时间戳不同
                    // 那么说明 UI 线程被阻塞了, 没有及时响应 vsync 信号
                    Log.i(
                        "FrameMetrics", "INTENDED_VSYNC_TIMESTAMP : " +
                                fm.getMetric(FrameMetrics.INTENDED_VSYNC_TIMESTAMP)
                    )

                    // 7. 组件树 ( view hierarchy ) 测量 ( measure ) 和摆放 ( layout ) 花费的时间
                    // 单位 纳秒
                    Log.i(
                        "FrameMetrics", "LAYOUT_MEASURE_DURATION : " +
                                fm.getMetric(FrameMetrics.LAYOUT_MEASURE_DURATION)
                    )

                    // 8. CPU 传递多维向量图形数据给 GPU 花费的时间, 单位纳秒
                    Log.i(
                        "FrameMetrics", "SWAP_BUFFERS_DURATION : " +
                                fm.getMetric(FrameMetrics.SWAP_BUFFERS_DURATION)
                    )

                    // 9. 显示列表 ( DisplayLists ) 与显示线程同步花费的时间, 单位纳秒
                    Log.i(
                        "FrameMetrics", "SYNC_DURATION : " +
                                fm.getMetric(FrameMetrics.SYNC_DURATION)
                    )

                    // 10. CPU 渲染到传递到 GPU 所用的总时间, 上述所花费的有意义的时间之和
                    // 单位纳秒
                    Log.i(
                        "FrameMetrics", "TOTAL_DURATION : " +
                                fm.getMetric(FrameMetrics.TOTAL_DURATION)
                    )

                    // 11. UI 线程响应并开始处理渲染的等待时间, 一般是 0, 如果大于 0 说明出问题了
                    Log.i(
                        "FrameMetrics", "UNKNOWN_DELAY_DURATION : " +
                                fm.getMetric(FrameMetrics.UNKNOWN_DELAY_DURATION)
                    )

                    // 12. vsync 信号发出的时间戳, 该时刻 GPU 应该进行绘制, 间隔 16ms
                    // 同时 CPU 开始渲染
                    Log.i(
                        "FrameMetrics", "VSYNC_TIMESTAMP : " +
                                fm.getMetric(FrameMetrics.VSYNC_TIMESTAMP)
                    )
                }, handler
            )
        }
    }


}
