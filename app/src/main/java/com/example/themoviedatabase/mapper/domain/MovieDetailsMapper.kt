package com.example.themoviedatabase.mapper.domain

import androidx.annotation.VisibleForTesting
import com.example.themoviedatabase.R
import com.example.themoviedatabase.database.query.MovieDetailsQuery
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.utils.UIPlural
import com.example.themoviedatabase.utils.UIText
import com.example.themoviedatabase.utils.toHourMinutes
import com.example.themoviedatabase.utils.thousandFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsMapper @Inject constructor(
    private val imagePathMapper: Mapper<String, ImagePath>
) : Mapper<MovieDetailsQuery, MovieDetails> {

    override fun map(objectToMap: MovieDetailsQuery): MovieDetails {
        val backdropPath = objectToMap.backdropPaths?.firstOrNull().orEmpty()
        return MovieDetails(
            movieId = objectToMap.movieTable.movieId,
            title = UIText(string = objectToMap.movieTable.title),
            backdropPath = imagePathMapper.map(backdropPath),
            rating = objectToMap.movieTable.rating,
            ratingCount = UIPlural(
                pluralId = R.plurals.rating_count_format,
                formatArgs = objectToMap.movieTable.ratingCount.thousandFormat(),
                count = objectToMap.movieTable.ratingCount
            ),
            genres = objectToMap.genres?.joinToString(separator = ", ") { it.name }.orEmpty(),
            overview = UIText(string = objectToMap.movieTable.overview),
            releaseDate = objectToMap.movieTable.releaseDate,
            runtimeIcon = ImagePath(backup = R.drawable.clock),
            runtime = objectToMap.movieTable.runtime.takeIf { it > 0 }?.toHourMinutes() ?: PLACEHOLDER
        )
    }

    companion object {
        @VisibleForTesting const val PLACEHOLDER = "--:--"
    }
}