package com.example.nasajm.domain

interface NasaRepository {

    suspend fun getPictureOfTheDay(daysFromToday: Long): RepositoryResult<PictureOfTheDay>
}