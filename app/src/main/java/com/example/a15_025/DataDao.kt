package com.example.a15_025

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDao {
    @Insert
    suspend fun insert(data: DataModel)

    @Query("SELECT * FROM data_table")
    suspend fun getAllData(): List<DataModel>

    @Query("DELETE FROM data_table WHERE id = :id")
    suspend fun deleteById(id: Int)

}
