package com.example.hopeheaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hopeheaven.databinding.ActivityDonorRegisterBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import models.validations.ValidationResult
import java.util.regex.Pattern


class DonorRegister : AppCompatActivity() {

    lateinit var editTextDonorName: EditText
    lateinit var dAge : EditText
    lateinit var dFrom: EditText
    lateinit var dPhone: EditText
    lateinit var dEmail: EditText
    lateinit var dPassword: EditText
    lateinit var dPassword2: EditText
    lateinit var subBtn:Button
    private var count = 0;

    private lateinit var dbRef:DatabaseReference




//    fun displayAlert(title:String, message:String){
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle(title)
//        builder.setMessage(message)
//        builder.setPositiveButton("OK") { dialog, which ->
//            // Do something when the "OK" button is clicked
//        }
//        val dialog = builder.create()
//        dialog.show()
//    }
//
//    fun submit(v: View) {
//        val myForm = DonorRegFormData(
//            editTextDonorName.text.toString(),
//            dAge.text.toString().toInt(),
//            dFrom.text.toString(),
//            dPhone.text.toString().toInt(),
//            dEmail.text.toString(),
//            dPassword.text.toString(),
//            dPassword2.text.toString()
//        )
//        val donorNameValidation = myForm.validateDonorName()
//        val donorAgeValidation = myForm.validateDonorAge()
//        val donorAddressValidation = myForm.validateDonorFrom()
//        val donorPhoneValidation = myForm.validateDonorPhone()
//        val donorEmailValidation = myForm.validateDonorEmail()
//        val donorPasswordValidation = myForm.validateDonorPassword()
//        val donorPasswordValidation2 = myForm.validateDonorPassword2()
//
//        when (donorNameValidation) {
//            is ValidationResult.Valid -> {
//                count++
//            }
//            is ValidationResult.Invalid -> {
//                editTextDonorName.error = donorNameValidation.errorMessage
//            }
//            is ValidationResult.Empty -> {
//                editTextDonorName.error = donorNameValidation.errorMessage
//            }
//        }
//
//        when (donorAgeValidation) {
//            is ValidationResult.Valid -> {
//                count++
//            }
//            is ValidationResult.Invalid -> {
//                dAge.error = donorAgeValidation.errorMessage
//            }
//            is ValidationResult.Empty -> {
//                dAge.error = donorAgeValidation.errorMessage
//            }
//        }
//
//        when (donorAddressValidation) {
//            is ValidationResult.Valid -> {
//                count++
//            }
//            is ValidationResult.Invalid -> {
//                dFrom.error = donorAddressValidation.errorMessage
//            }
//            is ValidationResult.Empty -> {
//                dFrom.error = donorAddressValidation.errorMessage
//            }
//        }
//
//        when (donorPhoneValidation) {
//            is ValidationResult.Valid -> {
//                count++
//            }
//            is ValidationResult.Invalid -> {
//                dPhone.error = donorPhoneValidation.errorMessage
//            }
//            is ValidationResult.Empty -> {
//                dPhone.error = donorPhoneValidation.errorMessage
//            }
//        }
//
//        when (donorEmailValidation) {
//            is ValidationResult.Valid -> {
//                count++
//            }
//            is ValidationResult.Invalid -> {
//                dEmail.error = donorEmailValidation.errorMessage
//            }
//            is ValidationResult.Empty -> {
//                dEmail.error = donorEmailValidation.errorMessage
//            }
//        }
//
//        when (donorPasswordValidation) {
//            is ValidationResult.Valid -> {
//                count++
//            }
//            is ValidationResult.Invalid -> {
//                dPassword.error = donorPasswordValidation.errorMessage
//            }
//            is ValidationResult.Empty -> {
//                dPassword.error = donorPasswordValidation.errorMessage
//            }
//        }
//
//        when (donorPasswordValidation2) {
//            is ValidationResult.Valid -> {
//                count++
//            }
//            is ValidationResult.Invalid -> {
//                dPassword2.error = donorPasswordValidation2.errorMessage
//            }
//            is ValidationResult.Empty -> {
//                dPassword2.error = donorPasswordValidation2.errorMessage
//            }
//        }
//
//        if (count == 7) {
//            val intent = Intent(this, StudentLogin::class.java)
//            startActivity(intent)
//        }
//
//    }

    private lateinit var binding: ActivityDonorRegisterBinding
    private lateinit var database:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding=ActivityDonorRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDonate  .setOnClickListener {
            val intent = Intent (this, StudentLogin::class.java)
           startActivity(intent)
        }

        editTextDonorName = findViewById(R.id.etName)
        dAge = findViewById(R.id.mail)
        dFrom = findViewById(R.id.phone)
        dPhone = findViewById(R.id.cat)
        dEmail = findViewById(R.id.qty)
        dPassword = findViewById(R.id.location)
        dPassword2 = findViewById(R.id.day)
        subBtn=findViewById(R.id.btnDonate)

        dbRef=FirebaseDatabase.getInstance().getReference("Donor")
        subBtn.setOnClickListener {
             saveDonorData()
        }

//        val regBtn = findViewById<Button>(R.id.btnReg)
//        regBtn.setOnClickListener{
//            val intent = Intent (this, StudentLogin::class.java)
//            startActivity(intent)
//        }
    }

    private fun saveDonorData(){
            val name=editTextDonorName.text.toString()
            val age=dAge.text
            val from=dFrom.text.toString()
            val phone=dPhone.text
            val email=dEmail.text.toString()
            val password=dPassword.text.toString()
            val password2=dPassword2.text.toString()

           if(name.isEmpty()){
               editTextDonorName.error="plese enter name"
           }
        if(age.isEmpty()){
            dAge.error="please enter age"
        }
        if(from.isEmpty()) {
            dFrom.error = "please enter address"
        }
        if(phone.isEmpty()){
            dPhone.error="please enter phone number"
        }else if (!Pattern.matches("^[+]?[0-9]{10,13}\$", phone.toString())){
            dPhone.error="please enter valid phone number"
        }

        if(email.isEmpty()){
            dEmail.error="please enter email"
        } else if (!Pattern.matches("^\\S+@\\S+\\.\\S+\$", email)){
            dEmail.error="please enter valid email address"
        }

        if(password.isEmpty()){
            dPassword.error="please enter password"
        }

        if(password2.isEmpty()){
            dPassword2.error="please re enter password"
        }else if(password != password2) {
                ValidationResult.Invalid("Passwords do not match")
        }

        val donorId=dbRef.push().key!!

        val donor = Donor(name,age ,from,phone,email,password,donorId)
        dbRef.child(donorId).setValue(donor)
            .addOnCompleteListener(){
                Toast.makeText(this,"data inserted successfully",Toast.LENGTH_LONG).show()
            }.addOnFailureListener(){
                err->Toast.makeText(this,"data did not inserted successfully",Toast.LENGTH_LONG).show()
            }

    }
}