package com.example.bookshelf.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookshelf.data.entities.AnnotationEntity

@Dao
interface AnnotationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(annotation: AnnotationEntity)

    @Query("SELECT * FROM annotations WHERE bookId = :bookId")
    suspend fun getAnnotationsForBook(bookId: String): List<AnnotationEntity>
}
