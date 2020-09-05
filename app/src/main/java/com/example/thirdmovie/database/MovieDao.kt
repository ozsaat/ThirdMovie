package com.example.thirdmovie.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.thirdmovie.network.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("select * from MovieEntity")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)
}

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao : MovieDao
}

private lateinit var INSTANCE: MovieDatabase

fun getDatabase(context: Context): MovieDatabase {
    synchronized(MovieDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            MovieDatabase::class.java,
            "movies")
                .build()
        }
    }
    return INSTANCE
}
