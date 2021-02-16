package com.mahesh_prajapati.matchingapp.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahesh_prajapati.matchingapp.coroutines.R
import com.mahesh_prajapati.matchingapp.storage.model.ResultX
import com.mahesh_prajapati.matchingapp.ui.main.view.CardDetailsActivity
import com.mahesh_prajapati.matchingapp.utils.AppConstants
import kotlinx.android.synthetic.main.item_history_layout.view.*

class HistoryListAdaptor (
    private val cardsList: ArrayList<ResultX>,
    private val context: Context
) : RecyclerView.Adapter<HistoryListAdaptor.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            card: ResultX,
            context: Context
        ) {
            itemView.apply {
                tvPersonName.text = "${card.name!!.title} ${card.name.first} ${card.name.last} "
                tvPersonLocation.text ="${card.location!!.city}, ${card.location!!.state}, ${card.location.country}"
                var text="${card.dob!!.age} ${"Years"}"
                if(card.selectVal==1){
                    text="<font>${card.dob!!.age} ${"Years"}</font>     <font color=#00ff00>${context.getString(R.string.selected)}</font>"
                }else if(card.selectVal==-1){
                    text="<font>${card.dob!!.age} ${"Years"}</font>     <font color=#ff0000>${context.getString(R.string.rejected)}</font>"
                }
                tvPersonAge.text =(Html.fromHtml(text))
                Glide.with(ivCardImage.context)
                    .load(card.picture!!.medium)
                    .into(ivCardImage)
            }
            itemView.setOnClickListener {

                val intent = Intent(context, CardDetailsActivity::class.java)
                intent.putExtra(AppConstants.CARD_DATA, card)
                context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history_layout, parent, false))

    override fun getItemCount(): Int = cardsList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(cardsList[position],context)
    }

    fun addCards(cards: List<ResultX>) {
        this.cardsList.apply {
            clear()
            addAll(cards)
        }
    }


}