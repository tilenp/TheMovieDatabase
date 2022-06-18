package com.example.themoviedatabase.ui.movie_details

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.Video
import com.example.themoviedatabase.utils.UISnackbar

@Immutable
class MovieDetailsState private constructor(builder: Builder) {
    val movieDetails: MovieDetails? = builder.movieDetails
    val videos: List<Video>? = builder.videos

    @StringRes
    val instructionMessage: Int? = builder.instructionMessage
    val error: UISnackbar<Event.Load>? = builder.error

    class Builder(state: MovieDetailsState? = null) {
        var movieDetails: MovieDetails? = state?.movieDetails
        var videos: List<Video>? = state?.videos

        @StringRes
        var instructionMessage: Int? = state?.instructionMessage
        var error: UISnackbar<Event.Load>? = state?.error

        fun movieDetails(movieDetails: MovieDetails?): Builder {
            this.movieDetails = movieDetails
            return this
        }

        fun videos(videos: List<Video>?): Builder {
            this.videos = videos
            return this
        }

        fun instructionMessage(@StringRes instructionMessage: Int?): Builder {
            this.instructionMessage = instructionMessage
            return this
        }

        fun movieDetailsError(throwable: Throwable?): Builder {
            throwable?.let {
                val message = R.string.An_error_occurred_while_loading_movie_details
                val actionLabel = R.string.Retry
                val event = Event.Load.Builder(error?.action)
                    .add(ActionType.LOAD_MOVIE_DETAILS)
                    .build()
                this.error = UISnackbar(message, actionLabel, event)
            }
            return this
        }

        fun videosError(throwable: Throwable?): Builder {
            throwable?.let {
                val message = when (error) {
                    null -> R.string.An_error_occurred_while_loading_videos
                    else -> R.string.An_error_occurred_while_loading_movie_details
                }
                val actionLabel = R.string.Retry
                val event = Event.Load.Builder(error?.action)
                    .add(ActionType.LOAD_VIDEOS)
                    .build()
                this.error = UISnackbar(message, actionLabel, event)
            }
            return this
        }

        fun resetError(): Builder {
            this.error = null
            return this
        }

        fun build(): MovieDetailsState {
            return MovieDetailsState(this)
        }
    }

    companion object {
        val Instructions = Builder()
            .instructionMessage(R.string.Select_a_movie)
            .build()
    }
}
