package com.virtual4vibe.ncertbooks.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.virtual4vibe.ncertbooks.models.BookMark
import com.virtual4vibe.ncertbooks.models.Pdf
import kotlinx.coroutines.flow.Flow


@Dao
interface  NcertDao {

    //Downloads
    @Insert
    suspend fun insertDownloadedPdf(pdf: Pdf)

    @Delete
    suspend fun deletePdf(pdf: Pdf)

    @Query("select * from pdf_table")
    fun getDownloadedPdf(): Flow<List<Pdf>>

    //Bookmarks
    @Insert
    suspend fun insertBookmark(book:BookMark)

    @Delete
    suspend fun deleteBookmark(bookmark: BookMark)

    @Query("select * from bookmark_table")
    fun getbookMark() : Flow<List<BookMark>>

    @Query("delete from pdf_table where id = :id")
    fun deletePdfWithId(id :Int)
}