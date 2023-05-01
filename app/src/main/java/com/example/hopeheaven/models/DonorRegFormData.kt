package models

import models.validations.ValidationResult
import java.util.regex.Pattern

class DonorRegFormData(
    private var donorName:String,
    private var donorAge: Int = -1,
    private var donorFrom: String,
    private var donorPhone: Int,
    private var donorEmail: String,
    private var donorPassword: String,
    private var donorPassword2: String,
) {
    fun validateDonorName(): ValidationResult {
        return if(donorName.isEmpty()){
            ValidationResult.Empty("Please enter your name")
        }else{
            ValidationResult.Valid
        }
    }

    fun validateDonorAge(): ValidationResult {
        return if (donorAge == -1) {
            ValidationResult.Empty("Please enter your age")
        } else if (donorAge <= 0) {
            ValidationResult.Invalid("Please enter a valid age")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateDonorFrom(): ValidationResult {
        return if(donorFrom.isEmpty()){
            ValidationResult.Empty("Please enter your address")
        }else{
            ValidationResult.Valid
        }
    }

    fun validateDonorPhone(): ValidationResult {
        return if (donorPhone.toString().isEmpty()) {
            ValidationResult.Empty("Please enter your phone number")
        } else if (!Pattern.matches("^[+]?[0-9]{10,13}\$", donorPhone.toString())) {
            ValidationResult.Invalid("Please enter valid phone number")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateDonorEmail(): ValidationResult {
        return if (donorEmail.isEmpty()) {
            ValidationResult.Empty("Please enter an email")
        } else if (!Pattern.matches("^\\S+@\\S+\\.\\S+\$", donorEmail)) {
            ValidationResult.Invalid("Please enter a valid email")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateDonorPassword(): ValidationResult {
        return  if (donorPassword.isEmpty()) {
            ValidationResult.Empty("Pease ceate a password")
        }else if (donorPassword.length < 5) {
            ValidationResult.Invalid("Password must be at least 5 characters long")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateDonorPassword2(): ValidationResult {
        return  if (donorPassword2.isEmpty()) {
            ValidationResult.Empty("Please re enter your password")
        } else if (donorPassword2 != donorPassword) {
            ValidationResult.Invalid("Passwords do not match")
        } else {
            ValidationResult.Valid
        }
    }



}