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
    private val _actionDispatcher = MutableStateFlow<Action>(initialAction)
    val uiState: SharedFlow<MovieDetailsState> = _uiState

    init {
        setUpContent()
    }

    private fun setUpContent() {
        movieCache.getSelectedMovieId()
            .flatMapLatest { movieId ->
                _actionDispatcher.filterIsInstance<Action.Load>()
                    .throttleFirst(viewModelScope.plus(dispatcherProvider.main), THROTTLE_INTERVAL)
                    .map { action ->
                        action.actions.map { type ->
                            when (type) {
                                ActionType.LOAD_MOVIE_DETAILS -> updateMovieDetailsUseCase.invoke(movieId)
                                    .map { updateMovieDetails(it) }
                                    .onStart { emit(setLoadMovieDetailsState()) }
                                ActionType.LOAD_VIDEOS -> updateVideosUseCase.invoke(movieId)
                                    .map { updateVideos(it) }
                                    .onStart { emit(setLoadVideosState()) }
                            }
                        }
                    }
                    .flatMapLatest { flows -> merge(flows.merge()) }
                    .onStart { emit(MovieDetailsState.Builder().build()) }
            }
            .onEach { _uiState.emit(it) }
            .launchIn(viewModelScope.plus(dispatcherProvider.main))
    }

    private suspend fun setLoadMovieDetailsState(): MovieDetailsState {
        return MovieDetailsState.Builder(_uiState.first())
            .movieDetails(null)
            .resetError()
            .build()
    }

    private suspend fun setLoadVideosState(): MovieDetailsState {
        return MovieDetailsState.Builder(_uiState.first())
            .videos(null)
            .resetError()
            .build()
    }

    private suspend fun updateMovieDetails(resource: Resource<MovieDetails>): MovieDetailsState {
        return MovieDetailsState.Builder(_uiState.first())
            .movieDetails(resource.data)
            .movieDetailsError(resource.error)
            .build()
    }

    private suspend fun updateVideos(resource: Resource<List<Video>>): MovieDetailsState {
        return MovieDetailsState.Builder(_uiState.first())
            .videos(resource.data)
            .videosError(resource.error)
            .build()
    }

    fun newAction(action: Action) {
        viewModelScope.launch(dispatcherProvider.main) { _actionDispatcher.emit(action) }
    }

    companion object {
        val initialAction = Action.Load.Builder()
            .add(ActionType.LOAD_MOVIE_DETAILS)
            .add(ActionType.LOAD_VIDEOS)
            .build()
    }
}