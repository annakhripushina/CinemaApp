package com.example.cinema_app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema
import com.example.cinema_app.data.entity.ScheduleCinema
import io.reactivex.rxjava3.core.Flowable

@Dao
interface CinemaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCinema(cinema: Cinema?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteCinema(favouriteCinema: FavouriteCinema?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLikedCinema(likedCinema: LikedCinema?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScheduleCinema(ScheduleCinema: ScheduleCinema?)

    @Query("DELETE FROM cinemaTable")
    suspend fun deleteAll()

    @Query("DELETE FROM likedTable WHERE originalId == :cinemaOriginalId")
    suspend fun deleteLikedCinema(cinemaOriginalId: Int)

    @Query("DELETE FROM favouriteTable WHERE originalId == :cinemaOriginalId")
    suspend fun deleteFavouriteCinema(cinemaOriginalId: Int)

    @Query("DELETE FROM scheduleTable WHERE originalId = :id")
    suspend fun deleteScheduleCinema(id: Int)

    @Query("SELECT * FROM cinemaTable")
    fun getAll(): Flowable<List<Cinema>>

    @Query("SELECT c.originalId, c.title, c.description, c.image, c.titleColor, c.dateViewed, c.id FROM cinemaTable c, favouriteTable f WHERE c.originalId = f.originalId")
    fun getFavouriteCinema(): Flowable<List<Cinema>>

    @Query("SELECT * FROM likedTable")
    fun getLikedCinema(): Flowable<List<LikedCinema>>

    @Query("SELECT * FROM scheduleTable")
    fun getSchedule(): Flowable<List<ScheduleCinema>>

    @Query("SELECT c.originalId, c.title, c.description, c.image, c.titleColor, c.dateViewed, c.id FROM cinemaTable c, scheduleTable s WHERE c.originalId = s.originalId")
    fun getScheduleCinema(): Flowable<List<Cinema>>

    @Query("SELECT * FROM cinemaTable WHERE title LIKE '%' || :title || '%'")
    fun searchCinema(title: String): Flowable<List<Cinema>>

    @Query("UPDATE cinemaTable SET titleColor = :titleColor WHERE id = :id")
    suspend fun updateTitleColor(titleColor: Int, id: Int)

    @Query("UPDATE cinemaTable SET dateViewed = :dateViewed WHERE originalId = :originalId")
    suspend fun updateDateViewed(dateViewed: String, originalId: Int)

}