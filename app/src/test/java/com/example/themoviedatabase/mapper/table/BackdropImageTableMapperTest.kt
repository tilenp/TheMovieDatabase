package com.example.themoviedatabase.mapper.table

import com.example.themoviedatabase.model.dto.MovieDTO
import org.junit.Assert
import org.junit.Test

class BackdropImageTableMapperTest {

    @Test
    fun map_path_test() {
        // arrange
        val path = "path"
        val movieDto = MovieDTO(backdropPath = path)
        val mapper = BackdropImageTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        Assert.assertEquals(path, result.path)
    }

    @Test
    fun map_null_path_test() {
        // arrange
        val movieDto = MovieDTO(posterPath = null)
        val mapper = BackdropImageTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        Assert.assertEquals("", result.path)
    }

    @Test
    fun map_id_test() {
        // arrange
        val id = 1L
        val movieDto = MovieDTO(id = id)
        val mapper = BackdropImageTableMapper()

        // act
        val result = mapper.map(movieDto)

        // assert
        Assert.assertEquals(id, result.itemId)
    }
}