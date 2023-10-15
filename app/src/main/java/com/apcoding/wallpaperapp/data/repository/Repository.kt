package com.apcoding.wallpaperapp.data.repository

import androidx.compose.runtime.mutableIntStateOf
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apcoding.wallpaperapp.data.local.UnsplashDatabase
import com.apcoding.wallpaperapp.data.remote.UnsplashApi
import com.apcoding.wallpaperapp.model.UnsplashImage
import com.apcoding.wallpaperapp.paging.SearchPagingSource
import com.apcoding.wallpaperapp.paging.UnsplashRemoteMediator
import com.apcoding.wallpaperapp.ui.screens.common.search.SearchChip
import com.apcoding.wallpaperapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashDatabase: UnsplashDatabase,
) {
    val wallpaperItems =
        arrayListOf(
            SearchChip("Digital Art", "digital art"),
            SearchChip("Popular", "4k wallpapers"),
            SearchChip("Space", "space"),
            SearchChip("Dark", "dark wallpapers"),
            SearchChip("Night", "night wallpapers"),
            SearchChip("Mobile", "Android Wallpapers"),
            SearchChip("Nature", "Nature"),
        )

    var selectedIndex = mutableIntStateOf(0)

    fun getAllImages(): Flow<PagingData<UnsplashImage>> {
        val pagingSourceFactory = { unsplashDatabase.unsplashImageDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(
                unsplashApi = unsplashApi,
                unsplashDatabase = unsplashDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(
                unsplashApi = unsplashApi,
                unsplashDatabase = unsplashDatabase
            ),
            pagingSourceFactory = {
                SearchPagingSource(unsplashApi = unsplashApi, query = query)
            }
        ).flow
    }

}

