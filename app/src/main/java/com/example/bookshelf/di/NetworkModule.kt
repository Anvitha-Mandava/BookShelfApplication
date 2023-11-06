package com.example.bookshelf.di

import com.example.bookshelf.network.BooksApiService
import com.example.bookshelf.network.IpApiService
import com.example.bookshelf.network.CountryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BooksApiRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CountryApiRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IpApiRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @CountryApiRetrofit
    @Provides
    @Singleton
    fun provideCountryApiRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.first.org/data/")
            .addConverterFactory(gsonConverterFactory).build()
    }

    @IpApiRetrofit
    @Provides
    @Singleton
    fun provideIpApiRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder().baseUrl("http://ip-api.com/")
            .addConverterFactory(gsonConverterFactory).build()
    }

    @BooksApiRetrofit
    @Provides
    @Singleton
    fun provideBooksApiRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder().baseUrl("https://www.jsonkeeper.com/")
            .addConverterFactory(gsonConverterFactory).build()
    }

    @CountryApiRetrofit
    @Provides
    @Singleton
    fun provideCountryApiService(@CountryApiRetrofit retrofit: Retrofit): CountryService {
        return retrofit.create(CountryService::class.java)
    }

    @IpApiRetrofit
    @Provides
    @Singleton
    fun provideIpApiService(@IpApiRetrofit retrofit: Retrofit): IpApiService {
        return retrofit.create(IpApiService::class.java)
    }

    @BooksApiRetrofit
    @Provides
    @Singleton
    fun provideBooksApiService(@BooksApiRetrofit retrofit: Retrofit): BooksApiService {
        return retrofit.create(BooksApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}