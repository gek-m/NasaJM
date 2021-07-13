package com.example.nasajm.domain

import com.example.nasajm.BuildConfig
import com.example.nasajm.network.ApiEarthPicture
import com.example.nasajm.network.ApiMarsPhotoResponse
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

    private val gson = Gson()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val mapApi: NasaMapApi = retrofit.create(NasaMapApi::class.java)


    override suspend fun getPictureOfTheDay(daysFromToday: Long): RepositoryResult<PictureOfTheDay> {

        try {
            val pictureMapResponse =
                mapApi.getPictureOfTheDaySuspend(setDateInString(daysFromToday, API_DATE_PATTERN))
            return Success(pictureMapResponse.parseToPictureOfTheDay())

        } catch (ex: Exception) {
            return Error(ex)
        }
    }

    override suspend fun getEarthPictures(): RepositoryResult<List<ApiEarthPicture>> {

        try {
            val earthPicturesList = mapApi.getEarthPhotoListSuspend()
            return  Success(earthPicturesList)
        } catch (ex: Exception){
            return  Error(ex)
        }
    }

    override suspend fun getMarsPictures(): RepositoryResult<ApiMarsPhotoResponse> {
        try {
            val marsPhoto = mapApi.getMarsPhotoListSuspend()
            return  Success(marsPhoto)
        } catch (ex: Exception){
            return  Error(ex)
        }
    }
}