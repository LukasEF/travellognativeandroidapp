package com.example.travellogapp

import android.net.Uri
import kotlinx.android.synthetic.main.photo_layout.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PhotoAdapter(private val images: ArrayList<Uri>) :
    RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return images.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uri = images.get(position)
        holder.image.setImageURI(uri)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val image = view.imgPicture
    }


}