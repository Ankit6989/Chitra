package com.apcoding.wallpaperapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apcoding.wallpaperapp.data.local.dao.UnsplashImageDao
import com.apcoding.wallpaperapp.data.local.dao.UnsplashRemoteKeysDao
import com.apcoding.wallpaperapp.model.FavouriteUrls
import com.apcoding.wallpaperapp.model.UnsplashImage
import com.apcoding.wallpaperapp.model.UnsplashRemoteKeys
import com.apcoding.wallpaperapp.util.Constants

@Database(
    entities = [UnsplashImage::class, FavouriteUrls::class, UnsplashRemoteKeys::class],
    version = 1
)
abstract class UnsplashDatabase : RoomDatabase() {

    abstract fun unsplashImageDao(): UnsplashImageDao //The function returns an instance of UnsplashImageDao. This indicates that it's used to access and manipulate data related to Unsplash images within the database.
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao //The function returns an instance of UnsplashRemoteKeysDao. This indicates that it's used to access and manipulate data related to remote keys within the database.

    //This line declares a private and @Volatile property named INSTANCE. The @Volatile annotation ensures that reads and writes to this property are atomic and not cached by threads, which is important for thread safety.
    //Initially, it's set to null, indicating that the database instance has not been created yet.
    companion object {
        @Volatile
        private var INSTANCE: UnsplashDatabase? = null

        fun getDatabase(context: Context): UnsplashDatabase { //This function takes a Context object as a parameter and returns an instance of UnsplashDatabase.
            if (INSTANCE == null) { //The function checks whether the database instance has been created or not. If it hasn't, it creates it.
                synchronized(this) { //The synchronized block ensures that only one thread of execution at a time can enter this block of code, which makes sure the database only gets initialized once.
                    INSTANCE =
                        Room.databaseBuilder( //The Room.databaseBuilder() function takes three parameters: a Context object, the database class, and the database name.
                            context.applicationContext,
                            UnsplashDatabase::class.java,
                            Constants.UNSPLASH_DATABASE
                        )
                            .build() //After configuring the database using databaseBuilder, the build() method is called to actually create the database instance.
                }
            }
            return INSTANCE!!  //Finally, the code returns the created or existing INSTANCE of the database.
            // The !! operator asserts that INSTANCE is not null at this point. If it were null, it would indicate an unexpected error, and an exception would be thrown.
        }
    }
}