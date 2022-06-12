package com.example.themoviedatabase.dagger.module

import com.example.themoviedatabase.database.query.MovieDetailsQuery
import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.database.table.*
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.mapper.domain.ImagePathMovieMapper
import com.example.themoviedatabase.mapper.domain.MovieDetailsMapper
import com.example.themoviedatabase.mapper.domain.MovieSummaryMapper
import com.example.themoviedatabase.mapper.table.*
import com.example.themoviedatabase.model.domain.ImagePath
import com.example.themoviedatabase.model.domain.MovieDetails
import com.example.themoviedatabase.model.domain.MovieSummary
import com.example.themoviedatabase.model.dto.MovieDTO
import dagger.Binds
import dagger.Module

@Module
interface MapperModule {

    /** database table mappers **/

    @Binds
    fun bindsBackdropImageTableMapper(backdropImageTableMapper: BackdropImageTableMapper): Mapper<MovieDTO, BackdropImageTable>

    @Binds
    fun bindsGenreTableMapper(genreTableMapper: GenreTableMapper): Mapper<MovieDTO, List<@JvmSuppressWildcards GenreTable>>

    @Binds
    fun bindsImagePathTableMapper(imagePathTableMapper: ImagePathTableMapper): Mapper<MovieDTO, ImagePathTable>

    @Binds
    fun bindsMovieGenreTableMapper(movieGenreTableMapper: MovieGenreTableMapper): Mapper<MovieDTO, List<@JvmSuppressWildcards MovieGenreTable>>

    @Binds
    fun bindsMovieTableMapper(movieTableMapper: MovieTableMapper): Mapper<MovieDTO, MovieTable>

    /** domain model mappers **/

    @Binds
    fun bindsImagePathMovieMapper(imagePathMovieMapper: ImagePathMovieMapper): Mapper<String, ImagePath>

    @Binds
    fun bindsMovieDetailsMapper(movieDetailsMapper: MovieDetailsMapper): Mapper<MovieDetailsQuery, MovieDetails>

    @Binds
    fun bindsMovieSummaryMapper(movieSummaryMapper: MovieSummaryMapper): Mapper<MovieSummaryQuery, MovieSummary>
}