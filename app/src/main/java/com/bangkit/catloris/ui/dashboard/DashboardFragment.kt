package com.bangkit.catloris.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.catloris.databinding.FragmentDashboardBinding
import com.bangkit.catloris.ui.auth.LoginActivity
import com.bangkit.catloris.ui.sidefeatures.BodyMassActivity
import com.bangkit.catloris.ui.sidefeatures.FoodHistoryActivity
import com.bangkit.catloris.ui.sidefeatures.HealthInsightActivity
import com.bangkit.catloris.ui.sidefeatures.WorkoutActivity

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.dashboardLogout.setOnClickListener {
            val logoutIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(logoutIntent)
            requireActivity().finish()
        }
        binding.bmiButton.setOnClickListener {
            val bmiIntent = Intent(requireContext(), BodyMassActivity::class.java)
            startActivity(bmiIntent)
        }
        binding.hitButton.setOnClickListener {
            val hitIntent = Intent(requireContext(), HealthInsightActivity::class.java)
            startActivity(hitIntent)

        }
        binding.foodButton.setOnClickListener {
            val foodIntent = Intent(requireContext(), FoodHistoryActivity::class.java)
            startActivity(foodIntent)
        }
        binding.workoutButton.setOnClickListener {
            val workoutIntent = Intent(requireContext(), WorkoutActivity::class.java)
            startActivity(workoutIntent)
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}