package tech.demura.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyJobService: JobService() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        log("onStartJob")
        scope.launch {
            for (i in 1 until 8){
                log("Timer: $i")
            }
            jobFinished(p0,true)
        }
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    private fun log(message: String){
        Log.d("Job service", message)
    }
}