package com.example.themoviedatabase.mapper.domain

import com.example.themoviedatabase.database.query.MovieDetailsQuery
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.utils.UIText
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
            overview = UIText(string = objectToMap.movieTable.overview)
        )
    }
}