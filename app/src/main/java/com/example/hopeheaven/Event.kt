package com.example.hopeheaven

import android.content.ClipDescription
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class Event : AppCompatActivity() {


    private lateinit var eventID : TextView
    private lateinit var location : TextView
    private lateinit var date : TextView
    private lateinit var time : TextView
    private lateinit var description : TextView

    private lateinit var tvStudent : TextView
    private lateinit var tvDonor: TextView


    var studentCount = 0
    var donorCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event)



        val studentCounterButton = findViewById<Button>(R.id.studentCounter)
        studentCounterButton.setOnClickListener {

                studentCount++

                tvStudent = findViewById(R.id.tvStudent)
                tvStudent.text = studentCount.toString()

        }

        val donorCounterButton = findViewById<Button>(R.id.donorCounter)
        donorCounterButton.setOnClickListener {

                donorCount++

                tvDonor = findViewById(R.id.tvDonor)
                tvDonor.text = donorCount.toString()

        }




        val updateButton = findViewById<Button>(R.id.editButton)
        updateButton.setOnClickListener {
            openUpdateForm(
                intent.getStringExtra("eventID").toString(),
                intent.getStringExtra("topic").toString(),

            )
        }

        val deleteButton = findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            removeEvent(
                intent.getStringExtra("eventID").toString()
                )
        }






        setValuesToViews()
    }

    private fun removeEvent(eventID: String) {

        val dbRef = FirebaseDatabase.getInstance().getReference("Events").child(eventID)
        val removeTask = dbRef.removeValue()

        removeTask.addOnSuccessListener {

            Toast.makeText(this,"Event removed successfully", Toast.LENGTH_LONG).show()


            val intent = Intent(this, FetchingEvents::class.java )
            finish()
            startActivity(intent)

        }.addOnFailureListener{ error ->

            Toast.makeText(this,"Event removing error ${error.message}", Toast.LENGTH_LONG).show()

        }

    }


    private fun setValuesToViews(){
        eventID = findViewById(R.id.eventId)
        location = findViewById(R.id.eventLocation)
        date = findViewById(R.id.eventDate)
        time = findViewById(R.id.eventTime)
        description = findViewById(R.id.eventDescription)

        eventID.text=intent.getStringExtra("eventID")
        location.text=intent.getStringExtra("location")
        date.text=intent.getStringExtra("date")
        time.text=intent.getStringExtra("time")
        description.text=intent.getStringExtra("description")
    }

    private fun openUpdateForm(eventID: String, location: String) {

        val updateDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val updateDialogView = inflater.inflate(R.layout.edit_event,null)

        updateDialog.setView(updateDialogView)

        val etLocation = updateDialogView.findViewById<EditText>(R.id.etLocation)
        val etDate = updateDialogView.findViewById<EditText>(R.id.etDate)
        val etTime = updateDialogView.findViewById<EditText>(R.id.etTime)
        val etDescription = updateDialogView.findViewById<EditText>(R.id.etDescription)
        val buttonUpdate = updateDialogView.findViewById<Button>(R.id.submitUpdate)

        etLocation.setText(intent.getStringExtra("location").toString())
        etDate.setText(intent.getStringExtra("date").toString())
        etTime.setText(intent.getStringExtra("time").toString())
        etDescription.setText(intent.getStringExtra("description").toString())

        updateDialog.setTitle("Updating $eventID record")

        val alertDialog = updateDialog.create()
        alertDialog.show()

        buttonUpdate.setOnClickListener{
            updateEventData(
                eventID,
                etLocation.text.toString(),
                etDate.text.toString(),
                etTime.text.toString(),
                etDescription.text.toString()


                )


            Toast.makeText(applicationContext, "Event Data Updated", Toast.LENGTH_LONG).show()


            date.text=intent.getStringExtra("date")
            time.text=intent.getStringExtra("time")
            description.text=intent.getStringExtra("description")

            alertDialog.dismiss()


        }

    }

    private fun updateEventData(
        eventID: String,
        location: String,
        date: String,
        time: String,
        description: String) {


        val dbRef = FirebaseDatabase.getInstance().getReference("Events").child(eventID)
        val eventInfo = EventModel(eventID,  location,  date, time, description)
        dbRef.setValue(eventInfo)

    }







}