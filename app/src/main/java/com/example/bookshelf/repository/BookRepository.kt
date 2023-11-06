package com.example.bookshelf.repository

import com.example.bookshelf.data.dao.AnnotationDao
import com.example.bookshelf.data.dao.BookDao
import com.example.bookshelf.data.entities.AnnotationEntity
import com.example.bookshelf.data.entities.BookEntity
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookDao: BookDao, private val annotationDao: AnnotationDao
) {
    suspend fun updateFavoriteStatus(bookId: String, isFavorite: Boolean) {
        bookDao.updateFavoriteStatus(bookId, isFavorite)
    }

    suspend fun getAnnotationsForBook(bookId: String): List<AnnotationEntity> {
        return annotationDao.getAnnotationsForBook(bookId)
    }

    suspend fun addAnnotation(annotation: AnnotationEntity) {
        annotationDao.insert(annotation)
    }
}
