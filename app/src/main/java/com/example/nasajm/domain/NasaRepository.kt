package com.example.nasajm.domain

import com.example.nasajm.network.ApiEarthPicture
import com.example.nasajm.network.ApiMarsPhotoResponse

interface NasaRepository {

    suspend fun getPictureOfTheDay(daysFromToday: Long): RepositoryResult<PictureOfTheDay>

    suspend fun getEarthPictures(): RepositoryResult<List<ApiEarthPicture>>

    suspend fun  getMarsPictures(): RepositoryResult<ApiMarsPhotoResponse>
}