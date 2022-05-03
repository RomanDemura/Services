package tech.demura.services

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyIntentService : IntentService(SERVICE_NAME) {

    override fun onCreate() {
        super.onCreate()
        setIntentRedelivery(true)
        log("onCreate")
    }

    override fun onHandleIntent(p0: Intent?) {
        log("onHandledIntent")
        for (i in 0 until 8) {
            Thread.sleep(1000L)
            log("Timer: $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "Intent service: $message")
    }

    companion object {
        private const val SERVICE_NAME = "intent_service"

        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }
}
