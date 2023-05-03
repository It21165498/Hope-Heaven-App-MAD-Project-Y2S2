package com.example.hopeheaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MakeDonation : AppCompatActivity() {

    private lateinit var fname: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var category: EditText
    private lateinit var qty: EditText
    private lateinit var location: EditText
    private lateinit var date: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_donation)

        fname = findViewById(R.id.etName)
        email = findViewById(R.id.mail)
        phone = findViewById(R.id.phone)
        category = findViewById(R.id.cat)
        qty = findViewById(R.id.qty)
        location = findViewById(R.id.location)
        date = findViewById(R.id.day)
        btnSaveData = findViewById(R.id.btnDonate)

        dbRef = FirebaseDatabase.getInstance().getReference("Donations")


        btnSaveData.setOnClickListener {
            saveDonationData()
        }
    }

    private fun saveDonationData(){
        val fName = fname.text.toString()
        val Email = email.text.toString()
        val Phone = phone.text.toString()
        val Category = category.text.toString()
        val Qty = qty.text.toString()
        val Location = location.text.toString()
        val Date = date.text.toString()

        if (fName.isEmpty()) {
            fname.error="please enter name"
        }
        if (Email.isEmpty()) {
            email.error="please enter email"
        }
        if (Phone.isEmpty()) {
            phone.error="please enter phone number"
        }
        if (Category.isEmpty()) {
            category.error="please enter category"
        }
        if (Qty.isEmpty()) {
            qty.error="please enter quantity"
        }
        if (Location.isEmpty()) {
            location.error="please enter pickup location"
        }
        if (Date.isEmpty()) {
            date.error="please enter pickup date"
        }

        val donationId = dbRef.push().key!!

        val donation = DonationModel(donationId, fName, Email, Category,Qty,Location,Date)


        dbRef.child(donationId).setValue(donation)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                fname.text.clear()
                email.text.clear()
                phone.text.clear()
                category.text.clear()
                qty.text.clear()
                location.text.clear()
                date.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }


    }
}