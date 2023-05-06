package com.example.hopeheaven.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hopeheaven.models.FeedbackModel
import com.example.hopeheaven.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
    private lateinit var etFullName: EditText
    private lateinit var etType: EditText
    private lateinit var etAdditional: EditText


    private lateinit var btnSave: Button

    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etFullName= findViewById(R.id.etFullName)
        etType= findViewById(R.id.etType)
        etAdditional= findViewById(R.id.etAdditional)

        btnSave= findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("FeedBack")

        btnSave.setOnClickListener{
            saveTaskData()
        }
    }

    private fun saveTaskData() {
        //geting values
        val empFullName = etFullName.text.toString()
        val empType = etType.text.toString()
        val empAddtional = etAdditional.text.toString()


        if(empFullName.isEmpty()){
            etFullName.error="Please enter Full Name"
        }
        if(empType.isEmpty()){
            etType.error="Please enter donation type"
        }
        if(empAddtional.isEmpty()){
            etAdditional.error="Please enter additional information"
        }



        val empId = dbRef.push().key!!

        val task= FeedbackModel(empId,empFullName,empType,empAddtional)

        dbRef.child(empId).setValue(task)
            .addOnCompleteListener{
                Toast.makeText(this,"Data insert successfully", Toast.LENGTH_LONG).show()
                etFullName.text.clear()
                etType.text.clear()
                etAdditional.text.clear()




            }.addOnFailureListener { err->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

}