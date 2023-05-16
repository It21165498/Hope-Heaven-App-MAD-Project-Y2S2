package com.example.hopeheaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hopeheaven.databinding.ActivityStudentProfileDeleteBinding
import com.example.hopeheaven.databinding.ActivityStudentProfileEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class StudentProfileDelete : AppCompatActivity() {

    private lateinit var binding : ActivityStudentProfileDeleteBinding
    private var storageReference = Firebase.storage
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentProfileDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        var user = auth.currentUser
        val fireStoreDatabase = FirebaseFirestore.getInstance() //Get firestore db
        storageReference = FirebaseStorage.getInstance()

        binding.btnCancelDelete.setOnClickListener {
            finish()
        }

        binding.btnDeleteAcc.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()

            user?.delete()
                ?.addOnSuccessListener {
                    Toast.makeText(this, "user auth deleted", Toast.LENGTH_SHORT).show()


                    val documentRef  = fireStoreDatabase.collection("Users").document(user?.uid.toString())
                    val documentRefpic  = fireStoreDatabase.collection("UsersPhotos").document(user?.uid.toString())

                    documentRef.delete()
                        .addOnSuccessListener {
                            // Document deleted successfully
                            documentRefpic.delete().addOnSuccessListener {

                            }.addOnFailureListener {

                            }

                            Toast.makeText(this, "Succefully deleted account", Toast.LENGTH_SHORT).show()
                            auth.signOut()
                            val intent = Intent(this, StudentLogin::class.java)
                            startActivity(intent)// Start the StudentLogin activity
                            finish() //finish current activity

                        }
                        .addOnFailureListener { exception ->
                            // An error occurred while deleting the document
                            // Handle the error here
                        }


                }
                ?.addOnFailureListener { exception ->
                    // An error occurred while deleting the user account
                    // Handle the error here
                }


            val imageRef = storageReference.getReference("images").child("${user?.uid}/profile.jpg")

            imageRef.delete()
                .addOnSuccessListener {
                    // Image file deleted successfully

                }
                .addOnFailureListener { exception ->
                    // An error occurred while deleting the image file
                    // Handle the error here
                }



            // User account deleted successfully




        }
    }
}