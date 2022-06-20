package com.example.themoviedatabase.ui.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedatabase.cache.MovieCache
import com.example.themoviedatabase.model.Resource
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.use_case.UpdateMovieDetailsUseCase
import com.example.themoviedatabase.use_case.UpdateSimilarMoviesUseCase
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
    private val updateSimilarMoviesUseCase: UpdateSimilarMoviesUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsState.Instructions)
    private val _eventDispatcher = MutableSharedFlow<Event>()
    val uiState: SharedFlow<MovieDetailsState> = _uiState

    init {
        setUpContent()
    }

    private fun setUpContent() {
        movieCache.getSelectedMovieId()
            .flatMapLatest { movieId ->
                _eventDispatcher.filterIsInstance<Event.Load>()
                    .throttleFirst(viewModelScope.plus(dispatcherProvider.main), THROTTLE_INTERVAL)
                    .onStart { emit(initialEvent) }
                    .map { action ->
                        action.actions.map { type ->
                            when (type) {
                                ActionType.LOAD_MOVIE_DETAILS -> updateMovieDetailsUseCase.invoke(movieId)
                                    .map { updateMovieDetails(it) }
                                    .onStart { emit(setLoadMovieDetailsState()) }
                                ActionType.LOAD_VIDEOS -> updateVideosUseCase.invoke(movieId)
                                    .map { updateVideos(it) }
                                    .onStart { emit(setLoadVideosState()) }
                                ActionType.LOAD_SIMILAR_MOVIES -> updateSimilarMoviesUseCase.invoke(movieId)
                                    .map { updateSimilarMovies(it) }
                                    .onStart { emit(setLoadSimilarMoviesState()) }
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

    private suspend fun setLoadSimilarMoviesState(): MovieDetailsState {
        return MovieDetailsState.Builder(_uiState.first())
            .similarMovies(null)
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

    private suspend fun updateSimilarMovies(resource: Resource<List<MovieSummary>>): MovieDetailsState {
        return MovieDetailsState.Builder(_uiState.first())
            .similarMovies(resource.data)
            .similarMoviesError(resource.error)
            .build()
    }


    fun newEvent(event: Event) {
        viewModelScope.launch(dispatcherProvider.main) { _eventDispatcher.emit(event) }
    }

    companion object {
        val initialEvent = Event.Load.Builder()
            .add(ActionType.LOAD_MOVIE_DETAILS)
            .add(ActionType.LOAD_VIDEOS)
            .add(ActionType.LOAD_SIMILAR_MOVIES)
            .build()
    }
}