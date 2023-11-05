package cn.jailedbird.viewdemos

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    private var seekBar: SeekBar? = null
    private var waveView: WaveView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        seekBar = findViewById<View>(R.id.seek_bar) as SeekBar
        waveView = findViewById<View>(R.id.wave_view) as WaveView
        seekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                waveView?.setProgress(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}
