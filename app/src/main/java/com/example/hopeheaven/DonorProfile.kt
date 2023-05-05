package com.example.hopeheaven

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.hopeheaven.databinding.FragmentDonorProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class DonorProfile : Fragment() {

    private lateinit var binding : FragmentDonorProfileBinding
    val user = FirebaseAuth.getInstance().currentUser
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    lateinit var myButton: Button
    lateinit var edtBtn:Button




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        binding = FragmentDonorProfileBinding.inflate(inflater, container, false)
        myButton = binding.btnHis
        myButton.setOnClickListener {
            val intent = Intent(activity, DonationHistory::class.java)
            startActivity(intent)
        }



        fireStoreDatabase.collection("Users").document(user?.uid.toString()).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
// Get the data from the document snapshot
                    val age = documentSnapshot.getString("age").toString()
                    val email = documentSnapshot.getString("email").toString()
                    val from = documentSnapshot.getString("from").toString()
                    val name = documentSnapshot.getString("name").toString()


                    binding.textView21.text = name
                    binding.textView15.text = age
                    binding.textView28.text = email
                    binding.textView24.text = from

                    edtBtn=binding.btnEditDonorP
                    edtBtn.setOnClickListener {
                        val intent = Intent(activity, DonorProfileEdit::class.java)
                        intent.putExtra("name", name)
                        intent.putExtra("age", age)
                        intent.putExtra("email", email)
                        intent.putExtra("from", from)
                        startActivity(intent)

                    }



                } else {
                    Log.d("", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("", "Error getting document: $exception")
            }





        return binding.root
    }



}