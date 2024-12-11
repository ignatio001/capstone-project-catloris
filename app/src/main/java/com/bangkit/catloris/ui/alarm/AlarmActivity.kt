package com.bangkit.catloris.ui.alarm

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityAlarmBinding
import com.bangkit.catloris.utils.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AlarmActivity : AppCompatActivity(), View.OnClickListener, TimePickerFragment.DialogTimeListener {

    private var binding: ActivityAlarmBinding? = null
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var viewModel: AlarmViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]

        // Observasi LiveData dari ViewModel
        viewModel.breakfastTime.observe(this) { time ->
            binding?.tvOnceTime1?.text = if (time.isNotEmpty()) time else "Breakfast Time"
        }

        viewModel.lunchTime.observe(this) { time ->
            binding?.tvOnceTime2?.text = if (time.isNotEmpty()) time else "Lunch Time"
        }

        viewModel.dinnerTime.observe(this) { time ->
            binding?.tvOnceTime3?.text = if (time.isNotEmpty()) time else "Dinner Time"
        }

        // Listener untuk waktu makan
        binding?.btnOnceTime1?.setOnClickListener(this)
        binding?.btnOnceTime2?.setOnClickListener(this)
        binding?.btnOnceTime3?.setOnClickListener(this)

        // Listener untuk alarm
        binding?.btnSetOnceAlarm?.setOnClickListener(this)
        binding?.btnSetRepeatingAlarm?.setOnClickListener(this)
        binding?.btnCancelRepeatingAlarm?.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()

        // Memuat data alarm yang disimpan
        loadAlarmData()
    }

    private fun loadAlarmData() {
        // LiveData sudah memuat data saat ViewModel diinisialisasi.
        val breakfastTime = viewModel.breakfastTime.value ?: "No Set Yet"
        val lunchTime = viewModel.lunchTime.value ?: "No Set Yet"
        val dinnerTime = viewModel.dinnerTime.value ?: "No Set Yet"

        binding?.tvOnceTime1?.text = breakfastTime
        binding?.tvOnceTime2?.text = lunchTime
        binding?.tvOnceTime3?.text = dinnerTime
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_once_time1 -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, "TIME_PICKER_BREAKFAST")
            }
            R.id.btn_once_time2 -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, "TIME_PICKER_LUNCH")
            }
            R.id.btn_once_time3 -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, "TIME_PICKER_DINNER")
            }
            R.id.btn_set_once_alarm -> {
                val breakfastTime = viewModel.breakfastTime.value ?: ""
                val lunchTime = viewModel.lunchTime.value ?: ""
                val dinnerTime = viewModel.dinnerTime.value ?: ""

                if (breakfastTime.isNotEmpty()) {
                    alarmReceiver.setOneTimeAlarm(this, "Breakfast Time", breakfastTime, "It's Time to Breakfast")
                }
                if (lunchTime.isNotEmpty()) {
                    alarmReceiver.setOneTimeAlarm(this, "Lunch Time", lunchTime, "It's Time to get Lunch!")
                }
                if (dinnerTime.isNotEmpty()) {
                    alarmReceiver.setOneTimeAlarm(this, "Dinner Time", dinnerTime, "It's Time to get your Dinner!")
                }
            }
            R.id.btn_set_repeating_alarm -> {
                val breakfastTime = viewModel.breakfastTime.value ?: ""
                val lunchTime = viewModel.lunchTime.value ?: ""
                val dinnerTime = viewModel.dinnerTime.value ?: ""

                if (breakfastTime.isNotEmpty()) {
                    alarmReceiver.setRepeatingAlarm(this, "Breakfast Time", breakfastTime, "It's Time to Have Breakfast Everyday!")
                }
                if (lunchTime.isNotEmpty()) {
                    alarmReceiver.setRepeatingAlarm(this, "Lunch Time", lunchTime, "It's Time to eat your Lunch Everyday!")
                }
                if (dinnerTime.isNotEmpty()) {
                    alarmReceiver.setRepeatingAlarm(this, "Dinner Time", dinnerTime, "It's Time to Hit Your Dinner!")
                }
            }
            R.id.btn_cancel_repeating_alarm -> {
                alarmReceiver.cancelRepeatingAlarm(this, "Breakfast Time")
                alarmReceiver.cancelRepeatingAlarm(this, "Lunch Time")
                alarmReceiver.cancelRepeatingAlarm(this, "Dinner Time")
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val time = timeFormat.format(calendar.time)

        when (tag) {
            "TIME_PICKER_BREAKFAST" -> viewModel.setBreakfastTime(time)
            "TIME_PICKER_LUNCH" -> viewModel.setLunchTime(time)
            "TIME_PICKER_DINNER" -> viewModel.setDinnerTime(time)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}