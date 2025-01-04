package com.bangkit.catloris.ui.challenge

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.FragmentChallengeBinding


class ChallengeFragment : Fragment() {
    private var _binding: FragmentChallengeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChallengeBinding.inflate(inflater, container, false)

        binding.start.setOnClickListener{
            val chIntent = Intent(requireContext(), ChallengesDetailActivity::class.java)
            startActivity(chIntent)
        }
        return binding.root
    }

}