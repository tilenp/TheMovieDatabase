package com.example.themoviedatabase.dagger.module

import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.database.table.BackdropImageTable
import com.example.themoviedatabase.database.table.ImagePathTable
import com.example.themoviedatabase.database.table.MovieTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.mapper.domain.ImagePathMovieMapper
import com.example.themoviedatabase.mapper.domain.MovieSummaryMapper
import com.example.themoviedatabase.mapper.table.BackdropImageTableMapper
import com.example.themoviedatabase.mapper.table.ImagePathTableMapper
import com.example.themoviedatabase.mapper.table.MovieTableMapper
import com.example.themoviedatabase.model.domain.ImagePath
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
    fun bindsImagePathTableMapper(imagePathTableMapper: ImagePathTableMapper): Mapper<MovieDTO, ImagePathTable>

    @Binds
    fun bindsMovieTableMapper(movieTableMapper: MovieTableMapper): Mapper<MovieDTO, MovieTable>

    /** domain model mappers **/

    @Binds
    fun bindsImagePathMovieMapper(imagePathMovieMapper: ImagePathMovieMapper): Mapper<ImagePathTable, ImagePath>

    @Binds
    fun bindsMovieSummaryMapper(movieSummaryMapper: MovieSummaryMapper): Mapper<MovieSummaryQuery, MovieSummary>
}