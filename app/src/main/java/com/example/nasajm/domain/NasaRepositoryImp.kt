package com.example.nasajm.domain

import com.example.nasajm.BuildConfig
import com.example.nasajm.network.NasaMapApi
import com.example.nasajm.network.parseToPictureOfTheDay
import com.example.nasajm.util.setDateInString
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaRepositoryImp() : NasaRepository {

    companion object {
        const val API_DATE_PATTERN = "yyyy-MM-dd"
    }

    override suspend fun getPictureOfTheDay(daysFromToday: Long): RepositoryResult<PictureOfTheDay> {
        val gson = Gson()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val mapApi: NasaMapApi = retrofit.create(NasaMapApi::class.java)

        try {
            val pictureMapResponse =
                mapApi.getPictureOfTheDaySuspend(setDateInString(daysFromToday, API_DATE_PATTERN))
            return Success(pictureMapResponse.parseToPictureOfTheDay())

        } catch (ex: Exception) {
            return Error(ex)
        }
    }
}