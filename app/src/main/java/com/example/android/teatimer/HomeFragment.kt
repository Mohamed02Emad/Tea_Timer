package com.example.android.teatimer

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.android.teatimer.databinding.FragmentHomeBinding
import com.example.android.teatimer.service.MyService


class HomeFragment : Fragment() {

    var numbersToPick = Array<String?>(60) { null }
    lateinit var binding: FragmentHomeBinding
    var minutes = 0
    var seconds = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNumbers()
        setViews()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.StartTimerButton.setOnClickListener {

       //     CreateNotification("Hello")

            val intent = Intent(requireActivity() , MyService::class.java)
            intent.putExtra(Constants.MINUTES , binding.TimerPicker.minutes.value)
            intent.putExtra(Constants.SECONDS, binding.TimerPicker.seconds.value)
            requireActivity().startService(intent)
        }
    }

    private fun setViews() {

        // minutes timer
        setNubmerPicker(binding.TimerPicker.minutes, numbersToPick)
        binding.TimerPicker.minutes.setOnValueChangedListener { numberPicker, oldValue, newValue ->
         minutes = newValue
        }

        // seconds timer
        setNubmerPicker(binding.TimerPicker.seconds, numbersToPick)
        binding.TimerPicker.seconds.setOnValueChangedListener { numberPicker, oldValue, newValue ->
          seconds = newValue
        }

    }

    private fun setNumbers() {
        var i = 0
        while (i < 60) {
            numbersToPick[i] = i.toString()
            i++
        }

    }

    private fun setNubmerPicker(nubmerPicker: NumberPicker, numbers: Array<String?>) {
        nubmerPicker.maxValue = numbers.size - 1
        nubmerPicker.minValue = 0
        nubmerPicker.wrapSelectorWheel = true
        nubmerPicker.displayedValues = numbers as Array<String>
    }


}