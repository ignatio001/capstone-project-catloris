package com.bangkit.catloris.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MetricsViewModelFactory(private val repository: MetricsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MetricsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MetricsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}