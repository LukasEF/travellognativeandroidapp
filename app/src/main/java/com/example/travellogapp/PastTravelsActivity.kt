package com.example.travellogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_pasttravels.*
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class PastTravelsActivity : AppCompatActivity() {

    val FILE_NAME : String = "travels.txt"

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasttravels)
        loadData()
    }

    private fun loadData() {
        try{
            val countriesFile = File(filesDir, FILE_NAME)
            val inStream : InputStream = countriesFile.inputStream()
            val countriesFromFile = ArrayList<String>()
            inStream.bufferedReader().forEachLine {
                countriesFromFile.add(it)
            }
            recyclerView.layoutManager = GridLayoutManager(this@PastTravelsActivity,2)
            recyclerView.adapter = PastTravelsAdapter(countriesFromFile)
        }
        catch (f : FileNotFoundException){
            Toast.makeText(this, "NO COUNTRIES HAVE BEEN SAVED", Toast.LENGTH_LONG).show()
            recyclerView.adapter = null
        }
    }

}