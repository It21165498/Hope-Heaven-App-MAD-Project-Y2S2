package com.example.hopeheaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

class CreateEvent : AppCompatActivity() {


    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText


    private lateinit var textInputLayout2: TextInputLayout
    private lateinit var textInputEditText2: TextInputEditText

    private lateinit var textInputLayout3: TextInputLayout
    private lateinit var textInputEditText3: TextInputEditText

    private lateinit var textInputLayout4: TextInputLayout
    private lateinit var textInputEditText4: TextInputEditText

    private lateinit var editTextTextMultiLine2:EditText


    private lateinit var b6: Button


    private lateinit var dbRef: DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_event)

        textInputLayout = findViewById(R.id.tilTopic)
        textInputEditText= findViewById(R.id.textInputEditText)
        //textInputLayout.editText = textInputEditText

        textInputLayout2 = findViewById(R.id.tilLocation)
        textInputEditText2 = findViewById(R.id.textInputEditText2)
        //textInputLayout2.editText = textInputEditText2


        textInputLayout3 = findViewById(R.id.tilDate)
        textInputEditText3 = findViewById(R.id.textInputEditText3)
       // textInputLayout3.editText = textInputEditText3


        textInputLayout4 = findViewById(R.id.tilTime)
        textInputEditText4 = findViewById(R.id.textInputEditText4)
        //textInputLayout4.editText = textInputEditText4

        editTextTextMultiLine2 = findViewById(R.id.etDescription)

        b6 = findViewById(R.id.submitUpdate)



        dbRef = FirebaseDatabase.getInstance().getReference("Events")

        b6.setOnClickListener{
            saveEventData()
        }


    }

    private fun saveEventData(){

        //getting values
        val topic = textInputEditText.text.toString()
        val location = textInputEditText2.text.toString()
        val date = textInputEditText3.text.toString()
        val time = textInputEditText4.text.toString()
        val description = editTextTextMultiLine2.text.toString()


        if (topic.isEmpty()){
            textInputEditText.error = "Please enter the topic of event"
            return
        }

        if (location.isEmpty()){
            textInputEditText2.error = "Please enter the location of event"
            return
        }

        if (date.isEmpty()){
            textInputEditText3.error = "Please enter the date of event"
            return
        }

        if (time.isEmpty()){
            textInputEditText4.error = "Please enter the time of event"
            return
        }

        if (description.isEmpty()){
            editTextTextMultiLine2.error = "Please enter the description of event"
            return
        }


        val eventID = dbRef.push().key!!

        val event = EventModel(eventID, topic, location, date, time, description)

        dbRef.child(eventID).setValue(event)
            .addOnCompleteListener{
                Toast.makeText(this,"Event created successfully",Toast.LENGTH_LONG).show()

                textInputEditText.text?.clear()
                textInputEditText2.text?.clear()
                textInputEditText3.text?.clear()
                textInputEditText4.text?.clear()
                editTextTextMultiLine2.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }


    }
}