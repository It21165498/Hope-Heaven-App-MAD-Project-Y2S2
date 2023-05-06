package com.example.hopeheaven

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UpcomingEventsAdapter(private val upEventList : ArrayList<EventModel>) :
    RecyclerView.Adapter<UpcomingEventsAdapter.ViewHolder>(){

    private lateinit var mListener : onItemClickListener


    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }


    override fun onCreateViewHolder(  parent: ViewGroup,  viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_list_item, parent, false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

        val currentEvent = upEventList[position]

        holder.topic.text = currentEvent.topic

    }



    override fun getItemCount(): Int {
        return upEventList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val topic : TextView = itemView.findViewById(R.id.eventTopic)

        init{
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }


    }


}