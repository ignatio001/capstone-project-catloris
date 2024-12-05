package com.bangkit.catloris.ui.Alarm

import android.content.Context
import android.content.SharedPreferences

class AlarmPreferences(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("alarm_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_BREAKFAST_TIME = "breakfast_time"
        private const val KEY_LUNCH_TIME = "lunch_time"
        private const val KEY_DINNER_TIME = "dinner_time"
    }

    fun saveBreakfastTime(time: String) {
        preferences.edit().putString(KEY_BREAKFAST_TIME, time).apply()
    }

    fun saveLunchTime(time: String) {
        preferences.edit().putString(KEY_LUNCH_TIME, time).apply()
    }

    fun saveDinnerTime(time: String) {
        preferences.edit().putString(KEY_DINNER_TIME, time).apply()
    }

    fun getBreakfastTime(): String? = preferences.getString(KEY_BREAKFAST_TIME, "")
    fun getLunchTime(): String? = preferences.getString(KEY_LUNCH_TIME, "")
    fun getDinnerTime(): String? = preferences.getString(KEY_DINNER_TIME, "")
}