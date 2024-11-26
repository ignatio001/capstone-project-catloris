package com.bangkit.catloris.ui.intro

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.catloris.databinding.FragmentThirdIntroBinding


class ThirdIntroFragment : Fragment() {

    private var _binding: FragmentThirdIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentThirdIntroBinding.inflate(inflater, container, false)
        binding.btnFinishIntro.setOnClickListener {
            (activity as WelcomeActivity).navigateToLogin()
        }

        playAnimation()
        return binding.root
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.introFirstThird, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 7000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}