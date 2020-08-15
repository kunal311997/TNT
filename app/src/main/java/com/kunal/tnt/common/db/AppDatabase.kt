package com.kunal.tnt.common.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kunal.tnt.favourites.models.Favourites
import com.kunal.tnt.favourites.db.FavouritesDao

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Favourites::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favouritesDao(): FavouritesDao


    /*private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var wordDao = database.wordDao()

                    // Delete all content here.
                    wordDao.deleteAll()

                    // Add sample words.
                    var word = Favourites("Hello", "1", "", "", "", "", "", "")
                    wordDao.insert(word)
                    word = Favourites("Hello0", "2", "", "", "", "", "", "")
                    wordDao.insert(word)

                    *//* // TODO: Add your own words!
                     word = Favourites("TODO!")
                     wordDao.insert(word)*//*
                }
            }
        }
    }*/

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
            // ,scope: CoroutineScope
        ): AppDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                )
                    //.addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}