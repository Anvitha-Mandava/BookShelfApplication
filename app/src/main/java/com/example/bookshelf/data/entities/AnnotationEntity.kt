package com.example.bookshelf.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "annotations")
data class AnnotationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "bookId") val bookId: String,
    @ColumnInfo(name = "content") val content: String
)
