package com.example.themoviedatabase.mapper.table

import com.example.themoviedatabase.database.table.VideoTable
import com.example.themoviedatabase.model.dto.ResponseDTO
import com.example.themoviedatabase.model.dto.VideoDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class VideoTableMapperTest {

    @Test
    fun map_videos_test() {
        // arrange
        val id = 10L
        val id1 = "1"
        val id2 = "2"
        val key1 = "key1"
        val key2 = "key2"
        val videoDTOs = listOf(VideoDTO(id1, key1), VideoDTO(id2, key2))
        val responseDTO = ResponseDTO(id, videoDTOs)
        val mapper = VideoTableMapper()

        // act
        val result = mapper.map(responseDTO)

        // assert
        val videoTables = listOf(VideoTable(id1, key1, id), VideoTable(id2, key2, id))
        assertEquals(videoTables, result)
    }

    @Test
    fun map_null_video_id_test() {
        // arrange
        val id = 10L
        val videoDTOs = listOf(VideoDTO(id = null))
        val responseDTO = ResponseDTO(id, videoDTOs)
        val mapper = VideoTableMapper()

        // act
        val result = mapper.map(responseDTO)

        // assert
        val videoTables = listOf(VideoTable("", "", id))
        assertEquals(videoTables, result)
    }

    @Test
    fun map_null_video_key_test() {
        // arrange
        val id = 10L
        val videoDTOs = listOf(VideoDTO(key = null))
        val responseDTO = ResponseDTO(id, videoDTOs)
        val mapper = VideoTableMapper()

        // act
        val result = mapper.map(responseDTO)

        // assert
        val videoTables = listOf(VideoTable("", "", id))
        assertEquals(videoTables, result)
    }
}