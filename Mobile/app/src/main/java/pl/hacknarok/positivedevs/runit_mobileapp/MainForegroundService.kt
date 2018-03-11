package pl.hacknarok.positivedevs.runit_mobileapp

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log

/**
 * Created by Tomek on 2017-07-17.
 */

class MainForegroundService : Service() {
    private val handler = Handler()
    private var mContext: Context? = null

    private val runnable = object : Runnable {
        override fun run() {
            mContext!!.startService(Intent(mContext, UpdateService::class.java))
            handler.postDelayed(this, 60000)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCreator().create(this)

        startForeground(17, notification)

        mContext = this
        Log.d("Lemur", "Starting service")
        handler.post(runnable)

        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}