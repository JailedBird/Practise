package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    companion object{
        @JvmStatic
        fun start(context: android.content.Context) {
            val starter = Intent(context, MainActivity::class.java)

            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.tv).setOnClickListener{
            startSelf()
        }
    }

    private fun startSelf(){
        start(this)

    }


}