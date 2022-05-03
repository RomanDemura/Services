package tech.demura.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyForegroundService: Service() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        log("onCreate")
        super.onCreate()
//        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        coroutineScope.launch {
            for(i in 0 until 8){
                delay(1000L)
                log("Timer: $i")
            }
            stopSelf()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
        coroutineScope.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String){
        Log.d("SERVICE_TAG", "Foreground service: $message")
    }

    companion object{
        private const val CHANNEL_ID = "foreground_service_id"
        private const val CHANNEL_NAME = "Foreground service"
        private const val NOTIFICATION_ID = 112

        fun newIntent(context: Context): Intent{
            return Intent(context, MyForegroundService::class.java)
        }
    }

    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification(): Notification{
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground service")
            .setContentText("Notification")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }


}