package com.example.colorapp.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.firebase.database.FirebaseDatabase
import com.example.colorapp.data.ColorDao
import com.example.colorapp.data.ColorEntity

class ColorRepository(private val colorDao: ColorDao) {
    val allColors: LiveData<List<ColorEntity>> = colorDao.getAllColors()

    suspend fun insertColor(color: ColorEntity) {
        withContext(Dispatchers.IO) {
            colorDao.insertColor(color)
        }
    }

    suspend fun syncColorsToCloud() {
        val db = FirebaseDatabase.getInstance().getReference("colors")
        val colors = allColors.value ?: emptyList()
        withContext(Dispatchers.IO) {
            for (color in colors) {
                db.child(color.id.toString()).setValue(color)
            }
        }
    }
}
