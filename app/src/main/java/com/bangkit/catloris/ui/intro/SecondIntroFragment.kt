package com.bangkit.catloris.ui.intro

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.catloris.databinding.FragmentSecondIntroBinding

class SecondIntroFragment : Fragment() {

    private var _binding: FragmentSecondIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondIntroBinding.inflate(inflater, container, false)

        binding.btnNext2.setOnClickListener {
            (activity as WelcomeActivity).navigateToFragment(ThirdIntroFragment())
        }

        playAnimation()

        return binding.root
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.introSecondPict, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 7000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding
    }


}