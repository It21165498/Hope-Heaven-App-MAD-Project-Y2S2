package com.example.hopeheaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hopeheaven.databinding.ActivityStudentProfileDeleteBinding
import com.example.hopeheaven.databinding.ActivityStudentProfileEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class StudentProfileDelete : AppCompatActivity() {

    private lateinit var binding : ActivityStudentProfileDeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentProfileDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser // Get the Firebase Authentication user object
        val fireStoreDatabase = FirebaseFirestore.getInstance() //Get firestore db
        var storageReference = Firebase.storage

        binding.btnCancelDelete.setOnClickListener {
            finish()
        }

        binding.btnDeleteAcc.setOnClickListener {

            storageReference = FirebaseStorage.getInstance()




        }
    }
}