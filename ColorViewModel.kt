package com.example.colorapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.colorapp.data.ColorEntity
import com.example.colorapp.data.ColorDatabase
import com.example.colorapp.repository.ColorRepository

class ColorViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ColorRepository = ColorRepository(
        ColorDatabase.getDatabase(application).colorDao()
    )
    val allColors: LiveData<List<ColorEntity>> = repository.allColors
    val pendingSyncCount: LiveData<Int> = Transformations.map(allColors) { it.size }

    fun addColor() {
        viewModelScope.launch {
            val color = ColorEntity(
                color = generateRandomColor(),
                time = System.currentTimeMillis()
            )
            repository.insertColor(color)
        }
    }

    fun syncColors() {
        viewModelScope.launch {
            repository.syncColorsToCloud()
        }
    }

    private fun generateRandomColor(): String {
        val colors = listOf("#FFAABB", "#D7415F", "#A1C4FD") // Example colors
        return colors.random()
    }
}
