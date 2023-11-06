package com.example.bookshelf.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.bookshelf.data.entities.BookEntity
import com.example.bookshelf.repository.BooksProvider
import com.example.bookshelf.ui.common.UiState
import com.example.bookshelf.ui.common.getYearFromTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(private val booksProvider: BooksProvider) : ViewModel() {
    val booksUiState = MutableLiveData<UiState>()
    val booksFlow: Flow<PagingData<BookEntity>> =
        booksProvider.getBooksStream().cachedIn(viewModelScope)
    private val currentYearFilter = MutableStateFlow<Int?>(null)
    val years = MutableLiveData<List<Int>>()

    val filteredBooksFlow: Flow<PagingData<BookEntity>> = currentYearFilter.flatMapLatest { year ->
        if (year == null) {
            booksFlow
        } else {
            booksFlow.map { pagingData ->
                pagingData.filter { book ->
                    book.publishedChapterDate.getYearFromTimestamp() == year
                }
            }
        }
    }.cachedIn(viewModelScope)

    fun setYearFilter(year: Int?) {
        currentYearFilter.value = year
    }

    init {
        refreshBooks()
        viewModelScope.launch {
            booksProvider.getDistinctYearsFlow().collect { yearsList ->
                years.postValue(yearsList)
            }
        }
    }

    private fun refreshBooks() {
        booksUiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                booksProvider.refreshBooks()
                booksUiState.value = UiState.Success
            } catch (e: Exception) {
                booksUiState.value = UiState.Error(e)
            }
        }
    }

    fun toggleFavorite(book: BookEntity) {
        viewModelScope.launch {
            booksProvider.toggleFavorite(book.id, !book.isFavorite)
        }
    }
}

