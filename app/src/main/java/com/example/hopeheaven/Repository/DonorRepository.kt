package com.example.hopeheaven.Repository


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hopeheaven.models.DonorModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class DonorRepository {
    val user = FirebaseAuth.getInstance().currentUser // Get the Firebase Authentication user object
    val fireStoreDatabase = FirebaseFirestore.getInstance() //Get firestore db
    private val firestoreReference: CollectionReference =
        FirebaseFirestore.getInstance().collection("Users")
    @Volatile
    private var INSTANCE: DonorRepository? = null

    fun getInstance(): DonorRepository {

        return INSTANCE ?: synchronized(this) {
            val instance = DonorRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadDonors(donorList: MutableLiveData<List<DonorModel>>) {
        firestoreReference.whereEqualTo("userType", "Donor").get().addOnSuccessListener { documents ->
            try {
                val _donorList: List<DonorModel> = documents.map { document ->
                    document.toObject(DonorModel::class.java)
                }

                donorList.postValue(_donorList)
            } catch (e: Exception) {
                Log.e(TAG, "Error getting donors", e)
            }
        }.addOnFailureListener { e ->
            Log.e(TAG, "Error getting donors", e)
        }
    }

}