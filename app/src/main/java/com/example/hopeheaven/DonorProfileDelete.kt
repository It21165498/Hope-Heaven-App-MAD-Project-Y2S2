package com.example.hopeheaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DonorProfileDelete : AppCompatActivity() {

    private lateinit var cancelButton: Button
    private lateinit var deleteButton: Button
    private val user = FirebaseAuth.getInstance().currentUser
    private val firestoreDatabase = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_profile_delete)

        deleteButton=findViewById(R.id.btnDPdelete)
        cancelButton=  findViewById(R.id.btnDPDcancel)

        deleteButton.setOnClickListener {
            deleteUserData()
        }
        cancelButton.setOnClickListener {
            val intent = Intent(this, DonorProfileEdit::class.java)
            startActivity(intent)
        }
    }

    private fun deleteUserData() {
        firestoreDatabase.collection("Users").document(user?.uid.toString())
            .delete()
            .addOnSuccessListener {
                FirebaseAuth.getInstance().currentUser?.delete()
                    ?.addOnSuccessListener {
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(this, StudentLogin::class.java)
                        startActivity(intent)
                        finish()
                    }
                    ?.addOnFailureListener { exception ->
                        Toast.makeText(this, "Error deleting user authentication: $exception", Toast.LENGTH_LONG).show()
                    }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error deleting user data: $exception", Toast.LENGTH_LONG).show()
            }
    }


}