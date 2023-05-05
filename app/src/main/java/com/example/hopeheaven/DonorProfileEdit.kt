package com.example.hopeheaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DonorProfileEdit : AppCompatActivity()  {

    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextFrom: EditText
    private lateinit var updateButton: Button
    private lateinit var cancelButton: Button

    private val user = FirebaseAuth.getInstance().currentUser
    private val firestoreDatabase = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_profile_edit)

        editTextName = findViewById(R.id.editTextTextPersonName)
        editTextAge = findViewById(R.id.editTextTextPersonName4)
        editTextEmail = findViewById(R.id.editTextTextPostalAddress)
        editTextFrom = findViewById(R.id.editTextTextPersonName2)
        updateButton = findViewById(R.id.btnDonate)
        cancelButton=  findViewById(R.id.btnCancel)

        updateButton.setOnClickListener {
            updateUserData()
        }

        firestoreDatabase.collection("Users").document(user?.uid.toString()).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Get the data from the document snapshot
                    val name = documentSnapshot.getString("name").toString()
                    val age = documentSnapshot.getString("age").toString()
                    val email = documentSnapshot.getString("email").toString()
                    val from = documentSnapshot.getString("from").toString()

                    editTextName.setText(name)
                    editTextAge.setText(age)
                    editTextEmail.setText(email)
                    editTextFrom.setText(from)

                } else {
                    Log.d("", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("", "Error getting document: $exception")
            }

        cancelButton.setOnClickListener {
            val intent = Intent(this, DonorProfile::class.java)
            startActivity(intent)
        }
    }

    private fun updateUserData() {
        val name = editTextName.text.toString()
        val age = editTextAge.text.toString()
        val email = editTextEmail.text.toString()
        val from = editTextFrom.text.toString()

        val userUpdates = hashMapOf<String, Any>(
            "name" to name,
            "age" to age,
            "email" to email,
            "from" to from
        )

        firestoreDatabase.collection("Users").document(user?.uid.toString())
            .update(userUpdates)
            .addOnSuccessListener {
                Toast.makeText(this, "User data updated successfully", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error updating user data: $exception", Toast.LENGTH_LONG).show()
            }
    }
}