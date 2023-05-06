package com.example.hopeheaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class FetchingEvents : AppCompatActivity() {

    private lateinit var ueRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var upEventArrayList: ArrayList<EventModel>
    private lateinit var dbRef: DatabaseReference

    private lateinit var etSearch : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching_events)

        ueRecyclerView = findViewById(R.id.upevents)
        ueRecyclerView.layoutManager = LinearLayoutManager(this)
        ueRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        upEventArrayList = arrayListOf<EventModel>()

        etSearch = findViewById(R.id.etSearch)

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                filterData(query)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })



        getEvents()


    }

    private fun getEvents() {
        ueRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Events")

        dbRef.addValueEventListener(object : ValueEventListener{


            override fun onDataChange(snapshot: DataSnapshot) {
                upEventArrayList.clear()

                if(snapshot.exists()){

                    for(eventSnap in snapshot.children){

                        val eventData = eventSnap.getValue(EventModel::class.java)
                        upEventArrayList.add(eventData!!)
                    }


                    val ueAdapter = UpcomingEventsAdapter(upEventArrayList)
                    ueRecyclerView.adapter=ueAdapter

                    ueAdapter.setOnItemClickListener(object : UpcomingEventsAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingEvents, Event::class.java)


                            intent.putExtra("eventID", upEventArrayList[position].eventID)
                            intent.putExtra("location", upEventArrayList[position].location)
                            intent.putExtra("date", upEventArrayList[position].date)
                            intent.putExtra("time", upEventArrayList[position].time)
                            intent.putExtra("description", upEventArrayList[position].description)
                            startActivity(intent)

                        }

                    })

                    ueRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }

    private fun filterData(query: String?) {
        val filteredList = ArrayList<EventModel>()

        for (event in upEventArrayList) {
            if (event.topic?.lowercase(Locale.getDefault())?.contains(
                    query?.lowercase(Locale.getDefault()) ?: "") == true) {
                filteredList.add(event)
            }
        }

        val ueAdapter = UpcomingEventsAdapter(filteredList)
        ueRecyclerView.adapter = ueAdapter

        ueAdapter.setOnItemClickListener(object : UpcomingEventsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@FetchingEvents, Event::class.java)
                intent.putExtra("eventID", filteredList[position].eventID)
                intent.putExtra("location", filteredList[position].location)
                intent.putExtra("date", filteredList[position].date)
                intent.putExtra("time", filteredList[position].time)
                intent.putExtra("description", filteredList[position].description)
                startActivity(intent)
            }
        })
    }


}
