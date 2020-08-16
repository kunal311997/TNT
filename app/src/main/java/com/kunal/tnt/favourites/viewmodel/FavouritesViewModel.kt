package com.kunal.tnt.favourites.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kunal.tnt.common.db.AppDatabase
import com.kunal.tnt.favourites.models.Favourites
import com.kunal.tnt.favourites.repository.FavouritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    private val repository: FavouritesRepository

    val allFavourites: LiveData<List<Favourites>>

    init {
        val favouritesDao = AppDatabase.getDatabase(application).favouritesDao()
        repository = FavouritesRepository(
            favouritesDao
        )
        allFavourites = repository.allWords
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Favourites) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }


    fun unBook(userId: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.unBook(userId)
    }
}
