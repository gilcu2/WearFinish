package com.gilcu2.wearfinish

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView

class MainActivityMobile : AppCompatActivity() {

    val DEBUG_TAG = "MainActivity"

    private var mTextView: TextView? = null

    val mDetector by lazy { GestureDetectorCompat(this, MyGestureListener()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextView = findViewById(R.id.text) as TextView
        mTextView!!.setOnClickListener {
            val intent = Intent(this@MainActivityMobile, SecondActivityMobile::class.java)
            startActivity(intent)
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "dispatchTouchEvent: " + event)

        var result = super.dispatchTouchEvent(event)
        Log.d(DEBUG_TAG, "super Result: " + result)
        if (!result)
            result = mDetector.onTouchEvent(event)
        Log.d(DEBUG_TAG, "finalResult: " + result)

        return result
    }

    internal inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        private val DEBUG_TAG = "GesturesMain"

        private val SWIPE_MIN_DISTANCE = 120
        private val SWIPE_MAX_OFF_PATH = 200
        private val SWIPE_THRESHOLD_VELOCITY = 200

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float):
                Boolean {
            Log.d(DEBUG_TAG, "onScroll: " + velocityX + " " + velocityY)

            try {
                val diffAbs = Math.abs(e1.y - e2.y)
                val diff = e1.x - e2.x

                if (diffAbs > SWIPE_MAX_OFF_PATH) {
                    return false
                }

                // Left swipe
                if (diff > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(DEBUG_TAG, "Start second activity")
                    val intent = Intent(this@MainActivityMobile, SecondActivityMobile::class.java)
                    startActivity(intent)
                    return true
                }

                // Right swipe
                else if (-diff > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    finish()
                    return true
                }
            } catch (e: Exception) {
                Log.e(DEBUG_TAG, "Error on gestures")
            }

            return false


        }
    }
}
