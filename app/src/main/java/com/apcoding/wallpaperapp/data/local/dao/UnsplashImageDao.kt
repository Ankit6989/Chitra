package com.apcoding.wallpaperapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apcoding.wallpaperapp.model.FavouriteUrls
import com.apcoding.wallpaperapp.model.UnsplashImage

@Dao
interface UnsplashImageDao {

    // This is a function declaration within the DAO interface.
// It defines a function named getAllImages that returns a PagingSource.
// The PagingSource is a part of Android's Paging library and is used for loading data in a paginated manner.
    @Query("SELECT * FROM unsplash_image_table")
    fun getAllImages(): PagingSource<Int, UnsplashImage>

    //ORDER BY id ASC: This part of the query sorts the results in ascending order based on the "id" column.
    // It orders the retrieved records by their "id" values from smallest to largest.
    @Query("SELECT * FROM unsplash_fav_urls_table ORDER BY id ASC")
    fun getAllFavUrls(): LiveData<List<FavouriteUrls>>

    // This code declares a suspend function named getFavouriteUrl that uses a Room database query to retrieve a favorite URL from the "unsplash_fav_urls_table" table based on its "id."
    // The function returns the result as a nullable FavouriteUrls object, allowing for the possibility that no matching record is found in the database
    @Query("SELECT * FROM unsplash_fav_urls_table WHERE id = :id")
    suspend fun getFavouriteUrl(id: Int): FavouriteUrls?

    // This code defines a suspend function named addImages that uses Room's @Insert annotation to insert a list of UnsplashImage objects into a database table. If there are conflicts with existing entries (determined by the primary key), the existing entries will be replaced with the new ones.
    // This is a common strategy for updating or replacing data when inserting into a database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<UnsplashImage>)

    // This code defines a suspend function named addToFavourites that uses Room's @Insert annotation to insert a FavouriteUrls object into a database table.
    // If there are conflicts with an existing entry (determined by the primary key), the existing entry will be replaced with the new one, as specified by the onConflict strategy. This is a common strategy for updating or replacing data when inserting into a database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourites(favouriteUrls: FavouriteUrls)

    @Delete
    suspend fun deleteFavouriteUrl(favouriteUrls: FavouriteUrls)

    @Query("DELETE FROM unsplash_fav_urls_table")
    suspend fun deleteAllFavUrls()

    @Query("DELETE FROM unsplash_image_table")
    suspend fun deleteAllImages()
}