package com.kunal.tnt.favourites.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kunal.tnt.favourites.models.Favourites

@Dao
interface FavouritesDao {

    @Query("SELECT * from favourites ORDER BY title ASC")
    fun getAlphabetizedWords(): LiveData<List<Favourites>>

    @Query("SELECT * from favourites")
    fun getAllFavourites(): LiveData<List<Favourites>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favourite: Favourites)

    @Query("DELETE FROM favourites")
    suspend fun deleteAll()

    @Query("DELETE FROM favourites WHERE id = :userId")
    fun deleteByUserId(userId: String)
}