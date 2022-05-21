package com.example.themoviedatabase.ui.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.repository.MovieRepository
import com.example.themoviedatabase.utils.DispatcherProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsState.Empty)
    val uiState: SharedFlow<MovieDetailsState> = _uiState

    init {
        setUpContent()
    }

    private fun setUpContent() {
        movieRepository
            .getSelectedMovieId()
            .flatMapLatest { movieId -> movieRepository.getMovieDetailsWithId(movieId) }
            .map { movieSummary -> buildContent(movieSummary) }
            .onEach { _uiState.emit(it) }
            .launchIn(viewModelScope.plus(dispatcherProvider.main))
    }

    private suspend fun buildContent(movieDetails: MovieDetails): MovieDetailsState {
        return _uiState.first().copy(movieDetails = movieDetails)
    }
}