package com.example.hopeheaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.hopeheaven.databinding.ActivityMainBinding
import com.example.hopeheaven.databinding.ActivityStudentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())


        binding.NavBar.setOnItemSelectedListener {
            lifecycleScope.launch {
                val isStudent = isStudent()
                Toast.makeText(this@MainActivity, isStudent.toString(), Toast.LENGTH_SHORT).show()
                when(it.itemId) {
                    R.id.icon_home -> replaceFragment(Home())
                    R.id.icon_students -> replaceFragment(StudentsList())
                    // R.id.icon_donors -> replaceFragment(all donor fetching fragment should be here)
                    R.id.icon_profile -> if(isStudent) {
                        replaceFragment(StudentProfile())
                    } else {
                        // replaceFragment(donorprofile fragment should be here)
                    }
                    else -> {
                        // handle other menu item clicks
                    }
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


    private suspend fun getCurrentUserDocument(): DocumentSnapshot? {
        return try {
            val user = FirebaseAuth.getInstance().currentUser // Get the Firebase Authentication user object
            val fireStoreDatabase = FirebaseFirestore.getInstance() //Get firestore db

            fireStoreDatabase.collection("Users")
                .document(user?.uid.toString())
                .get()
                .await() // suspend the coroutine until the query completes

        } catch (e: Exception) {
            null // handle errors here
        }
    }

    private suspend fun isStudent(): Boolean {
        val documentSnapshot = getCurrentUserDocument()

        if (documentSnapshot != null && documentSnapshot.exists()) {
            val userType = documentSnapshot.getString("userType").toString()

            if (userType == "Student") {
                return true
            }
        }

        return false
    }



}
