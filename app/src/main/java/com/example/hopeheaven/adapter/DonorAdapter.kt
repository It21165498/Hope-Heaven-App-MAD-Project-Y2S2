package com.example.hopeheaven.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hopeheaven.R
import com.example.hopeheaven.models.DonorModel

class DonorAdapter : RecyclerView.Adapter<DonorAdapter.DonorViewHolder>() {

    private val donorlist= ArrayList<DonorModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorViewHolder {
        val donorView = LayoutInflater.from(parent.context).inflate(
            R.layout.donation_list,
            parent,false
        )
        return DonorViewHolder(donorView)
    }

    override fun getItemCount(): Int {
        return donorlist.size
    }

    override fun onBindViewHolder(holder: DonorViewHolder, position: Int) {
        val currentDonor = donorlist[position]

        holder.name.text = currentDonor.name
        holder.email.text=currentDonor.email
        holder.age.text = currentDonor.age.toString()
        holder.from.text=currentDonor.from
    }

    fun updateDonorList(donorlist:List<DonorModel>){
        this.donorlist.clear()
        this.donorlist.addAll(donorlist)
        notifyDataSetChanged()
    }

    class DonorViewHolder(donorView: View):RecyclerView.ViewHolder(donorView){

        val name: TextView =donorView.findViewById(R.id.donorName)
        val email: TextView =donorView.findViewById(R.id.donorEmail)
        val from: TextView =donorView.findViewById(R.id.donorAddr)
        val age: TextView =donorView.findViewById(R.id.donorAge)

    }
}