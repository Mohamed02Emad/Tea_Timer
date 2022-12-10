package com.example.android.teatimer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.android.teatimer.databinding.ActivityAfterDoneBinding
import com.example.android.teatimer.service.MyService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AfterDoneActivity : AppCompatActivity() {
    lateinit var binding: ActivityAfterDoneBinding

    var myMinutes = 0
    var mySeconds = 0
    var isTeaReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAfterDoneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getData()
        setScreenData()
        setOnClicks()
    }

    private fun setScreenData() {
        if (isTeaReady) {
            binding.textView.text = "Your Tea is\nReady"
            val intent = Intent(this , MyService::class.java)
            stopService(intent)
        } else {
            lifecycleScope.launch {
                StartTimer(myMinutes, mySeconds)

            }
        }

    }

    private suspend fun StartTimer(minutes: Int, seconds: Int) {
        var thisMinutes = Constants.minutes
        var thisSeconds = Constants.seconds

        // timer
        while (thisMinutes >= 0) {
            while (thisSeconds > 0) {

                delay(1000)
                thisSeconds--
             binding.textView.text=thisMinutes.toString() + " M , " + thisSeconds + " S"
            }
            thisMinutes--
        }

        binding.textView.text = "Your Tea is\nReady"

        val intent = Intent(this , MyService::class.java)
        stopService(intent)
    }

    private fun getData() {
        myMinutes = intent.getIntExtra(Constants.MINUTES, 0)
        mySeconds = intent.getIntExtra(Constants.SECONDS, 0)
        isTeaReady = intent.getBooleanExtra(Constants.IS_TEAE_RADY, false)
    }

    private fun setOnClicks() {
        binding.backButton.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

}