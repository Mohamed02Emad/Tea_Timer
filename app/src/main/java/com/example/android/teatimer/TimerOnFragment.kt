package com.example.android.teatimer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.teatimer.Constants.minutes
import com.example.android.teatimer.databinding.FragmentTimerOnBinding

class TimerOnFragment : Fragment() {

    lateinit var binding: FragmentTimerOnBinding
    var minutes =0
    var seconds = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTimerOnBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        minutes=Constants.minutes
        seconds=Constants.seconds

        setViews()
        setOnClickListeners()
    }

    private fun setViews() {
        binding.timeLeft.text=minutes.toString()+" M\n"+seconds+" S"
    }

    private fun setOnClickListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()

//            Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment)
//                .navigate(R.id.action_timerOnFragment_to_setTimerFragment)

        }
    }


}