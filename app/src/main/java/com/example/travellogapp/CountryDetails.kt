package com.example.travellogapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_country_details.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStreamWriter
import java.lang.Exception

class CountryDetails : AppCompatActivity(){

    val FILE_NAME : String = "travels.txt"
    val RATINGS_FILE : String = "ratings.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)
        try {
            val name = intent.getStringExtra("EXTRA_COUNTRYNAME")
            countryName.text = "$name"
            val flag = intent.getStringExtra("EXTRA_FLAG")
            Glide.with(this).load(flag).into(imgFlag)
            val capital = intent.getStringExtra("EXTRA_CAPITAL")
            countryCapital.text = "Capital: $capital"
            val region = intent.getStringExtra("EXTRA_REGION")
            countryRegion.text = "Region: $region"
            val population = intent.getIntExtra("EXTRA_POPULATION", 0)
            countryPopulation.text = "Population: $population"
            val currency = intent.getStringExtra("EXTRA_CURRENCIES")
            countryCurrencies.text = "Currency: $currency"
            val language = intent.getStringExtra("EXTRA_LANGUAGES")
            countryLanguages.text = "Language: $language"
            val timezone = intent.getStringExtra("EXTRA_TIMEZONES")
            countryTimezones.text = "Timezone: $timezone"

            val c = Country(capital!!,
                currency!!,
                flag!!,
                language!!,
                name!!,
                population,
                region!!,
                timezone!!)
            setStars(readRating(c))
            btnAPI.setOnClickListener{saveCountry(c)}
            btn_wiki.setOnClickListener{openWiki(c)}
            btn_star1.setOnClickListener{rateCountry(btn_star1, c)}
            btn_star2.setOnClickListener{rateCountry(btn_star2, c)}
            btn_star3.setOnClickListener{rateCountry(btn_star3, c)}
            btn_star4.setOnClickListener{rateCountry(btn_star4, c)}
            btn_star5.setOnClickListener{rateCountry(btn_star5, c)}
        }
        catch (e : Exception){
            val errorIntent : Intent = Intent(this, MainActivity::class.java)
            startActivity(errorIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.country_details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val name = intent.getStringExtra("EXTRA_COUNTRYNAME")
        return when (item.itemId) {
            R.id.take_photo -> {
                takePhoto(name)
                true
            }
            R.id.view_photos ->{
                viewPhotos(name)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun takePhoto(name : String?){
        val toCameraIntent: Intent = Intent(this, TakePhoto::class.java)
            .putExtra("EXTRA_NAME", name)
        startActivity(toCameraIntent)
    }

    private fun viewPhotos(name : String?){
        val toPhotosIntent: Intent = Intent(this, ViewPhotos::class.java)
            .putExtra("EXTRA_NAME", name)
        startActivity(toPhotosIntent)
    }

    private fun saveCountry(c: Country){
        val countriesFile = File(filesDir, FILE_NAME)
        var countrySaved = false
        if(countriesFile.exists()){
            val countryToCheck = Gson().toJson(c)
            val inStream : InputStream = countriesFile.inputStream()
            inStream.bufferedReader().forEachLine {
                val line = it
                if(countryToCheck == line) {
                    Toast.makeText(this, "Country already marked as travelled", Toast.LENGTH_SHORT).show()
                    countrySaved = true
                }
            }
        }
        if(!countrySaved){
            val fileOutStream : FileOutputStream = openFileOutput(FILE_NAME, MODE_APPEND)
            val countryWriter = OutputStreamWriter(fileOutStream)
            val saveCountry = Gson().toJson(c)
            countryWriter.write(saveCountry)
            countryWriter.write("\n")
            countryWriter.close()
            Toast.makeText(this, "Country marked as travelled \n Saved to $filesDir", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openWiki(c: Country){
        val url: String = "https://wikipedia.org/wiki/" + c.name
        val intent: Intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
         startActivity(intent)
    }

    //Problem with this function is that it constantly appends new ratings to the text file instead of replacing the old one try and fix this once the whole app is finished
    private fun rateCountry(star: ImageButton, c: Country){
        var countryRating = 0

        when (star) {
            btn_star1 -> {
                setStars(1)
                countryRating = 1
            }
            btn_star2 -> {
                setStars(2)
                countryRating = 2
            }
            btn_star3 -> {
                setStars(3)
                countryRating = 3
            }
            btn_star4 -> {
                setStars(4)
                countryRating = 4
            }
            btn_star5 -> {
                setStars(5)
                countryRating = 5
            }
        }

        val fileOutStream : FileOutputStream = openFileOutput(RATINGS_FILE, MODE_APPEND)
        val ratingsWriter = OutputStreamWriter(fileOutStream)
        val ratingsLine : String = "${c.name}: $countryRating"
        ratingsWriter.write(ratingsLine)
        ratingsWriter.write("\n")
        ratingsWriter.close()
    }

    private fun readRating(c : Country) : Int {
        var rating : Int = 0
        val ratingsFile = File(filesDir, RATINGS_FILE)
        if(ratingsFile.exists()){
            val inStream : InputStream = ratingsFile.inputStream()
            inStream.bufferedReader().forEachLine {
                if(it.contains(c.name)){
                    val vals: List<String> = it.split(": ")
                    rating = vals[1].toInt()
                }
            }
            inStream.close()
        }
        else{
            rating = 0
        }

        return rating
    }

    fun setStars(stars : Int){
        when (stars) {
            1 -> {
                btn_star1.setImageResource(R.drawable.stargold)
                btn_star2.setImageResource(R.drawable.star)
                btn_star3.setImageResource(R.drawable.star)
                btn_star4.setImageResource(R.drawable.star)
                btn_star5.setImageResource(R.drawable.star)
            }
            2 -> {
                btn_star1.setImageResource(R.drawable.stargold)
                btn_star2.setImageResource(R.drawable.stargold)
                btn_star3.setImageResource(R.drawable.star)
                btn_star4.setImageResource(R.drawable.star)
                btn_star5.setImageResource(R.drawable.star)
            }
            3 -> {
                btn_star1.setImageResource(R.drawable.stargold)
                btn_star2.setImageResource(R.drawable.stargold)
                btn_star3.setImageResource(R.drawable.stargold)
                btn_star4.setImageResource(R.drawable.star)
                btn_star5.setImageResource(R.drawable.star)
            }
            4 -> {
                btn_star1.setImageResource(R.drawable.stargold)
                btn_star2.setImageResource(R.drawable.stargold)
                btn_star3.setImageResource(R.drawable.stargold)
                btn_star4.setImageResource(R.drawable.stargold)
                btn_star5.setImageResource(R.drawable.star)
            }
            5 -> {
                btn_star1.setImageResource(R.drawable.stargold)
                btn_star2.setImageResource(R.drawable.stargold)
                btn_star3.setImageResource(R.drawable.stargold)
                btn_star4.setImageResource(R.drawable.stargold)
                btn_star5.setImageResource(R.drawable.stargold)
            }
        }
    }

}