package com.example.hopeheaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class EventHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_home)

        val createEventButton = findViewById<Button>(R.id.button)
        createEventButton.setOnClickListener {
            val intent = Intent(this, CreateEvent::class.java)
            startActivity(intent)
        }



        val upcomingEventsButton = findViewById<Button>(R.id.button7)
        upcomingEventsButton.setOnClickListener {
            val intent = Intent(this, FetchingEvents::class.java)
            startActivity(intent)
        }



    }
}