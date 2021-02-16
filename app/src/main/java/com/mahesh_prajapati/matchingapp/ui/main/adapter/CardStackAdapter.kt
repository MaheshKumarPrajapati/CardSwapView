package com.mahesh_prajapati.matchingapp.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahesh_prajapati.matchingapp.coroutines.R
import com.mahesh_prajapati.matchingapp.storage.model.ResultX
import com.mahesh_prajapati.matchingapp.ui.main.view.CardDetailsActivity
import com.mahesh_prajapati.matchingapp.utils.AppConstants

class CardStackAdapter(
    val context: Context,
    private var cardsList: List<ResultX> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cardsList[position]
        holder.name.text = "${card.name!!.title} ${card.name.first} ${card.name.last} "
        holder.city.text = "${card.location!!.city}, ${card.location!!.state}, ${card.location.country}"
        holder.age.text = "${card.dob!!.age} ${"Years"}  ${if(card.selectVal==1){"selected"}else if(card.selectVal==-1){"Not Selected"}else {""}}"
        Glide.with(holder.image)
                .load(card.picture!!.medium)
                .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            val intent = Intent(context, CardDetailsActivity::class.java)
            intent.putExtra(AppConstants.CARD_DATA, card)
            context.startActivity(intent)
            //Toast.makeText(v.context, spot.name.title, Toast.LENGTH_SHORT).show()
        }

        setCurrentItem(card)
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    fun setCards(card: List<ResultX>) {
        this.cardsList = card
    }

    fun getCards(): List<ResultX> {
        return cardsList
    }

    var card: ResultX?=null

    public fun setCurrentItem(card: ResultX) {
        this.card=card
    }

    public fun getetCurrentItem() :ResultX? {
        return card
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var city: TextView = view.findViewById(R.id.item_city)
        var image: ImageView = view.findViewById(R.id.item_image)
        var age: TextView = view.findViewById(R.id.item_age)
    }

}
