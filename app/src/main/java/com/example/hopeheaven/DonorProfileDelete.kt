package com.example.hopeheaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DonorProfileDelete : AppCompatActivity() {

    private lateinit var cancelButton: Button
    private lateinit var deleteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_profile_delete)

        cancelButton=  findViewById(R.id.btnDPDcancel)
        cancelButton.setOnClickListener {
            val intent = Intent(this, DonorProfileEdit::class.java)
            startActivity(intent)
        }
    }
}