package com.example.android.teatimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.teatimer.databinding.ActivityTimerIsFinishedBinding

class TimerIsFinished : AppCompatActivity() {
    lateinit var binding : ActivityTimerIsFinishedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerIsFinishedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setOnClicks()

    }

    private fun setOnClicks() {
        binding.backButton.setOnClickListener{
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}