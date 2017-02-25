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

class MainActivity : WearableActivity() {

    val DEBUG_TAG = "MainActivity"

    private var mContainerView: BoxInsetLayout? = null
    private var mTextView: TextView? = null
    private var mClockView: TextView? = null

    val mDetector by lazy { GestureDetectorCompat(this, MyGestureListener()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAmbientEnabled()

        mContainerView = findViewById(R.id.container) as BoxInsetLayout
        mTextView = findViewById(R.id.text) as TextView
        mClockView = findViewById(R.id.clock) as TextView

//        mTextView!!.setOnClickListener {
//            val intent = Intent(this@MainActivity, SecondActivity::class.java)
//            startActivity(intent)
//        }
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

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "dispatchTouchEvent: " + event)

        var result = mDetector!!.onTouchEvent(event)
        Log.d(DEBUG_TAG, "detector Result: " + result)
        if (!result)
            result = super.dispatchTouchEvent(event)
        Log.d(DEBUG_TAG, "finalResult: " + result)

        return result
    }

    internal inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        private val DEBUG_TAG = "GesturesMain"

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float,
                              distanceY: Float): Boolean {
            Log.d(DEBUG_TAG, "onScroll: " + distanceX + " " + distanceY)

            if (Math.abs(distanceX) > 2 * Math.abs(distanceY)) {
                if (distanceX < -10) {
                    finish()
                    return true
                } else if (distanceX > 10) {
                    Log.d(DEBUG_TAG, "Start second activity")
                    val intent = Intent(this@MainActivity, SecondActivity::class.java)
                    startActivity(intent)
                    return true
                }

            }

            return false

        }
    }

    companion object {

        private val AMBIENT_DATE_FORMAT = SimpleDateFormat("HH:mm", Locale.US)
    }
}
