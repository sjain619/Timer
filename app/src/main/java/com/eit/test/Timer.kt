package com.eit.test

import android.os.Handler
import android.os.Message
import android.util.Log

private const val TAG = "Timer"

class Timer(private val listener: NotifyEvent, private val handler: Handler) {

    private var time: Long = 3000 //2000 * 60
    private var isPaused = false

    interface NotifyEvent{
        fun display(currentTime: String)
    }


    /**
     * This will be a blocking event
     * We need to create a thread to execute this...
     */
    private fun downTimer(){
        Thread(Runnable {
            while (time > 0L && !isPaused) {
                Thread.sleep(1000) // decreasing 1 second
                time -= 1000
                Log.d(TAG, "downTimer: $time")
            }
            // usgin handler to pass the message
            stop()
        }).start()
        // reach to 00:00 call stop
        // this is called immediatelly
        // handler.post()
    }

    /**
     * This will be called from MainThread...
     */
    fun start(){
        downTimer()
    }

    /**
     * MainThread...
     */
    fun stop(){
        // cancel downTimer and reset to 0? and reset flag
        // display directly to 00:00
        time = 0L
        isPaused = false
        display()
    }

    fun pause(){
        // "pause" downtimer and print current
        isPaused = true
        // display time
        display()
    }

    fun display(){
        // send current timer to history fragment
        // lisetener to connect to the MainActivity
        //listener.display(parseTimer())

        handler.dispatchMessage(Message().apply {
            obj = time
            what = 100
        })
    }

    private fun parseTimer(): String{
        if (time == 0L) return "00:00"

        return time.toString()
        // 200 00 -> 2 mins -> 02:00
        // 15034
        // mod by 60 if > 0 only seconds...
        // if timer < 1000 * 60 only seconds
        // 59000 -> 00:59 trim last 3 0
    }
}

// Start, Stop, Pause, Display

/*
Once you start the timer it should countdown from 02:00 to 00:00.
3. Once the timer is finished or is manually stopped you need to save it in history for future
reference (even after killing and re-opening the app).
4. Pause action should temporarily pause the timer (and change the button text/action to resume).
5.
 */