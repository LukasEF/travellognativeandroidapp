package com.example.travellogapp

import android.content.Intent
import kotlinx.android.synthetic.main.country_layout.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CountryAdapter(private val countries: Countries) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.country_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theCountry = countries.get(position)
        holder.countryName.text = theCountry.name
        holder.countryCapital.text = "Capital: " + theCountry.capital
        holder.countryRegion.text = "Region: " + theCountry.region
        Glide.with(holder.itemView.context).load(theCountry.flag).into(holder.flag)
        holder.flag.setOnClickListener{viewCountry(holder, theCountry)}
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val countryName = view.countryName
        val countryCapital = view.countryCapital
        val countryRegion = view.countryRegion
        val flag = view.imgFlag
    }

    private fun viewCountry(holder: ViewHolder, country: Countries.CountriesItem){
        val detailsIntent: Intent = Intent(holder.itemView.context, CountryDetails::class.java).apply{
            putExtra("EXTRA_COUNTRYNAME", country.name)
            putExtra("EXTRA_FLAG", country.flag)
            putExtra("EXTRA_CAPITAL", country.capital)
            putExtra("EXTRA_REGION", country.region)
            putExtra("EXTRA_POPULATION", country.population)
            putExtra("EXTRA_CURRENCIES", country.currencies.get(0).symbol)
            putExtra("EXTRA_LANGUAGES", country.languages.get(0).name)
            putExtra("EXTRA_TIMEZONES", country.timezones.get(0))
        }
        holder.itemView.context.startActivity(detailsIntent)
    }


}
