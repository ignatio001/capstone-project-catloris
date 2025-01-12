package com.bangkit.catloris.ui.dashboard

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.FragmentDashboardBinding
import com.bangkit.catloris.helper.DashboardAdapter
import com.bangkit.catloris.helper.DashboardItem
import com.bangkit.catloris.ui.alarm.AlarmActivity
import com.bangkit.catloris.ui.auth.LoginActivity
import com.bangkit.catloris.ui.sidefeatures.BodyMassActivity
import com.bangkit.catloris.ui.sidefeatures.FoodHistoryActivity
import com.bangkit.catloris.ui.sidefeatures.WorkoutActivity

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val namePreferences = requireActivity().getSharedPreferences("name_user", MODE_PRIVATE)
        val nameUser = namePreferences.getString("name_user", null)

        val bmiPreferences = requireActivity().getSharedPreferences("bmi_user", MODE_PRIVATE)
        val bmiPref = bmiPreferences.getString("bmi_user", null)


        binding.dashboardUsername.text = nameUser
        binding.bodyCondition.text = bmiPref

        val bodyCondition = binding.bodyCondition.text.toString().trim()

        when(bodyCondition) {
            "Normal" -> {
                binding.catIllustration.setImageResource(R.drawable.kucing_normal)
                binding.bodyFatsCondition.text = "15%-31%"
            }
            "Underweight" -> {
                binding.catIllustration.setImageResource(R.drawable.kucing_kurus)
                binding.bodyFatsCondition.text = "6%-13%"
            }
            "Overweight" -> {
                binding.catIllustration.setImageResource(R.drawable.kucing_gendut)
                binding.bodyFatsCondition.text = "31%-41%"
            }
            "Obesity Type 1" -> {
                binding.catIllustration.setImageResource(R.drawable.kucing_gendut)
                binding.bodyFatsCondition.text = "41%-57% Please Control your Habit"
            }
            "Obesity Type 2" -> {
                binding.catIllustration.setImageResource(R.drawable.kucing_gendut)
                binding.bodyFatsCondition.text = "59%-67% Make sure you diet"
            }
            "Obesity Type 3" -> {
                binding.catIllustration.setImageResource(R.drawable.kucing_gendut)
                binding.bodyFatsCondition.text = "78%-81% Please make a check up"
            }
        }



        binding.dashboardLogout.setOnClickListener {
            val logoutIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(logoutIntent)
            requireActivity().finish()
        }
        binding.bmiButton.setOnClickListener {
            val bmiIntent = Intent(requireContext(), BodyMassActivity::class.java)
            startActivity(bmiIntent)
        }
        binding.alarmButton.setOnClickListener {
            val alarmIntent = Intent(requireContext(), AlarmActivity::class.java)
            startActivity(alarmIntent)
        }
        binding.foodButton.setOnClickListener {
            val foodIntent = Intent(requireContext(), FoodHistoryActivity::class.java)
            foodIntent.putExtra("user_con", bodyCondition)
            startActivity(foodIntent)
        }
        binding.workoutButton.setOnClickListener {
            val workoutIntent = Intent(requireContext(), WorkoutActivity::class.java)
            startActivity(workoutIntent)
        }

        setupDashRecycler()
        playAnimation()


        return binding.root
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.catIllustration, View.TRANSLATION_X, -20f, 20f).apply {
            duration = 7000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    private fun setupDashRecycler() {
        val items = listOf(
            DashboardItem(R.drawable.dashlide2, "https://www.google.com"),
            DashboardItem(R.drawable.dashlide1, "https://www.google.com"),
            DashboardItem(R.drawable.dashlide3, "https://www.google.com")
        )

        val adapter = DashboardAdapter(items)
        binding.seemoreRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.seemoreRecycler.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}