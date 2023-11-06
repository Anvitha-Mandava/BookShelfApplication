package com.example.bookshelf.di

import android.content.Context
import com.example.bookshelf.MyApplication
import com.example.bookshelf.data.dao.BookDao
import com.example.bookshelf.data.dao.UserDao
import com.example.bookshelf.network.BooksApiService
import com.example.bookshelf.network.ConnectivityUtil
import com.example.bookshelf.network.CountryService
import com.example.bookshelf.network.IpApiService
import com.example.bookshelf.repository.BooksProvider
import com.example.bookshelf.repository.CountryProvider
import com.example.bookshelf.repository.IpApiProvider
import com.example.bookshelf.ui.viewModels.BooksViewModel
import com.example.bookshelf.ui.viewModels.UserViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideUserViewModel(
        countryProvider: CountryProvider, userDao: UserDao, ipApiProvider: IpApiProvider,
    ): UserViewModel {
        return UserViewModel(countryProvider, userDao, ipApiProvider)
    }

    @Provides
    fun provideBooksViewModel(
        booksProvider: BooksProvider
    ): BooksViewModel {
        return BooksViewModel(booksProvider)
    }

    @Provides
    @Singleton
    fun providesCountryProvider(
        @CountryApiRetrofit countryService: CountryService, connectivityUtil: ConnectivityUtil
    ): CountryProvider {
        return CountryProvider(countryService, connectivityUtil)
    }

    @Provides
    @Singleton
    fun providesBooksProvider(
        @BooksApiRetrofit bookService: BooksApiService,
        bookDao: BookDao,
        connectivityUtil: ConnectivityUtil
    ): BooksProvider {
        return BooksProvider(bookService, bookDao, connectivityUtil)
    }

    @Provides
    @Singleton
    fun providesIpApiProvider(
        @IpApiRetrofit ipApiService: IpApiService, connectivityUtil: ConnectivityUtil
    ): IpApiProvider {
        return IpApiProvider(ipApiService, connectivityUtil)
    }


    @Provides
    fun providesConnectivityUtil(
        context: Context
    ): ConnectivityUtil {
        return ConnectivityUtil(context)
    }

    @Provides
    fun provideContext(): Context = MyApplication.instance.applicationContext
}