package com.example.themoviedatabase.ui.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedatabase.cache.MovieCache
import com.example.themoviedatabase.model.Resource
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.use_case.UpdateMovieDetailsUseCase
import com.example.themoviedatabase.use_case.UpdateVideosUseCase
import com.example.themoviedatabase.utils.DispatcherProvider
import com.example.themoviedatabase.utils.THROTTLE_INTERVAL
import com.example.themoviedatabase.utils.throttleFirst
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val movieCache: MovieCache,
    private val updateMovieDetailsUseCase: UpdateMovieDetailsUseCase,
    private val updateVideosUseCase: UpdateVideosUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsState.Instructions)
    private val _actionDispatcher = MutableSharedFlow<Action>()
    val uiState: SharedFlow<MovieDetailsState> = _uiState
    val actions: SharedFlow<Action> = _actionDispatcher
        .throttleFirst(viewModelScope.plus(dispatcherProvider.main), THROTTLE_INTERVAL)
        .shareIn(
            scope =  viewModelScope.plus(dispatcherProvider.main),
            started = SharingStarted.WhileSubscribed(5000)
        )

    init {
        setUpContent()
    }

    private fun setUpContent() {
        movieCache.getSelectedMovieId()
            .flatMapLatest { movieId ->
                merge(
                    updateMovieDetailsUseCase.invoke(movieId).map { updateMovieDetails(it) },
                    updateVideosUseCase.invoke(movieId).map { updateVideos(it) }
                )
                    .onStart { emit(MovieDetailsState()) }
            }
            .onEach { _uiState.emit(it) }
            .launchIn(viewModelScope.plus(dispatcherProvider.main))
    }

    private suspend fun updateMovieDetails(resource: Resource<MovieDetails>): MovieDetailsState {
        return _uiState.first().copy(movieDetails = resource.data)
    }

    private suspend fun updateVideos(resource: Resource<List<Video>>): MovieDetailsState {
        return _uiState.first().copy(videos = resource.data)
    }

    fun newAction(action: Action) {
        viewModelScope.launch(dispatcherProvider.main) { _actionDispatcher.emit(action) }
    }

    sealed class Action {
        data class SelectVideo(val url: String): Action()
    }
}