package tech.demura.services

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService: JobIntentService() {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onHandleWork(intent: Intent) {
        log("onHandledWork")
        val page = intent.getIntExtra(PAGE, 0)
        for (i in 0 until 8) {
            Thread.sleep(1000L)
            log("Timer: $i page: $page")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "JobIntent service: $message")
    }

    companion object {
        private const val PAGE = "page"
        private const val ID = 111

        fun enqueu(context: Context, page: Int){
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                ID,
                newIntent(context, page)

            )
        }

        private fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}