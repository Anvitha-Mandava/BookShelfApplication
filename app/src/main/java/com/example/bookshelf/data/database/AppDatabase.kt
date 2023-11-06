package com.example.bookshelf.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookshelf.data.dao.BookDao
import com.example.bookshelf.data.dao.UserDao
import com.example.bookshelf.data.entities.BookEntity
import com.example.bookshelf.data.entities.User

@Database(entities = [User::class, BookEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao
}
