package com.example.hopeheaven

import android.text.Editable

data class DonorsModel(
    var fullName:String?=null, var age: Editable ,
    var from:String?=null, var phone: Editable , var email: String?=null,
    var password:String?=null, var donorId:String?=null){

}
