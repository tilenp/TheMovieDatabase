package com.example.themoviedatabase.mapper.domain

import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.database.table.ImagePathTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.utils.UIText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieSummaryMapper @Inject constructor(
    private val imagePathMapper: Mapper<String, ImagePath>
) : Mapper<MovieSummaryQuery, MovieSummary> {

    override fun map(objectToMap: MovieSummaryQuery): MovieSummary {
        val url = objectToMap.imagePaths?.firstOrNull().orEmpty()
        return MovieSummary(
            movieId = objectToMap.movieId,
            title = UIText(string = objectToMap.title),
            posterPath = imagePathMapper.map(url),
            rating = objectToMap.rating
        )
    }
}