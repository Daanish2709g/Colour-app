package com.example.colorapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ColorDao {
    @Query("SELECT * FROM colors")
    fun getAllColors(): LiveData<List<ColorEntity>>

    @Insert
    suspend fun insertColor(color: ColorEntity)

    @Query("DELETE FROM colors")
    suspend fun deleteAllColors()
}
