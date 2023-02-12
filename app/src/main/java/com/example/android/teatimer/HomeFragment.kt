package com.example.android.teatimer

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.android.teatimer.databinding.FragmentHomeBinding
import com.example.android.teatimer.service.MyService
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var numbersToPick = Array<String?>(60) { null }
    private lateinit var binding: FragmentHomeBinding
    private var minutes = 0
    private var seconds = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

            var mediaPlayer = MediaPlayer.create(context, R.raw.ball)
            mediaPlayer.start() // no need to call prepare(); create() does that for you

            val intent = Intent(requireActivity(), MyService::class.java)
            intent.putExtra(Constants.MINUTES, minutes)
            intent.putExtra(Constants.SECONDS, seconds)
            requireActivity().startService(intent)
        }
    }

    private fun setViews() {

        val mediaPlayer2 = MediaPlayer.create(context, R.raw.touch)
        // minutes timer
        setNubmerPicker(binding.TimerPicker.minutes, numbersToPick)
        binding.TimerPicker.minutes.setOnValueChangedListener { numberPicker, oldValue, newValue ->
            lifecycleScope.launch {
                if (mediaPlayer2.isPlaying){
                    mediaPlayer2.seekTo(0)
                }
                mediaPlayer2.start()
            }
            minutes = newValue
        }

        // seconds timer
        setNubmerPicker(binding.TimerPicker.seconds, numbersToPick)
        binding.TimerPicker.seconds.setOnValueChangedListener { numberPicker, oldValue, newValue ->
            lifecycleScope.launch {
                if (mediaPlayer2.isPlaying){
                    mediaPlayer2.seekTo(0)
                }
                mediaPlayer2.start()
            }
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