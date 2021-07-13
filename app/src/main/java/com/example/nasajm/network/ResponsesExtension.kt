package com.example.nasajm.network

import com.example.nasajm.BuildConfig
import com.example.nasajm.domain.PictureOfTheDay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

fun ApiPictureOfTheDay.parseToPictureOfTheDay(): PictureOfTheDay {

    return PictureOfTheDay(
        copyright = this.copyright,
        date = this.date,
        explanation = this.explanation,
        hdurl = this.hdurl,
        title = this.title,
        url = this.url
    )
}

fun List<ApiEarthPicture>.getRandomPictureUrl(): String {

    val size = this.size
    val randomPicture = Random.nextInt(0, size - 1)

    val date = LocalDateTime.parse(
        this[randomPicture].date,
        DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss")
    )
    val monthString = if (date.monthValue < 10) "0${date.monthValue}" else "${date.monthValue}"

    return "${BuildConfig.BASE_URL}EPIC/archive/natural/${date.year}/${monthString}/${date.dayOfMonth}/png/${this[randomPicture].image}.png?api_key=${BuildConfig.NASA_API_KEY}"
}

fun ApiMarsPhotoResponse.getRandomPictureUrl(): String {

    this.photos?.let {
        val randomPicture = Random.nextInt(0, this.photos.size - 1)
        return this.photos[randomPicture].image
    }

    return ""
}