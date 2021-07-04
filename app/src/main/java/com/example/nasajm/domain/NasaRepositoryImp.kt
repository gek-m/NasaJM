package com.example.nasajm.domain

import com.example.nasajm.BuildConfig
import com.example.nasajm.network.NasaMapApi
import com.example.nasajm.network.parseToPictureOfTheDay
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NasaRepositoryImp() : NasaRepository {

    override suspend fun getPictureOfTheDay(daysFromToday: Long): RepositoryResult<PictureOfTheDay> {
        val gson = Gson()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val mapApi: NasaMapApi = retrofit.create(NasaMapApi::class.java)

        try {
            val current =
                LocalDateTime.now().minusDays(daysFromToday)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            val pictureMapResponse = mapApi.getPictureOfTheDaySuspend(current)
            return Success(pictureMapResponse.parseToPictureOfTheDay())

        } catch (ex: Exception) {
            return Error(ex)
        }
    }
}