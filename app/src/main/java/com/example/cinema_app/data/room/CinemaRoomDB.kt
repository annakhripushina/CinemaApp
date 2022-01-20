package com.example.cinema_app.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema
import com.example.cinema_app.data.entity.SheduleCinema

@Database(
    entities = [Cinema::class, FavouriteCinema::class, LikedCinema::class, SheduleCinema::class],
    version = 1
)
abstract class CinemaRoomDB : RoomDatabase() {
    abstract fun getCinemaDao(): CinemaDao

    companion object {
        @Volatile
        private var INSTANCE: CinemaRoomDB? = null

        fun getDatabase(
            context: Context
        ): CinemaRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CinemaRoomDB::class.java,
                    "cinema_database.db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

}