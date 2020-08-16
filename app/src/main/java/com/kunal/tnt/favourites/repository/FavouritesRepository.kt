package com.kunal.tnt.favourites.repository

import androidx.lifecycle.LiveData
import com.kunal.tnt.favourites.db.FavouritesDao
import com.kunal.tnt.favourites.models.Favourites

class FavouritesRepository(private val wordDao: FavouritesDao) {

    val allWords: LiveData<List<Favourites>> = wordDao.getAllFavourites()

    suspend fun unBook(userId: String) {
        wordDao.deleteByUserId(userId)
    }
}