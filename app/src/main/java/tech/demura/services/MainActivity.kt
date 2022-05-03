package tech.demura.services

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

        binding.btnService.setOnClickListener {
            stopService(ForegroundService.newIntent(this))
            startService(SimpleService.newIntent(this, 25))
        }

        binding.btnForegroundService.setOnClickListener {
            ContextCompat.startForegroundService(
                this,
                ForegroundService.newIntent(this)
            )
        }

    }

}