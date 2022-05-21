package com.example.themoviedatabase.cache

import kotlinx.coroutines.flow.Flow

interface MovieCache {

    suspend fun setSelectedMovieId(characterId: Long)

    fun getSelectedMovieId(): Flow<Long>
}