package com.example.themoviedatabase.mapper.table

import com.example.themoviedatabase.model.dto.MovieDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class ImagePathTableMapperTest {

    @Test
    fun map_path_test() {
        // arrange
        val path = "path"
        val movieDto = MovieDTO(posterPath = path)
        val mapper = ImagePathTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(path, result.path)
    }

    @Test
    fun map_null_path_test() {
        // arrange
        val movieDto = MovieDTO(posterPath = null)
        val mapper = ImagePathTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals("", result.path)
    }

    @Test
    fun map_id_test() {
        // arrange
        val id = 1L
        val movieDto = MovieDTO(id = id)
        val mapper = ImagePathTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        assertEquals(id, result.itemId)
    }
}