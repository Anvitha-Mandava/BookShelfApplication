package com.example.bookshelf.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "score") val score: Double,
    @ColumnInfo(name = "popularity") val popularity: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "publishedChapterDate") val publishedChapterDate: Long,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false
): Serializable
