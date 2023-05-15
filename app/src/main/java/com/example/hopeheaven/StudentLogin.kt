package com.example.hopeheaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hopeheaven.databinding.ActivityStudentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class StudentLogin : AppCompatActivity() {

    private lateinit var binding : ActivityStudentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvRegisterNow.setOnClickListener {
            val intent = Intent(this, StudentRegister::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailLogin.text.toString()
            val pwd = binding.etPwdLogin.text.toString()
            
            if(email.isNotEmpty() && pwd.isNotEmpty()){

                firebaseAuth.signInWithEmailAndPassword(email , pwd).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Please enter both email and password!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}