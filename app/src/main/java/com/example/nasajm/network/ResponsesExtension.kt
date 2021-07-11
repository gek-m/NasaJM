package com.example.nasajm.network

import com.example.nasajm.domain.PictureOfTheDay

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