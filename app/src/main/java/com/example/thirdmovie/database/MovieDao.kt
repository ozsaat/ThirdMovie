package com.example.thirdmovie.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.thirdmovie.network.Movie

@Dao
interface MovieDao {
    @Query("select * from Movie")
    fun getMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)
}

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao : MovieDao
}

private lateinit var INSTANCE: MovieDatabase

fun getDatabase(context: Context): MovieDatabase {
    synchronized(MovieDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            MovieDatabase::class.java,
            "movies").build()
        }
    }
    return INSTANCE
}
