package com.example.travellogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_countries.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CountriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)
        loadData()
    }

    private fun loadData() {
        val service = ServiceBuilder.buildService(CountryService::class.java)
        val requestCall = service.getCountries()

        requestCall.enqueue(object : Callback<Countries> {
            override fun onResponse(
                call: Call<Countries>,
                response: Response<Countries>
            ) {
                if (response.isSuccessful) {
                    recyclerView.layoutManager = GridLayoutManager(this@CountriesActivity,2)
                    recyclerView.adapter = CountryAdapter(response.body()!!)

                } else {
                    AlertDialog.Builder(this@CountriesActivity)
                        .setTitle("API error")
                        .setMessage("Response, but something went wrong ${response.message()}")
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }
            }

            override fun onFailure(call: Call<Countries>, t: Throwable) {
                AlertDialog.Builder(this@CountriesActivity)
                    .setTitle("API error")
                    .setMessage("No response, and something went wrong $t")
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }
        })
    }
}
