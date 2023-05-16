package com.example.hopeheaven

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hopeheaven.databinding.FragmentHomeBinding
import com.example.hopeheaven.databinding.FragmentStudentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        var user = auth.currentUser

        binding.btnLogout.setOnClickListener {

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