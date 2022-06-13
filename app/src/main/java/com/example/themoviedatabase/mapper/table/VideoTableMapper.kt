package com.example.themoviedatabase.mapper.table

import com.example.themoviedatabase.database.table.VideoTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.dto.ResponseDTO
import com.example.themoviedatabase.model.dto.VideoDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoTableMapper @Inject constructor() : Mapper<ResponseDTO<VideoDTO>, List<@JvmSuppressWildcards VideoTable>> {

    override fun map(objectToMap: ResponseDTO<VideoDTO>): List<VideoTable> {
        return objectToMap.results?.map { videoDto ->  mapVideo(objectToMap.id, videoDto) } ?: emptyList()
    }

    private fun mapVideo(id: Long?, videoDTO: VideoDTO): VideoTable {
        return VideoTable(
            id = videoDTO.id.orEmpty(),
            key = videoDTO.key.orEmpty(),
            itemId = id ?: 0L
        )
    }
}