package com.eit.test

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.annotation.RequiresApi

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), Timer.NotifyEvent, FragmentInteraction {

    // create a singleton of Timer
    private lateinit var timer: Timer

    @RequiresApi(Build.VERSION_CODES.P)
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 100) {
                val current: Long = msg.obj as Long
                display(current.toString())
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timer = Timer(this, handler)

        // inflate the fragments
        onStarted()
    }


    override fun display(currentTime: String) {
        // update value to history fragment
        Log.d(TAG, "display: $currentTime")
    }

    override fun onStarted() {
        timer.start()
    }

    override fun onPaused() {
        timer.pause()
    }

    override fun onStopped() {
        timer.stop()
    }
}

interface FragmentInteraction{
    fun onStarted()
    fun onPaused()
    fun onStopped()
}

/*
Simple 2 Minute Stop Timer Coding Challenge:
This coding challenge is not about rushing to finish but rather planning and designing the app and following the coding standard.

Requirement:
1. Single Activity with three fragments (start timer, stop timer, history).
2. Once you start the timer it should countdown from 02:00 to 00:00.
3. Once the timer is finished or is manually stopped you need to save it in history for future reference (even after killing and re-opening the app).
4. Pause action should temporarily pause the timer (and change the button text/action to resume).
5. If you exit out of the app while the timer is running (back press) then you still need to countdown in the background.
6. The same (5) applies to Pause.

Addons:
1. Account for future expansion to sync the data to the cloud.
2. Unit tests for any utility functions.
 */