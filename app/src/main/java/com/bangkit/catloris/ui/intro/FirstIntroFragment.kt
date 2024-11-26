package com.bangkit.catloris.ui.intro

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.catloris.databinding.FragmentFirstIntroBinding


class FirstIntroFragment : Fragment() {

    private var _binding: FragmentFirstIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFirstIntroBinding.inflate(inflater, container, false)

        binding.btnNext1.setOnClickListener {
            (activity as WelcomeActivity).navigateToFragment(SecondIntroFragment())
        }

        playAnimation()

        return binding.root
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.introFirstSecond, View.TRANSLATION_X, -30f, 30f).apply {
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