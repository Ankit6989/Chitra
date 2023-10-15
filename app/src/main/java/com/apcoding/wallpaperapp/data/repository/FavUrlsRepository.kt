package com.apcoding.wallpaperapp.data.repository

import com.apcoding.wallpaperapp.data.local.dao.UnsplashImageDao
import com.apcoding.wallpaperapp.model.FavouriteUrls


//We have a class called "FavUrlsRepository" that helps us manage favorite URLs in our app. It's set up to work with a database for Unsplash images.
class FavUrlsRepository(private val unsplashImageDao: UnsplashImageDao) {
    // We declare a property called "readAllFavUrls" to get a list of all favorite URLs from the database.
    val readAllFavUrls = unsplashImageDao.getAllFavUrls()


    // This function allows us to add a new favorite URL to the database.
    // It's marked with "suspend" because it can be used with asynchronous operations.
    // This keyword tells the app that it's okay to wait for this function to finish before moving on to other tasks.
    // Inside the addFavUrl function, we first check if a URL with the same ID already exists in our database. We don't want to add the same URL twice!
    suspend fun addFavUrl(favUrls: FavouriteUrls) {

        // First, we check if a URL with the same ID already exists in the database.
        val existingUrl = unsplashImageDao.getFavouriteUrl(favUrls.id)

        // If there is no URL with the same ID (existingUrl is null), we can add the new URL to the database.
        if (existingUrl == null) {
            unsplashImageDao.addToFavourites(favUrls)
        }
    }

    suspend fun getFavouriteUrlById(id: Int): FavouriteUrls? {
        // This function lets us find a favorite URL in our database using its unique ID.
        // We pass the ID as a parameter to the function.

        // We call another function from "unsplashImageDao" to do the actual search.
        // If a URL with the given ID is found, we return it. If not, we return null.
        return unsplashImageDao.getFavouriteUrl(id)
    }

    suspend fun deleteFavUrl(favUrls: FavouriteUrls) {
        // This function helps us remove a specific favorite URL from our database.
        // We need to provide the URL we want to delete as a parameter.

        // We call another function from "unsplashImageDao" to perform the deletion.
        unsplashImageDao.deleteFavouriteUrl(favUrls)
    }

    suspend fun deleteAllFavUrls() {
        // This function allows us to clear (delete) all favorite URLs from our database.
        // It doesn't need any parameters.

        // We call another function from "unsplashImageDao" to perform the deletion.
        unsplashImageDao.deleteAllFavUrls()
    }
}