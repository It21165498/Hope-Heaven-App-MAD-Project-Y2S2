package com.example.hopeheaven

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hopeheaven.databinding.ActivityStudentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class StudentRegister : AppCompatActivity() {

    private lateinit var binding: ActivityStudentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //change the input fields based on the role-----------------------------
        val radioGroup = binding.rgStuOrDon
        val studentContainer = binding.studentContainer
        val donorContainer = binding.donorContainer

        // Check the student radio button initially
        val studentRadioButton = binding.rbStudent
        studentRadioButton.isChecked = true
        var isUserStudent = true

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbStudent -> {
                    studentContainer.visibility = View.VISIBLE
                    donorContainer.visibility = View.GONE
                    isUserStudent = true
                }
                R.id.rbDonor -> {
                    studentContainer.visibility = View.GONE
                    donorContainer.visibility = View.VISIBLE
                    isUserStudent = false
                }
            }
        } //---------------------------------------------------------------------

        firebaseAuth = FirebaseAuth.getInstance()







        binding.btnCancel.setOnClickListener{
            val intent = Intent(this, StudentLogin::class.java)
            startActivity(intent)
        }
    }
}