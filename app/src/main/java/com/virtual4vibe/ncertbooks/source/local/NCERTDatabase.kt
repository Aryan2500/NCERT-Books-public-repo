package com.virtual4vibe.ncertbooks.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.virtual4vibe.ncertbooks.models.BookMark
import com.virtual4vibe.ncertbooks.models.Pdf

@Database(entities = [Pdf::class , BookMark::class] , version = 1)
abstract  class NCERTDatabase : RoomDatabase(){
    abstract fun ncertDao() :NcertDao

}