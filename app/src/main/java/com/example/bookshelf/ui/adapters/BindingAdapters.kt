package com.example.bookshelf.ui.adapters

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.bookshelf.ui.common.getString
import com.example.bookshelf.ui.common.getYearFromTimestamp
import com.example.myapplication.R
import com.squareup.picasso.Picasso

@BindingAdapter("loadImageUrl")
fun ImageView.loadImageUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        Picasso.get().load(url).placeholder(R.drawable.ic_library_books_52)
            .error(R.drawable.ic_library_books_52).into(this)
    } else {
        this.setImageResource(R.drawable.ic_library_books_52)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setYear")
fun TextView.setPublishedYear(publishedChapterDate: Long?) {
    this.text =
        "${getString(R.string.published_in)}  ${publishedChapterDate?.getYearFromTimestamp()}"
}