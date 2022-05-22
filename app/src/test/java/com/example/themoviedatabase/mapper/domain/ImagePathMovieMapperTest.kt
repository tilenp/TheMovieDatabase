package com.example.themoviedatabase.mapper.domain

import com.example.themoviedatabase.R
import com.example.themoviedatabase.database.table.ImagePathTable
import com.example.themoviedatabase.utils.IMAGE_URL
import com.example.themoviedatabase.utils.MEDIUM
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
        val mediumImageSize = "$IMAGE_URL$MEDIUM$path"
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
        assertEquals(R.drawable.ic_broken_image, result.backup)
    }
}