package com.virtual4vibe.ncertbooks.di

import android.app.Application
import androidx.room.Room
import com.virtual4vibe.ncertbooks.source.local.NCERTDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application ) =
        Room.databaseBuilder(app, NCERTDatabase::class.java, "ndertBooksAndSolution_db")
            .fallbackToDestructiveMigration().build()

    @Provides
    fun provideDao(db:NCERTDatabase) =  db.ncertDao()

    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}