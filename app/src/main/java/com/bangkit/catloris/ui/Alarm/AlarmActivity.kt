package com.bangkit.catloris.ui.Alarm

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]

        // Observasi LiveData dari ViewModel
        viewModel.breakfastTime.observe(this) { time ->
            binding?.tvOnceTime1?.text = if (time.isNotEmpty()) time else "Sarapan"
        }

        viewModel.lunchTime.observe(this) { time ->
            binding?.tvOnceTime2?.text = if (time.isNotEmpty()) time else "Makan Siang"
        }

        viewModel.dinnerTime.observe(this) { time ->
            binding?.tvOnceTime3?.text = if (time.isNotEmpty()) time else "Makan Malam"
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
        val breakfastTime = viewModel.breakfastTime.value ?: "Belum Diatur"
        val lunchTime = viewModel.lunchTime.value ?: "Belum Diatur"
        val dinnerTime = viewModel.dinnerTime.value ?: "Belum Diatur"

        binding?.tvOnceTime1?.text = breakfastTime
        binding?.tvOnceTime2?.text = lunchTime
        binding?.tvOnceTime3?.text = dinnerTime
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_once_time1 -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, "TIME_PICKER_SARAPAN")
            }
            R.id.btn_once_time2 -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, "TIME_PICKER_MAKAN_SIANG")
            }
            R.id.btn_once_time3 -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, "TIME_PICKER_MAKAN_MALAM")
            }
            R.id.btn_set_once_alarm -> {
                val breakfastTime = viewModel.breakfastTime.value ?: ""
                val lunchTime = viewModel.lunchTime.value ?: ""
                val dinnerTime = viewModel.dinnerTime.value ?: ""

                if (breakfastTime.isNotEmpty()) {
                    alarmReceiver.setOneTimeAlarm(this, "Sarapan", breakfastTime, "Waktunya Sarapan!")
                }
                if (lunchTime.isNotEmpty()) {
                    alarmReceiver.setOneTimeAlarm(this, "Makan Siang", lunchTime, "Waktunya Makan Siang!")
                }
                if (dinnerTime.isNotEmpty()) {
                    alarmReceiver.setOneTimeAlarm(this, "Makan Malam", dinnerTime, "Waktunya Makan Malam!")
                }
            }
            R.id.btn_set_repeating_alarm -> {
                val breakfastTime = viewModel.breakfastTime.value ?: ""
                val lunchTime = viewModel.lunchTime.value ?: ""
                val dinnerTime = viewModel.dinnerTime.value ?: ""

                if (breakfastTime.isNotEmpty()) {
                    alarmReceiver.setRepeatingAlarm(this, "Sarapan", breakfastTime, "Waktunya Sarapan Setiap Hari!")
                }
                if (lunchTime.isNotEmpty()) {
                    alarmReceiver.setRepeatingAlarm(this, "Makan Siang", lunchTime, "Waktunya Makan Siang Setiap Hari!")
                }
                if (dinnerTime.isNotEmpty()) {
                    alarmReceiver.setRepeatingAlarm(this, "Makan Malam", dinnerTime, "Waktunya Makan Malam Setiap Hari!")
                }
            }
            R.id.btn_cancel_repeating_alarm -> {
                alarmReceiver.cancelRepeatingAlarm(this, "Sarapan")
                alarmReceiver.cancelRepeatingAlarm(this, "Makan Siang")
                alarmReceiver.cancelRepeatingAlarm(this, "Makan Malam")
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
            "TIME_PICKER_SARAPAN" -> viewModel.setBreakfastTime(time)
            "TIME_PICKER_MAKAN_SIANG" -> viewModel.setLunchTime(time)
            "TIME_PICKER_MAKAN_MALAM" -> viewModel.setDinnerTime(time)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}