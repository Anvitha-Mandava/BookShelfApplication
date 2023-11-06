import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bookshelf.data.dao.BookDao
import com.example.bookshelf.data.entities.BookEntity
import com.example.bookshelf.network.BooksApiService
import retrofit2.HttpException
import java.io.IOException

class BooksPagingSource(
    private val booksApiService: BooksApiService, private val bookDao: BookDao
) : PagingSource<Int, BookEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookEntity> {
        val pageNumber = params.key ?: 1
        return try {
            // If API supports pagination, pass pageNumber and loadSize, pass these as params
            val response = booksApiService.getBooks()
            if (response.isSuccessful) {
                response.body()?.let { bookList ->
                    bookDao.insertAll(bookList)
                }
                val databaseBooks = bookDao.getBooksFromPage(pageNumber, params.loadSize)
                LoadResult.Page(
                    data = databaseBooks,
                    prevKey = if (pageNumber == 1) null else pageNumber - 1,
                    nextKey = if (databaseBooks.size < params.loadSize) null else pageNumber + 1
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BookEntity>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1) ?: state.closestPageToPosition(
                position
            )?.nextKey?.minus(1)
        }
    }
}