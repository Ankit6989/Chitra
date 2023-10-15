package com.apcoding.wallpaperapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apcoding.wallpaperapp.model.UnsplashRemoteKeys

@Dao
interface UnsplashRemoteKeysDao {

    @Query("SELECT * FROM unsplash_remote_keys_table WHERE id =:id") //Retrieve a specific remote key based on its "id."
    suspend fun getRemoteKeys(id: String): UnsplashRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE) //Insert multiple remote keys into the table with conflict resolution (replacement if conflicts occur).
    suspend fun addAllRemoteKeys(remoteKeys: List<UnsplashRemoteKeys>)

    @Query("DELETE FROM unsplash_remote_keys_table") //Delete all remote key records from the table.
    suspend fun deleteAllRemoteKeys()

}