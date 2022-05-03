package tech.demura.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MySimpleService: Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        val start = intent?.getIntExtra(EXTRA_START, 0) ?: 0
        coroutineScope.launch {
            for (i in start until start + 8){
                delay(1000L)
                log("Timer: $i")
            }
            stopSelf()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        coroutineScope.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String){
        Log.d("Simple service", message)
    }

    companion object{
        private const val EXTRA_START = "start"
        fun newIntent(context: Context, start: Int): Intent{
            return Intent(context, MySimpleService::class.java).apply {
                putExtra(EXTRA_START, start)
            }
        }
    }
}