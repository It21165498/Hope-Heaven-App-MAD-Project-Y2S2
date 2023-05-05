package com.example.hopeheaven

import android.widget.EditText

data class DonationModel(
    var dId: String? = null,
    var name: String? =null,
    var email:String?=null,
    var category: String? =null,
    var qty:String?=null,
    var location:String?=null,
    var date:String?=null
)
