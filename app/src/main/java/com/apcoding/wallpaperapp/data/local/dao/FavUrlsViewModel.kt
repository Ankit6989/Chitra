package com.apcoding.wallpaperapp.data.local.dao

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.apcoding.wallpaperapp.data.local.UnsplashDatabase
import com.apcoding.wallpaperapp.data.repository.FavUrlsRepository
import com.apcoding.wallpaperapp.model.FavouriteUrls
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavUrlsViewModel(
    application: Application //It expects an instance of the Application class to be paIt expects an instance of the Application class to be passed as an argument when creating an instance of this ViewModel.ssed as an argument when creating an instance of this ViewModel.
) : AndroidViewModel(application) { //This constructor is used to create an instance of a ViewModel class that extends AndroidViewModel. It takes an Application instance as a parameter, which allows the ViewModel to have access to the application's context for Android-specific functionality. This ViewModel is likely used to manage and provide data and logic for an Android app's UI components.

    var clearAllImagesDialogState =
        mutableStateOf(false) //clearAllImagesDialogState is a variable that keeps track of whether a pop-up dialog should be cleared (closed).
    private val readAllFavUrls: LiveData<List<FavouriteUrls>> //readAllFavUrls is a LiveData object that holds a list of all the favorite URLs in the database.
    private val unsplashImageDao = UnsplashDatabase.getDatabase(application)
        .unsplashImageDao() //unsplashImageDao is a connection to a database. It's how we interact with the database to perform actions like reading and writing data. In this case, it's set up to work with a database for Unsplash images.
    private val repository =
        FavUrlsRepository(unsplashImageDao) //repository is like a manager for our data. It's created to handle interactions with the database and organize how data is accessed and modified. In this code, it's set up to work with the unsplashImageDao.
    val getAllFavUrls =
        repository.readAllFavUrls //getAllFavUrls is a property that gives us access to all the favorite URLs stored in the database. It's set to use the repository to read all the favorite URLs.

    init { //init is a special function that's called when an instance of this ViewModel is created. It's used to set up the ViewModel's initial state.
        readAllFavUrls =
            repository.readAllFavUrls //readAllFavUrls is set to use the repository to read all the favorite URLs.
    }

    fun addToFav(favouriteUrls: FavouriteUrls) {
        //This is a function that allows us to add a favorite URL (favouriteUrls) to our app's collection of favorite URLs.

        // We use "viewModelScope.launch" to start a new coroutine (a kind of background task).
        // We specify "Dispatchers.IO" to perform this task in the input/output (I/O) thread.
        viewModelScope.launch(Dispatchers.IO) {

            // Inside the coroutine, we first check if the favorite URL already exists in our collection.
            val existingFavUrl = repository.getFavouriteUrlById(favouriteUrls.id)

            // If the URL doesn't already exist (it's null), we add it to our collection.
            if (existingFavUrl == null) {
                repository.addFavUrl(favouriteUrls)
            } else {
                // Display a message to the user that the image is already in their favorites
            }
        }
    }

    //This function is used to delete a specific favorite URL (favouriteUrls) from your app's collection.
    fun deleteFavouriteUrl(favouriteUrls: FavouriteUrls) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavUrl(favouriteUrls)
        }
    }

    //This function is used to delete all favorite URLs from your app's collection. It clears the entire collection.
    fun deleteAllFavouriteUrls() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavUrls()
        }
    }
}
