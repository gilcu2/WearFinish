package com.gilcu2.wearfinish

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.support.wearable.activity.WearableActivity
import android.support.wearable.view.BoxInsetLayout
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SecondActivity : WearableActivity() {

    val DEBUG_TAG = "SecondActivity"

    private var mContainerView: BoxInsetLayout? = null
    private var mTextView: TextView? = null
    private var mClockView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(DEBUG_TAG, "OnCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setAmbientEnabled()

        mContainerView = findViewById(R.id.container) as BoxInsetLayout
        mTextView = findViewById(R.id.text) as TextView
        mClockView = findViewById(R.id.clock) as TextView

        mTextView!!.setOnClickListener { finish() }
    }

    override fun onEnterAmbient(ambientDetails: Bundle?) {
        super.onEnterAmbient(ambientDetails)
        updateDisplay()
    }

    override fun onUpdateAmbient() {
        super.onUpdateAmbient()
        updateDisplay()
    }

    override fun onExitAmbient() {
        updateDisplay()
        super.onExitAmbient()
    }

    private fun updateDisplay() {
        if (isAmbient) {
            mContainerView!!.setBackgroundColor(resources.getColor(android.R.color.black))
            mTextView!!.setTextColor(resources.getColor(android.R.color.white))
            mClockView!!.visibility = View.VISIBLE

            mClockView!!.text = AMBIENT_DATE_FORMAT.format(Date())
        } else {
            mContainerView!!.background = null
            mTextView!!.setTextColor(resources.getColor(android.R.color.black))
            mClockView!!.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        Log.d(DEBUG_TAG, "OnDestroy")
        super.onDestroy()
    }

    companion object {

        private val AMBIENT_DATE_FORMAT = SimpleDateFormat("HH:mm", Locale.US)
    }
}
