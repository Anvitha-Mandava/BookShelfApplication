package com.example.bookshelf.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.data.entities.BookEntity
import com.example.bookshelf.ui.adapters.BooksAdapter
import com.example.bookshelf.ui.common.UiState
import com.example.bookshelf.ui.common.getYearFromTimestamp
import com.example.bookshelf.ui.viewModels.BooksViewModel
import com.example.myapplication.databinding.ActivityBookListBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookListBinding
    private var isUserInteraction = true

    @Inject
    lateinit var booksViewModel: BooksViewModel

    private val booksAdapter by lazy {
        BooksAdapter(booksViewModel, ::selectCorrespondingTab, ::moveToBookDetail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        setupRecyclerView()
        setupTabLayoutWithViewModel()
    }

    private fun setupRecyclerView() {
        binding.booksRecyclerView.adapter = booksAdapter
        val layoutManager = binding.booksRecyclerView.layoutManager as LinearLayoutManager
        binding.booksRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
                    val book = booksAdapter.peek(firstVisibleItemPosition)
                    book?.let {
                        val year = book.publishedChapterDate.getYearFromTimestamp()
                        selectCorrespondingTab(year)
                    }
                }
            }
        })
    }

    private fun setupTabLayoutWithViewModel() {
        booksViewModel.years.observe(this) { years ->
            binding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            binding.tabLayout.removeAllTabs()
            years.forEach { year ->
                binding.tabLayout.addTab(binding.tabLayout.newTab().setText(year.toString()))
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (isUserInteraction) {
                    tab?.text?.toString()?.toIntOrNull()?.let { year ->
                        booksViewModel.setYearFilter(year)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun selectCorrespondingTab(year: Int) {
        val tabCount = binding.tabLayout.tabCount
        isUserInteraction = false
        for (i in 0 until tabCount) {
            val tab = binding.tabLayout.getTabAt(i)
            if (tab?.text?.toString()?.toInt() == year) {
                tab.select()
                break
            }
        }
        isUserInteraction = true
    }

    private fun observeViewModel() {
        booksViewModel.booksFlow.asLiveData().observe(this) { pagingData ->
            booksAdapter.submitData(lifecycle, pagingData)
        }
        booksViewModel.filteredBooksFlow.asLiveData().observe(this) { pagingData ->
            booksAdapter.submitData(lifecycle, pagingData)
        }

        booksViewModel.booksUiState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> hideLoading()
                is UiState.Error -> {
                    hideLoading()
                    showError(state.exception.localizedMessage ?: "An unknown error occurred")
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.booksRecyclerView.visibility = View.INVISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.booksRecyclerView.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun performLogOut(v: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun moveToBookDetail(book: BookEntity) {
        val intent = Intent(this, BookDetailActivity::class.java).apply {
            putExtra("BOOK_DETAIL", book)
        }
        startActivity(intent)
    }
}
