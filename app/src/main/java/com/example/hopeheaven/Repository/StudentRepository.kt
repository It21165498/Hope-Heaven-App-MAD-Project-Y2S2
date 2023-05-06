package com.example.hopeheaven.Repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hopeheaven.models.StudentModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class StudentRepository {
    private val firestoreReference: CollectionReference = FirebaseFirestore.getInstance().collection("Users")

    @Volatile
    private var INSTANCE: StudentRepository? = null

    fun getInstance(): StudentRepository {

        return INSTANCE ?: synchronized(this) {
            val instance = StudentRepository()
            INSTANCE = instance
            instance
        }
    }


    fun loadStudents(studentList: MutableLiveData<List<StudentModel>>) {
        firestoreReference.whereEqualTo("userType", "Student").get().addOnSuccessListener { documents ->
            try {
                val studentLists: List<StudentModel> = documents.map { document ->
                    document.toObject(StudentModel::class.java)
                }

                studentList.postValue(studentLists)
            } catch (e: Exception) {
                Log.e(TAG, "Error getting students", e)
            }
        }.addOnFailureListener { e ->
            Log.e(TAG, "Error getting students", e)
        }
    }


}