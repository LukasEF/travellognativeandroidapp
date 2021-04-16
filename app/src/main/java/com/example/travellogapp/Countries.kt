package com.example.travellogapp

import com.google.gson.annotations.SerializedName

class Countries() : ArrayList<Countries.CountriesItem>(){
    data class CountriesItem(
        val capital: String,
        val currencies: List<Currency>,
        val flag: String,
        val languages: List<Language>,
        val name: String,
        val population: Int,
        val region: String,
        val timezones: List<String>
    ) {
        data class Currency(
            val code: String,
            val name: String,
            val symbol: String?
        )
    
        data class Language(
            @SerializedName("iso639_1")
            val iso6391: String,
            @SerializedName("iso639_2")
            val iso6392: String,
            val name: String,
            val nativeName: String
        )
    }
}