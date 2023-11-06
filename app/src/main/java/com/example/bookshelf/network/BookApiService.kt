package com.example.bookshelf.network

import com.example.bookshelf.data.entities.BookEntity
import retrofit2.Response
import retrofit2.http.GET

interface BooksApiService {
    @GET("b/CNGI")
    suspend fun getBooks(): Response<List<BookEntity>>
}