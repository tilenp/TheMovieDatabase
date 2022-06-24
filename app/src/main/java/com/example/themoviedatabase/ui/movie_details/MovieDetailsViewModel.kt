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
                    .flatMapLatest { event ->
                        val stateBuilder = getLoadingState(event.actions)
                        merge(
                            flowOf(stateBuilder),
                            updateMovieDetailsUseCase.invoke(movieId, event.actions.contains(ActionType.LOAD_MOVIE_DETAILS))
                                .map { updateMovieDetails(stateBuilder, it) },
                            updateVideosUseCase.invoke(movieId, event.actions.contains(ActionType.LOAD_VIDEOS))
                                .map { updateVideos(stateBuilder, it) },
                            updateSimilarMoviesUseCase.invoke(movieId, event.actions.contains(ActionType.LOAD_SIMILAR_MOVIES))
                                .map { updateSimilarMovies(stateBuilder, it) }
                        )
                    }
                    .map { stateBuilder -> stateBuilder.build() }
            }
            .onEach { _uiState.emit(it) }
            .launchIn(viewModelScope.plus(dispatcherProvider.main))
    }

    private suspend fun getLoadingState(actions: Set<ActionType>):MovieDetailsState.Builder {
        val stateBuilder = MovieDetailsState.Builder(_uiState.first())
            .instructionMessage(null)
            .resetError()
        actions.forEach { type ->
            when (type) {
                ActionType.LOAD_MOVIE_DETAILS -> stateBuilder.movieDetails(null)
                ActionType.LOAD_VIDEOS -> stateBuilder.videos(null)
                ActionType.LOAD_SIMILAR_MOVIES -> stateBuilder.similarMovies(null)
            }
        }
        return stateBuilder
    }

    private fun updateMovieDetails(
        stateBuilder: MovieDetailsState.Builder,
        resource: Resource<MovieDetails>
    ): MovieDetailsState.Builder {
        return stateBuilder
            .movieDetails(resource.data)
            .movieDetailsError(resource.error)
    }

    private fun updateVideos(
        stateBuilder: MovieDetailsState.Builder,
        resource: Resource<List<Video>>
    ): MovieDetailsState.Builder {
        return stateBuilder
            .videos(resource.data)
            .videosError(resource.error)
    }

    private fun updateSimilarMovies(
        stateBuilder: MovieDetailsState.Builder,
        resource: Resource<List<MovieSummary>>
    ): MovieDetailsState.Builder {
        return stateBuilder
            .similarMovies(resource.data)
            .similarMoviesError(resource.error)
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