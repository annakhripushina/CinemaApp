package com.example.cinema_app.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema
import com.example.cinema_app.data.entity.WatchCinema

@Dao
interface CinemaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCinema(cinema: Cinema?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavouriteCinema(favouriteCinema: FavouriteCinema?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLikedCinema(likedCinema: LikedCinema?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWatchCinema(watchCinema: WatchCinema?)

    @Query("DELETE FROM liked_table WHERE original_id == :cinemaOriginalId")
    fun deleteLikedCinema(cinemaOriginalId: Int)

    @Query("DELETE FROM favourite_table WHERE original_id == :cinemaOriginalId")
    fun deleteFavouriteCinema(cinemaOriginalId: Int)

    @Query("DELETE FROM cinema_table")
    fun deleteAll()

    @Query("SELECT * FROM cinema_table")
    fun getAll(): LiveData<List<Cinema>>

    @Query("SELECT c.original_id, c.title, c.description, c.image, c.titleColor, c.id FROM cinema_table c, favourite_table f WHERE c.original_id = f.original_id")
    fun getFavouriteCinema(): LiveData<List<Cinema>>

    @Query("SELECT * FROM liked_table")
    fun getLikedCinema(): LiveData<List<LikedCinema>>

    @Query("SELECT c.original_id, c.title, c.description, c.image, c.titleColor, c.id FROM cinema_table c, watch_table w WHERE c.original_id = w.original_id")
    fun getAllWatchCinema(): LiveData<List<Cinema>>

 /*   @Query("SELECT c.original_id, c.title FROM cinema_table c, watch_table w WHERE c.original_id = w.original_id")
    fun getWatchCinema(original_id: Int): LiveData<List<Cinema>>*/

    @Query("UPDATE cinema_table SET titleColor = :titleColor WHERE id = :id")
    fun updateTitleColor(titleColor: Int, id: Int)

    @Query("UPDATE watch_table SET date_viewed = :dateViewed WHERE original_id = :id")
    fun updateTitleColor(dateViewed: String, id: Int)
}