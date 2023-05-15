package com.example.hopeheaven

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DonationAdapter(private val donationList:ArrayList<DonationModel>):RecyclerView.Adapter<DonationAdapter.donationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): donationViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.donation_list,
        parent,false)
        return donationViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return donationList.size
    }

    override fun onBindViewHolder(holder: donationViewHolder, position: Int) {
      val currentItem=donationList[position]
        holder.donorName.text=currentItem.name
        holder.donorEmail.text=currentItem.email
        holder.donationCategory .text=currentItem.category
        holder.quantity .text=currentItem.qty
        holder.pickupLocation.text=currentItem.location
        holder.pickupDate.text=currentItem.date


    }



    class donationViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val donorName: TextView = itemView.findViewById(R.id.dnrName)
        val donorEmail: TextView = itemView.findViewById(R.id.dnrEmail)
        val donationCategory: TextView = itemView.findViewById(R.id.dnrCat)
        val quantity: TextView = itemView.findViewById(R.id.dnrQty)
        val pickupLocation: TextView = itemView.findViewById(R.id.dnrPickUploc)
        val pickupDate: TextView = itemView.findViewById(R.id.dnrPickupDate)


    }
}