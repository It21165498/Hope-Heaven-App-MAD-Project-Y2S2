package com.example.hopeheaven.models

import java.io.Serializable

data class StudentModel(
    var name: String? = null,
    var age: String? = null,
    var from: String? = null,
    var email: String? = null,
    var gender: String? = null,
    var school: String? = null,
    var phone: String? = null,
    var achievements: String? = null,
    var needs: String? = null


): Serializable
