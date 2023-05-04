package com.example.hopeheaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hopeheaven.databinding.ActivityMainBinding
import com.example.hopeheaven.databinding.ActivityStudentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())


        binding.NavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.icon_home -> replaceFragment(Home())
                R.id.icon_students -> replaceFragment(StudentsList())
//                R.id.icon_donors -> replaceFragment(all donor fetching fragment should be here)
                R.id.icon_profile -> if(isStudent()){
                    replaceFragment(StudentProfile())
                }else{
//                    replaceFragment(donorprofile fragment shoulb be here)
                }

                else -> {

                }

            }
            true
        }
    }


    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()

    }


    private fun isStudent(): Boolean {
        var isStudent = false // default value
        val user = FirebaseAuth.getInstance().currentUser // Get the Firebase Authentication user object
        val fireStoreDatabase = FirebaseFirestore.getInstance() //Get firestore db

        fireStoreDatabase.collection("Users")
            .document(user?.uid.toString())
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userType = documentSnapshot.getString("userType")

                    if (userType == "Student"){
                        isStudent = true
                    } else {
                        isStudent = false
                    }

                } else {
                    // document does not exist
                }
            }
            .addOnFailureListener { exception ->
                // handle errors here
            }
        return isStudent
    }


}
