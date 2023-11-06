package com.example.bookshelf.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.myapplication.R
import com.squareup.picasso.Picasso

@BindingAdapter("loadImageUrl")
fun ImageView.loadImageUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        Picasso.get().load(url).error(R.drawable.ic_library_books_52).into(this)
    } else {
        this.setImageResource(R.drawable.ic_library_books_52)
    }
}