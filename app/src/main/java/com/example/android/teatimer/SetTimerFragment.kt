package com.example.android.teatimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.android.teatimer.databinding.FragmentSetTimerBinding
import kotlin.math.min


class SetTimerFragment : Fragment() {

    var numbersToPick = Array<String?>(60) { null }
    lateinit var binding: FragmentSetTimerBinding
    var minutes = 0
    var seconds = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetTimerBinding.inflate(layoutInflater)
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
            Constants.minutes = binding.TimerPicker.minutes.value
            Constants.seconds = binding.TimerPicker.seconds.value
            Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment)
                .navigate(R.id.action_setTimerFragment_to_timerOnFragment)
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