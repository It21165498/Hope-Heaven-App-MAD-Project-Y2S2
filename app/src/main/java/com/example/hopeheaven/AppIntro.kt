package com.example.hopeheaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hopeheaven.databinding.ActivityAppIntroBinding
import com.example.hopeheaven.databinding.ActivityStudentRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class AppIntro : AppCompatActivity() {
    private lateinit var binding: ActivityAppIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        binding.btnLogin1.setOnClickListener {
            val intent = Intent(this, StudentLogin::class.java)
            startActivity(intent)
        }

        binding.btnRegister1.setOnClickListener {
            val intent = Intent(this, StudentRegister::class.java)
            startActivity(intent)
        }

    }
}