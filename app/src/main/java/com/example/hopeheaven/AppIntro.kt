package com.example.hopeheaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hopeheaven.databinding.ActivityAppIntroBinding
import com.example.hopeheaven.databinding.ActivityStudentRegisterBinding

class AppIntro : AppCompatActivity() {
    private lateinit var binding: ActivityAppIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStudent.setOnClickListener {
            val intent = Intent(this, StudentLogin::class.java)
            startActivity(intent)
        }

    }
}