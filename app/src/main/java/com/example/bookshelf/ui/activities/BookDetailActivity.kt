package com.example.bookshelf.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.bookshelf.ui.adapters.AnnotationsAdapter
import com.example.bookshelf.ui.viewModels.BookDetailViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityBookDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailBinding

    @Inject
    lateinit var viewModel: BookDetailViewModel

    private var annotationsAdapter: AnnotationsAdapter = AnnotationsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.annotationsRecyclerView.adapter = annotationsAdapter

        messageFromIntent()
        setupObservers()
        viewModel.loadBookDetails()
        setupAddAnnotationButton()
    }

    private fun messageFromIntent() {
        viewModel.book.set(intent.getParcelableExtra("BOOK_DETAIL"))
        binding.book = viewModel.book
    }

    private fun setupObservers() {
        binding.bookDetailInclude.favoriteIcon.setOnClickListener {
            viewModel.toggleFavorite()
        }

        viewModel.annotations.observe(this) { annotations ->
            annotationsAdapter.submitList(annotations)
        }
    }

    private fun setupAddAnnotationButton() {
        binding.addAnnotationButton.setOnClickListener {
            showAddAnnotationDialog()
        }
    }

    private fun showAddAnnotationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dailog_add_annotation, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextAnnotation)

        AlertDialog.Builder(this).apply {
            setView(dialogView)
            setTitle(R.string.add_annotation)
            setPositiveButton(R.string.save) { dialog, _ ->
                viewModel.addAnnotation(editText.text.toString())
            }
            setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            show()
        }
    }

    fun performLogOut(v: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

