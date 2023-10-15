package com.apcoding.wallpaperapp.di

import android.content.Context
import androidx.room.Room
import com.apcoding.wallpaperapp.data.local.UnsplashDatabase
import com.apcoding.wallpaperapp.util.Constants.UNSPLASH_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module //This annotation indicates that this is a Dagger-Hilt module. Dagger-Hilt is a dependency injection library used in Android apps to manage dependencies.
@InstallIn(SingletonComponent::class) //This annotation specifies where this module should be installed. In this case, it's installed in the SingletonComponent, which means it provides dependencies that have a single instance throughout the app's lifecycle.
object DatabaseModule { //This is the DatabaseModule class. It's a Dagger-Hilt module that provides the database instance as a dependency.

    @Provides //This annotation is used on the provideDatabase function to indicate that this function provides a specific dependency, which is the database instance.
    @Singleton //This annotation specifies that there should be only one instance of the database for the entire app. It ensures that the database is a singleton.
    fun provideDatabase(
        @ApplicationContext context: Context //This is the function that provides the database. It takes the Android application context as a parameter.
    ): UnsplashDatabase {
        // Here, we use Room's databaseBuilder to create the database.
        // - "context" is the Android application context.
        // - "UnsplashDatabase::class.java" specifies the database class we're creating.
        // - "UNSPLASH_DATABASE" is the name of the database.
        //   (This should be a constant that tells Room the name of your database.)
        // We call .build() to actually create the database.
        return Room.databaseBuilder(
            context,
            UnsplashDatabase::class.java,
            UNSPLASH_DATABASE
        ).build()
    }
}