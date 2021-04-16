package com.example.travellogapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_startCountriesActivity.setOnClickListener{startCountriesActivity()}
        btn_startPastTravelsActivity.setOnClickListener{startPastTravelsActivity()}
        btn_darkMode.setOnClickListener{goDark()}
        btn_lightMode.setOnClickListener{goLight()}
    }

    fun startCountriesActivity() {
        val countriesIntent: Intent = Intent(this, CountriesActivity::class.java)
        startActivity(countriesIntent)
    }

    fun startPastTravelsActivity() {
        val pastTravelsIntent: Intent = Intent(this, PastTravelsActivity::class.java)
        startActivity(pastTravelsIntent)
    }

    fun goDark() {
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun goLight() {
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO)
    }

}