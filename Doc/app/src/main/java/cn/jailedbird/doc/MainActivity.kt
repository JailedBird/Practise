package cn.jailedbird.doc

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import cn.jailedbird.doc.util.log
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


data class ZjcEvent(val message: String)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        "onCreate".log()
        EventBus.getDefault().post(ZjcEvent("Fuck"))
        setContentView(R.layout.activity_main)
        val viewGroup: ViewGroup? = null
        viewGroup?.dispatchTouchEvent(null)

    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun log(event: ZjcEvent) {
        Thread.currentThread().name.log()
        event.message.log()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}
