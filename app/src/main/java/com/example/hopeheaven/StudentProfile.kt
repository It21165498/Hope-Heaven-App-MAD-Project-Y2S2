package com.example.hopeheaven

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hopeheaven.databinding.FragmentStudentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class StudentProfile : Fragment() {

    private lateinit var binding: FragmentStudentProfileBinding
    // Get the Firebase Authentication user object
    lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentProfileBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        var user = auth.currentUser
        val fireStoreDatabase = FirebaseFirestore.getInstance()

        fireStoreDatabase.collection("Users").document(user?.uid.toString()).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Get the data from the document snapshot
                    val age = documentSnapshot.getString("age").toString()
                    val email = documentSnapshot.getString("email").toString()
                    val from = documentSnapshot.getString("from").toString()
                    val gender = documentSnapshot.getString("gender").toString()
                    val name = documentSnapshot.getString("name").toString()
                    val phone = documentSnapshot.getString("phone").toString()
                    val school = documentSnapshot.getString("Student").toString()

                    binding.tvNameInput.text = name
                    binding.tvAgeInput.text = age
                    binding.tvSclInput.text = school
                    binding.tvPhoneInput.text = phone
                    binding.tvEmailInput.text = email
                    binding.tvFromInput.text = from
                    binding.tvGenderInput.text = gender



                } else {
                    Log.d("", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("", "Error getting document: $exception")
            }

        binding.tvLogOut.setOnClickListener {
            auth.signOut()
            // Get the hosting Activity
            val activity = requireActivity()
            val intent = Intent(activity, StudentLogin::class.java)
            activity.startActivity(intent)// Start the StudentLogin activity
            activity.finish() //finish current activity

        }





        return binding.root
    }

}