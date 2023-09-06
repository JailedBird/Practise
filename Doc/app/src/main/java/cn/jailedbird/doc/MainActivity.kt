package cn.jailedbird.doc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import cn.jailedbird.doc.util.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "onCreate".log()
        setContentView(R.layout.activity_main)
        val viewGroup: ViewGroup? = null
        viewGroup?.dispatchTouchEvent(null)

    }


}
