package com.example.bookshelf.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookshelf.data.entities.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY publishedChapterDate LIMIT :pageSize OFFSET (:pageNumber - 1) * :pageSize")
    suspend fun getBooksFromPage(pageNumber: Int, pageSize: Int): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(books: List<BookEntity>)

    @Query("UPDATE books SET isFavorite = :isFavorite WHERE id = :bookId")
    suspend fun updateFavoriteStatus(bookId: String, isFavorite: Boolean)

    @Query("SELECT * FROM books ORDER BY publishedChapterDate DESC")
    fun getAllBooks(): PagingSource<Int, BookEntity>

    @Query("SELECT DISTINCT strftime('%Y', datetime(publishedChapterDate / 1000, 'unixepoch')) AS year FROM books ORDER BY year DESC")
    fun getDistinctYears(): Flow<List<Int>>
}
