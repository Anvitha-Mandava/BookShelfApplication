package com.example.bookshelf.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.data.entities.BookEntity
import com.example.bookshelf.ui.viewModels.BooksViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemBookBinding

class BooksAdapter(
    private val viewModel: BooksViewModel, private val yearChangeListener: (Int) -> Unit
) : PagingDataAdapter<BookEntity, BooksAdapter.BookViewHolder>(BOOK_COMPARATOR) {

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position) ?: return
        book.let {
            val currentYear = viewModel.getYearFromTimestamp(book.publishedChapterDate)
            if (position == 0 || viewModel.getYearFromTimestamp(
                    getItem(position - 1)?.publishedChapterDate ?: 0L
                ) != currentYear
            ) {
                yearChangeListener.invoke(currentYear)
            }
        }
        holder.bind(book)
    }

    inner class BookViewHolder(val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookEntity) {
            binding.apply {
                favoriteIcon.setOnClickListener {
                    viewModel.toggleFavorite(book)
                }
                layout.setOnClickListener {
                    //Move to detail page
                }
                favoriteIcon.setImageResource(if (book.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_gray)
                imageUrl = book.image
                bookScore.text = book.score.toString()
                bookTitle.text = book.title
                bookPublishDate.text = book.publishedChapterDate.toString()
                bookImage.drawable
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


