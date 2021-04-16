package com.example.travellogapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_pasttravels.*
import kotlinx.android.synthetic.main.activity_photos.*
import kotlinx.android.synthetic.main.activity_photos.recyclerView
import java.io.File
import java.lang.Exception

class ViewPhotos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        val name = intent.getStringExtra("EXTRA_NAME")
        loadData(name)
    }

    private fun loadData(countryName: String?){
        try{
            val imagesFromFile = ArrayList<Uri>()
            val imageDir = externalMediaDirs.firstOrNull()?.let {
                File(it, countryName)
            }
            for (f in imageDir?.listFiles()!!) {
                if (f.isFile) {
                    val savedUri = Uri.fromFile(f)
                    imagesFromFile.add(savedUri)
                }
            }
            recyclerView.layoutManager = GridLayoutManager(this@ViewPhotos,2)
            recyclerView.adapter = PhotoAdapter(imagesFromFile)
        }
        catch(e : Exception){
            Toast.makeText(this, "No photos saved", Toast.LENGTH_SHORT).show()
        }

    }

}