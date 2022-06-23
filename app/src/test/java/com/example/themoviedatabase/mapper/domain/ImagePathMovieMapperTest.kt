package com.example.themoviedatabase.mapper.domain

import com.example.themoviedatabase.BuildConfig.TMDB_IMAGE_URL
import com.example.themoviedatabase.R
import com.example.themoviedatabase.model.domain.ImagePath.Companion.SIZE_500
import org.junit.Assert.assertEquals
import org.junit.Test

class ImagePathMovieMapperTest {

    @Test
    fun map_path_test() {
        // arrange
        val path = "path"
        val mapper = ImagePathMovieMapper()

        // act
        val result = mapper.map(path)

        // assert
        val mediumImageSize ="${TMDB_IMAGE_URL}${SIZE_500}$path"
        assertEquals(mediumImageSize, result.medium)
    }

    @Test
    fun map_placeholder_test() {
        // arrange
        val mapper = ImagePathMovieMapper()

        // act
        val result = mapper.map("")

        // assert
        assertEquals(R.drawable.ic_photo, result.placeholder)
    }

    @Test
    fun map_backup_test() {
        // arrange
        val mapper = ImagePathMovieMapper()

        // act
        val result = mapper.map("")

        // assert
        assertEquals(R.drawable.ic_broken_image, result.resourceId)
    }
}