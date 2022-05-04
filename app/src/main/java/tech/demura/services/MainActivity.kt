package tech.demura.services

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobService
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import tech.demura.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
    }

    private fun setClickListeners(){
        binding.btnService.setOnClickListener {
            stopService(MyForegroundService.newIntent(this))
            startService(MySimpleService.newIntent(this, 25))
        }

        binding.btnForegroundService.setOnClickListener {
            ContextCompat.startForegroundService(
                this,
                MyForegroundService.newIntent(this)
            )
        }

        binding.btnIntentService.setOnClickListener {
            startService(MyIntentService.newIntent(this))
        }

        binding.btnJobScheduler.setOnClickListener {
            val componentName = ComponentName(this, MyJobService::class.java)

            val jobInfo = JobInfo.Builder(MyJobService.ID, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .build()

            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intent = MyJobService.newIntent(page++)
                jobScheduler.enqueue(jobInfo, JobWorkItem(intent))
            }
        }

        binding.btnJobIntentService.setOnClickListener {
            MyJobIntentService.enqueu(this, page++)
        }
    }

}