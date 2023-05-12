package models

import models.validations.ValidationResult
import java.util.regex.Pattern

class DonationFormData(
    private var donorName:String,
    private var category: String,
    private var QTY: String,
    private var donorPhone: Int,
    private var donorEmail: String,
    private var pickupLoc: String,
    private var pickUpDay: String,
) {
    fun validateDonorName(): ValidationResult {
        return if(donorName.isEmpty()){
            ValidationResult.Empty("Please enter your name")
        }else{
            ValidationResult.Valid
        }
    }

    fun validateCategory(): ValidationResult {
        return if(category.isEmpty()){
            ValidationResult.Empty("Please enter category")
        }else{
            ValidationResult.Valid
        }
    }

    fun validateQTY(): ValidationResult {
        return if(QTY.isEmpty()){
            ValidationResult.Empty("Please enter quantity")
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

    fun validatePickupLocation(): ValidationResult {
        return  if (pickupLoc.isEmpty()) {
            ValidationResult.Empty("Pease ceate a pickup place")
        }else {
            ValidationResult.Valid
        }
    }

    fun validatePickUpDate(): ValidationResult {
        return  if (pickUpDay.isEmpty()) {
            ValidationResult.Empty("Please re enter pickup date")
        }else {
            ValidationResult.Valid
        }
    }



}