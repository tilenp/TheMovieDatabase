package com.example.themoviedatabase.mapper

interface Mapper<A, B> {
    fun map(objectToMap: A): B
}