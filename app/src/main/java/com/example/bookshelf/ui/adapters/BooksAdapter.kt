package com.example.bookshelf.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.data.entities.BookEntity
import com.example.bookshelf.ui.common.getYearFromTimestamp
import com.example.bookshelf.ui.viewModels.BooksViewModel
import com.example.myapplication.databinding.ItemBookBinding

class BooksAdapter(
    private val viewModel: BooksViewModel,
    private val yearChangeListener: (Int) -> Unit,
    private val moveToDetail: (BookEntity) -> Unit
) : PagingDataAdapter<BookEntity, BooksAdapter.BookViewHolder>(BOOK_COMPARATOR) {

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position) ?: return
        book.let {
            val currentYear = book.publishedChapterDate.getYearFromTimestamp()
            if (position == 0 || hasYearChanged(currentYear, position)) {
                yearChangeListener.invoke(currentYear)
            }
        }
        holder.bind(book)
    }

    private fun hasYearChanged(currentYear: Int, position: Int) =
        (getItem(position - 1)?.publishedChapterDate ?: 0L).getYearFromTimestamp() != currentYear

    inner class BookViewHolder(val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookEntity) {
            binding.apply {
                this.book = ObservableField(book)
                favoriteIcon.setOnClickListener {
                    viewModel.toggleFavorite(book)
                }
                layout.setOnClickListener {
                    moveToDetail.invoke(book)
                }
            }
        }
    }

    companion object {
        private val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<BookEntity>() {
            override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }
}


