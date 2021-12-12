package com.example.cinema_app.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema

@Dao
interface CinemaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cinema: Cinema?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavourite(favouriteCinema: FavouriteCinema?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLikedCinema(likedCinema: LikedCinema?)

    @Query("DELETE FROM liked_table WHERE original_id == :cinemaOriginalId")
    fun deleteLikedItem(cinemaOriginalId: Int)

    @Query("DELETE FROM favourite_table WHERE original_id == :cinemaOriginalId")
    fun deleteFavouriteItem(cinemaOriginalId: Int)

    @Query("DELETE FROM cinema_table")
    fun deleteAll()

    @Query("SELECT * FROM cinema_table")
    fun getAll(): LiveData<List<Cinema>>

    @Query("SELECT c.original_id, c.title, c.description, c.image, c.titleColor, c.id FROM cinema_table c, favourite_table f WHERE c.original_id = f.original_id")
    fun getFavouriteCinema(): LiveData<List<Cinema>>

    @Query("SELECT * FROM liked_table WHERE original_id = :cinemaOriginalId")
    fun getLikedCinema(cinemaOriginalId: Int): List<LikedCinema>

    @Query("UPDATE cinema_table SET titleColor = :titleColor WHERE id = :id")
    fun updateTitleColor(titleColor: Int, id: Int)
}