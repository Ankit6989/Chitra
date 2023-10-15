package com.apcoding.wallpaperapp.util

object Constants {
    const val BASE_URL =
        "https://api.unsplash.com" //This constant stores the base URL for an API, which is used to build the Retrofit instance.
    //const val IMAGES_END_POINT = "photos"

    const val UNSPLASH_DATABASE =
        "unsplash_database" //This constant holds the name of a database, possibly related to Unsplash data.
    const val UNSPLASH_IMAGE_TABLE =
        "unsplash_image_table" //This constant represents the name of a table in the database, likely used to store Unsplash images.
    const val UNSPLASH_FAV_URLS_TABLE =
        "unsplash_fav_urls_table" //This constant is the name of another table in the database, possibly used to store favorite URLs.
    const val UNSPLASH_REMOTE_KEYS_TABLE =
        "unsplash_remote_keys_table" // This constant is the name of yet another table in the database, possibly used to store remote keys for pagination or synchronization.
    const val ITEMS_PER_PAGE =
        10 // This constant specifies the number of items to be displayed per page, often used for paginated data.

}