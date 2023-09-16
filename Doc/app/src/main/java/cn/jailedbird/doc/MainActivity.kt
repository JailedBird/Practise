package cn.jailedbird.doc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.view.ViewGroup
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
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.IOException


data class ZjcEvent(val message: String)

class MainActivity : AppCompatActivity() {
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            (service as? TestService.ZjcBinder)?.let {
                "Main bind you".log()
                lifecycleScope.launch{
                    while (true){
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
            while (true){
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
            bindService(Intent(this, TestService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
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


}
