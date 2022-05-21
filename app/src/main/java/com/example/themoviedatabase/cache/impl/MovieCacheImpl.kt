package com.example.themoviedatabase.cache.impl

import com.example.themoviedatabase.cache.MovieCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieCacheImpl @Inject constructor(): MovieCache {

    private val selectedMovieIdSharedFlow = MutableSharedFlow<Long>(replay = 1)

    override suspend fun setSelectedMovieId(characterId: Long) {
        selectedMovieIdSharedFlow.emit(characterId)
    }

    override fun getSelectedMovieId(): Flow<Long> {
        return selectedMovieIdSharedFlow
    }
}