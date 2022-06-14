package com.example.themoviedatabase.ui.movies_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.themoviedatabase.cache.MovieCache
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.network.MovieRequestQuery
import com.example.themoviedatabase.network.SortBy
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.utils.DispatcherProvider
import com.example.themoviedatabase.utils.ErrorMessageHandler
import com.example.themoviedatabase.utils.THROTTLE_INTERVAL
import com.example.themoviedatabase.utils.throttleFirst
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val movieCache: MovieCache,
    private val movieRepository: MovieRepository,
    private val errorMessageHandler: ErrorMessageHandler,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _actionDispatcher = MutableSharedFlow<Action>(replay = 1)
    val movies: Flow<PagingData<MovieSummary>> = movieRepository
        .getMovieSummaries(
            pagingConfig = PagingConfig(pageSize = 10),
            requestQuery = MovieRequestQuery.Builder().sortBy(SortBy.POPULARITY_DESC)
        ).cachedIn(viewModelScope.plus(dispatcherProvider.io))

    init {
        setUpUserAction()
    }

    private fun setUpUserAction() {
        _actionDispatcher
            .throttleFirst(viewModelScope.plus(dispatcherProvider.main), THROTTLE_INTERVAL)
            .flatMapLatest { action ->
                when (action) {
                    is Action.SelectMovie -> flow<Unit> { movieCache.setSelectedMovieId(action.movieId) }
                }
            }.launchIn(viewModelScope.plus(dispatcherProvider.main))
    }

    fun getErrorMessage(throwable: Throwable): Int {
        return errorMessageHandler.getExceptionMessage(throwable)
    }

    fun newAction(action: Action) {
        viewModelScope.launch(dispatcherProvider.main) { _actionDispatcher.emit(action) }
    }

    sealed class Action {
        data class SelectMovie(val movieId: Long): Action()
    }
}