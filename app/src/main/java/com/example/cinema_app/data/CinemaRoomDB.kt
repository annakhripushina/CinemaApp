package com.example.cinema_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Cinema::class, FavouriteCinema::class, LikedCinema::class], version = 1)
abstract class CinemaRoomDB : RoomDatabase() {
    abstract fun getCinemaDao(): CinemaDao

    companion object {
        @Volatile
        private var INSTANCE: CinemaRoomDB? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): CinemaRoomDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CinemaRoomDB::class.java,
                    "cinema_database.db"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    //.fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        /*  fun getInstance(context: Context, scope: CoroutineScope): CinemaRoomDB =
              INSTANCE ?: synchronized(this) {
                  INSTANCE ?: buildDatabase(context, scope).also { INSTANCE = it }
              }

          private fun buildDatabase(context: Context, scope: CoroutineScope): CinemaRoomDB {
              val dbName = "cinema_database.db"
              context.applicationContext.deleteDatabase(dbName)
              return Room.databaseBuilder(
                  context.applicationContext, CinemaRoomDB::class.java, dbName
              )
                  .fallbackToDestructiveMigration()
                  .addCallback(DatabaseCallback(scope))
                  .build()
          }
  */
        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        //database.getCinemaDao().deleteAll()
                    }
                }
            }
        }

    }

}