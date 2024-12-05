package com.bangkit.catloris.ui.Alarm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AlarmViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences = AlarmPreferences(application)

    private val _breakfastTime = MutableLiveData<String>()
    val breakfastTime: LiveData<String> get() = _breakfastTime

    private val _lunchTime = MutableLiveData<String>()
    val lunchTime: LiveData<String> get() = _lunchTime

    private val _dinnerTime = MutableLiveData<String>()
    val dinnerTime: LiveData<String> get() = _dinnerTime

    init {
        // Load saved data when ViewModel is created
        _breakfastTime.value = preferences.getBreakfastTime() ?: ""
        _lunchTime.value = preferences.getLunchTime() ?: ""
        _dinnerTime.value = preferences.getDinnerTime() ?: ""
    }

    fun setBreakfastTime(time: String) {
        _breakfastTime.value = time
        preferences.saveBreakfastTime(time)
    }

    fun setLunchTime(time: String) {
        _lunchTime.value = time
        preferences.saveLunchTime(time)
    }

    fun setDinnerTime(time: String) {
        _dinnerTime.value = time
        preferences.saveDinnerTime(time)
    }
}