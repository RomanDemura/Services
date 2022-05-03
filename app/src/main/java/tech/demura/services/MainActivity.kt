package tech.demura.services

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
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
    }
}