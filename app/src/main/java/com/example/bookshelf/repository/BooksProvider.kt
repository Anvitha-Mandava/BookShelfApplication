package com.example.bookshelf.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.bookshelf.data.dao.BookDao
import com.example.bookshelf.data.entities.BookEntity
import com.example.bookshelf.network.BooksApiService
import com.example.bookshelf.network.ConnectivityUtil
import com.example.bookshelf.util.ApiException
import com.example.bookshelf.util.NoConnectivityException
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

class BooksProvider @Inject constructor(
    private val booksApiService: BooksApiService,
    private val bookDao: BookDao,
    private val connectivityUtil: ConnectivityUtil
) {

    suspend fun refreshBooks() {
        if (connectivityUtil.isNetworkAvailable()) {
            try {
                val response = booksApiService.getBooks()
                if (response.isSuccessful) {
                    response.body()?.let { bookList ->
                        bookDao.insertAll(bookList)
                    }
                } else {
                    throw ApiException("Error fetching books: ${response.errorBody()?.string()}")
                }
            } catch (e: IOException) {
                throw NoConnectivityException()
            } catch (e: Exception) {
                throw ApiException("Error occurred: ${e.localizedMessage}")
            }
        }
    }

    suspend fun toggleFavorite(bookId: String, isFavorite: Boolean) {
        bookDao.updateFavoriteStatus(bookId, isFavorite)
    }

    fun getBooksStream(): Flow<PagingData<BookEntity>> {
        return Pager(config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = { bookDao.getAllBooks() }).flow
    }

    fun getDistinctYearsFlow(): Flow<List<Int>> = bookDao.getDistinctYears()
}




