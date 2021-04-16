package com.example.travellogapp

import retrofit2.Call
import retrofit2.http.GET

interface CountryService {
    @GET("all?fields=name;capital;currencies;region;population;languages;timezones;flag")

    fun getCountries(): Call<Countries>
}