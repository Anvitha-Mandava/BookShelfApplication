package com.example.bookshelf.ui.viewModels

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.data.entities.AnnotationEntity
import com.example.bookshelf.data.entities.BookEntity
import com.example.bookshelf.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    var book = ObservableField<BookEntity>()
    val annotations = MutableLiveData<List<AnnotationEntity>>()

    fun loadBookDetails() {
        viewModelScope.launch {
            annotations.value = bookRepository.getAnnotationsForBook(book.get()?.id.orEmpty())
        }
    }

    fun addAnnotation(content: String) {
        viewModelScope.launch {
            val newAnnotation =
                AnnotationEntity(bookId = book.get()?.id.orEmpty(), content = content)
            bookRepository.addAnnotation(newAnnotation)
            val updatedAnnotations = annotations.value.orEmpty() + newAnnotation
            annotations.postValue(updatedAnnotations)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            bookRepository.updateFavoriteStatus(
                book.get()?.id.orEmpty(), !(book.get()?.isFavorite ?: false)
            )
        }
    }
}
