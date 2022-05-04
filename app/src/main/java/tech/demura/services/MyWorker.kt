package tech.demura.services

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWorker(context: Context, private val workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        log("doWork")
        val page = workerParams.inputData.getInt(PAGE, 0)
        for (i in 0 until 8) {
            Thread.sleep(1000L)
            log("Timer: $i page: $page")
        }
        return Result.success()
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "WorkManager service: $message")
    }

    companion object {
        private const val PAGE = "page"
        const val WORK_NAME = "my_worker"

        fun makeRequest(page: Int): OneTimeWorkRequest{
            return OneTimeWorkRequestBuilder<MyWorker>().apply {
                setInputData(workDataOf(PAGE to page))
                setConstraints(makeConstraints())
            }.build()
        }

        private fun makeConstraints(): Constraints{
            return Constraints.Builder()
                .setRequiresCharging(true)
                .build()
        }
    }
}