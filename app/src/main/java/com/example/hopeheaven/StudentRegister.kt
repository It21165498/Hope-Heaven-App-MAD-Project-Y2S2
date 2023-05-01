package com.example.hopeheaven

import android.content.Intent
import android.os.Bundle
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

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            var fName = binding.etFullName.text.toString()
            var role = binding.etRole.text.toString()
            var age = binding.etAge.text.toString()
            var from = binding.etFrom.text.toString()
            var school = binding.etSchool.text.toString()
            var email = binding.etEmail.text.toString()
            var pwd = binding.etPwd.text.toString()
            var confirmPwd = binding.etConfirmPwd.text.toString()

            if (fName.isNotEmpty() && role.isNotEmpty() && age.isNotEmpty() && from.isNotEmpty() &&
                school.isNotEmpty() && email.isNotEmpty() && pwd.isNotEmpty() && confirmPwd.isNotEmpty()
            ) {
                if (pwd == confirmPwd) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener {
                        if (it.isSuccessful) {

                            // Get the Firebase Authentication user object
                            val user = FirebaseAuth.getInstance().currentUser

                            // Get a reference to the Firebase Realtime Database
                            val database = FirebaseDatabase.getInstance().reference

                            // Create a new node for the user in the database with the uid as the key
                            val userNode = database.child("Students").child(user!!.uid)

                            // Set the user data in the user node
                            userNode.child("name").setValue(fName)
                            userNode.child("role").setValue(role)
                            userNode.child("age").setValue(age)
                            userNode.child("from").setValue(from)
                            userNode.child("school").setValue(school)
                            userNode.child("email").setValue(email)

                            val intent = Intent(this, StudentLogin::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Please enter values for all fields", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }
}