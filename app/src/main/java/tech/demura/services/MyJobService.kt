package tech.demura.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {

    private val scope = CoroutineScope(Dispatchers.Main)

    companion object {
        const val ID = 111
        private const val PAGE = "page"

        fun newIntent(page: Int): Intent {
            return Intent().apply {
                putExtra(PAGE, page)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        log("onStartJob")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scope.launch {
                var workItem = p0?.dequeueWork()
                while (workItem != null) {
                    val page = workItem.intent.getIntExtra(PAGE, 0)

                    for (i in 1 until 5) {
                        delay(1000L)
                        log("Timer: $i in page: $page")
                    }
                    p0?.completeWork(workItem)
                    workItem = p0?.dequeueWork()
                }
                jobFinished(p0, false)
            }
        }
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "Job service: $message")
    }

}