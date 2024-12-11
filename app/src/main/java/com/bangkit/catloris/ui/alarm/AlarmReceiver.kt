package com.bangkit.catloris.ui.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bangkit.catloris.R
import java.util.Calendar

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE) ?: return
        val message = intent.getStringExtra(EXTRA_MESSAGE) ?: return

        val notificationId = when (type) {
            "Sarapan" -> 1
            "Makan Siang" -> 2
            "Makan Malam" -> 3
            else -> 0
        }

        showNotification(context, "Waktu $type", message, notificationId)
    }

    private fun showNotification(context: Context, title: String, message: String, notificationId: Int) {
        val channelId = "AlarmManager_Channel_$notificationId"
        val channelName = "AlarmManager Channel"

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.baseline_access_time_24)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000)

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, builder.build())
    }

    @SuppressLint("MissingPermission")
    fun setOneTimeAlarm(context: Context, type: String, time: String, message: String) {
        val timeArray = time.split(":")
        if (timeArray.size != 2) return

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, timeArray[0].toInt())
            set(Calendar.MINUTE, timeArray[1].toInt())
            set(Calendar.SECOND, 0)
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
            putExtra(EXTRA_TYPE, type)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            type.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(context, "Alarm $type diatur untuk $time", Toast.LENGTH_SHORT).show()
    }

    fun setRepeatingAlarm(context: Context, type: String, time: String, message: String) {
        val timeArray = time.split(":")
        if (timeArray.size != 2) return

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, timeArray[0].toInt())
            set(Calendar.MINUTE, timeArray[1].toInt())
            set(Calendar.SECOND, 0)
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
            putExtra(EXTRA_TYPE, type)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            type.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, "Repeating Alarm $type diatur untuk setiap hari pada $time", Toast.LENGTH_SHORT).show()
    }

    fun cancelRepeatingAlarm(context: Context, type: String) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            type.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, "Repeating Alarm $type dibatalkan", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TYPE = "extra_type"
    }
}