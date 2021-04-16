package com.example.travellogapp

import android.content.Intent
import kotlinx.android.synthetic.main.country_layout.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.io.Serializable

class PastTravelsAdapter(private val countries: ArrayList<String>) :
    RecyclerView.Adapter<PastTravelsAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.country_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theCountryString = countries.get(position)
        val c : Country = Gson().fromJson(theCountryString, Country::class.java)
        holder.countryName.text = c.name
        holder.countryCapital.text = "Capital: " + c.capital
        holder.countryRegion.text = "Region: " + c.region
        Glide.with(holder.itemView.context).load(c.flag).into(holder.flag)
        holder.flag.setOnClickListener{viewCountry(holder, c)}
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val countryName = view.countryName
        val countryCapital = view.countryCapital
        val countryRegion = view.countryRegion
        val flag = view.imgFlag
    }

    private fun viewCountry(holder: ViewHolder, country: Country){
        val detailsIntent: Intent = Intent(holder.itemView.context, CountryDetails::class.java).apply{
            putExtra("EXTRA_COUNTRYNAME", country.name)
            putExtra("EXTRA_FLAG", country.flag)
            putExtra("EXTRA_CAPITAL", country.capital)
            putExtra("EXTRA_REGION", country.region)
            putExtra("EXTRA_POPULATION", country.population)
            putExtra("EXTRA_CURRENCIES", country.currency)
            putExtra("EXTRA_LANGUAGES", country.language)
            putExtra("EXTRA_TIMEZONES", country.timezone)
        }
        holder.itemView.context.startActivity(detailsIntent)
    }


}
