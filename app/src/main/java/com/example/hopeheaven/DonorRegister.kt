package com.example.hopeheaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import models.DonorRegFormData
import models.validations.ValidationResult

class DonorRegister : AppCompatActivity() {

    lateinit var editTextDonorName: EditText
    lateinit var dAge : EditText
    lateinit var dFrom: EditText
    lateinit var dPhone: EditText
    lateinit var dEmail: EditText
    lateinit var dPassword: EditText
    lateinit var dPassword2: EditText
    private var count = 0;

    fun displayAlert(title:String, message:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which ->
            // Do something when the "OK" button is clicked
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun submit(v: View){
        val myForm = DonorRegFormData(
            editTextDonorName.text.toString(),
            dAge.text.toString().toInt(),
            dFrom.text.toString(),
            dPhone.text.toString().toInt(),
            dEmail.text.toString(),
            dPassword.text.toString(),
            dPassword2.text.toString()
        )
        val donorNameValidation = myForm.validateDonorName()
        val donorAgeValidation=myForm.validateDonorAge()
        val donorAddressValidation = myForm.validateDonorFrom()
        val donorPhoneValidation = myForm.validateDonorPhone()
        val donorEmailValidation= myForm.validateDonorEmail()
        val donorPasswordValidation = myForm.validateDonorPassword()
        val donorPasswordValidation2 = myForm.validateDonorPassword2()

        when(donorNameValidation){
            is ValidationResult.Valid ->{
                count ++
            }
            is ValidationResult.Invalid ->{
                editTextDonorName.error = donorNameValidation.errorMessage
            }
            is ValidationResult.Empty ->{
                editTextDonorName.error = donorNameValidation.errorMessage
            }
        }

        when (donorAgeValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                dAge.error = donorAgeValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                dAge.error = donorAgeValidation.errorMessage
            }
        }

        when (donorAddressValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                dFrom.error = donorAddressValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                dFrom.error = donorAddressValidation.errorMessage
            }
        }

        when (donorPhoneValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                dPhone.error = donorPhoneValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                dPhone.error = donorPhoneValidation.errorMessage
            }
        }

        when (donorEmailValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                dEmail.error = donorEmailValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                dEmail.error = donorEmailValidation.errorMessage
            }
        }

        when (donorPasswordValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                dPassword.error = donorPasswordValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                dPassword.error = donorPasswordValidation.errorMessage
            }
        }

        when (donorPasswordValidation2) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                dPassword2.error = donorPasswordValidation2.errorMessage
            }
            is ValidationResult.Empty -> {
                dPassword2.error = donorPasswordValidation2.errorMessage
            }
        }

        if(count==7){
            displayAlert("Success","You have successfully registered")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_register)

        editTextDonorName = findViewById(R.id.editTextDonorName)
        dAge = findViewById(R.id.dAge)
        dFrom = findViewById(R.id.dFrom)
        dPhone = findViewById(R.id.dPhone)
        dEmail = findViewById(R.id.dEmail)
        dPassword = findViewById(R.id.dPassword)
        dPassword2 = findViewById(R.id.dPassword2)
    }
}