package com.mahesh_prajapati.itunes.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahesh_prajapati.itunes.coroutines.R
import com.mahesh_prajapati.itunes.storage.model.ResultX
import com.mahesh_prajapati.itunes.ui.main.view.CardActivity
import com.mahesh_prajapati.itunes.ui.main.view.CardDetailsActivity
import com.mahesh_prajapati.itunes.utils.AppConstants

class CardStackAdapter(
    val context: Context,
    private var spots: List<ResultX> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_spot, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = spots[position]
        holder.name.text = "${spot.name!!.title} ${spot.name.first} ${spot.name.last} "
        holder.city.text = "${spot.location!!.city}, ${spot.location!!.state}, ${spot.location.country}"
        holder.age.text = "${spot.dob!!.age} ${"Years"}  ${if(spot.selectVal==1){"selected"}else if(spot.selectVal==-1){"Not Selected"}else {""}}"
        Glide.with(holder.image)
                .load(spot.picture!!.medium)
                .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            val intent = Intent(context, CardDetailsActivity::class.java)
            intent.putExtra(AppConstants.CARD_DATA, spot)
            context.startActivity(intent)
            //Toast.makeText(v.context, spot.name.title, Toast.LENGTH_SHORT).show()
        }

        setCurrentItem(spot)
    }

    override fun getItemCount(): Int {
        return spots.size
    }

    fun setSpots(spots: List<ResultX>) {
        this.spots = spots
    }

    fun getSpots(): List<ResultX> {
        return spots
    }

    var songs: ResultX?=null

    public fun setCurrentItem(songs: ResultX) {
        this.songs=songs
    }

    public fun getetCurrentItem() :ResultX? {
        return songs
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var city: TextView = view.findViewById(R.id.item_city)
        var image: ImageView = view.findViewById(R.id.item_image)
        var age: TextView = view.findViewById(R.id.item_age)
    }

}
