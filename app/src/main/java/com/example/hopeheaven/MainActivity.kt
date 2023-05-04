package com.example.hopeheaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hopeheaven.databinding.ActivityMainBinding
import com.example.hopeheaven.databinding.ActivityStudentRegisterBinding

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
                R.id.icon_donors -> replaceFragment(DonorList())
                R.id.icon_profile -> replaceFragment(StudentProfile())

                else -> {
                    replaceFragment(DonorProfile())
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
}
