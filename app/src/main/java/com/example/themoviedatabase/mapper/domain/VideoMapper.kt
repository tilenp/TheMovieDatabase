package com.example.themoviedatabase.mapper.domain

import com.example.themoviedatabase.database.table.VideoTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.domain.Video
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoMapper @Inject constructor() : Mapper<VideoTable, Video> {

    override fun map(objectToMap: VideoTable): Video {
        return Video(
            id = objectToMap.id,
            key = objectToMap.key
        )
    }
}