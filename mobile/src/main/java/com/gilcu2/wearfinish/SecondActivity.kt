package com.gilcu2.wearfinish

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class SecondActivityMobile : AppCompatActivity() {

    val DEBUG_TAG = "SecondActivity"

    private var mTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        mTextView = findViewById(R.id.text) as TextView
        mTextView!!.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        Log.d(DEBUG_TAG, "OnDestroy")
        super.onDestroy()
    }
}
