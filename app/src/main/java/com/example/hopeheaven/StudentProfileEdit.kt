package com.example.hopeheaven

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hopeheaven.databinding.ActivityStudentLoginBinding
import com.example.hopeheaven.databinding.ActivityStudentProfileEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StudentProfileEdit : AppCompatActivity() {
    private lateinit var binding : ActivityStudentProfileEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val age = intent.getStringExtra("age")
        val school = intent.getStringExtra("school")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")
        val from = intent.getStringExtra("from")
        val gender = intent.getStringExtra("gender")
        val achievements = intent.getStringExtra("achievements")
        val needs = intent.getStringExtra("needs")


        Log.d("myresult", "DATA" + name + age + school + phone + email + from + gender)

        binding.etNameInput?.setText(name)
        binding.etAgeInput?.setText(age)
        binding.etSclInput?.setText(school)
        binding.etPhoneInput?.setText(phone)
        binding.etFromInput?.setText(from)
        if(achievements == "null") {
            binding.etAchievements.setText("")
        }else{
            binding.etAchievements.setText(achievements)
        }
        if(needs == "null") {
            binding.etNeeds.setText("")
        }else{
            binding.etNeeds.setText(needs)
        }



        if (gender == "Male") {
            binding.radioButtonMale.isChecked = true
            binding.radioButtonFemale.isChecked = false
        } else{
            binding.radioButtonFemale.isChecked = true
            binding.radioButtonMale.isChecked = false
        }

        binding.radioButtonMale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.radioButtonFemale.isChecked = false
            }
        }

        binding.radioButtonFemale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.radioButtonMale.isChecked = false
            }
        }




        binding.btnUpdate.setOnClickListener {

            val newName = binding.etNameInput.text.toString()
            val newAge = binding.etAgeInput.text.toString()
            val newSchool = binding.etSclInput.text.toString()
            val newPhone = binding.etPhoneInput.text.toString()
            val newFrom = binding.etFromInput.text.toString()
            var selectedGender: String = ""
            var radioGenderMale = binding.radioButtonMale
            var radioGenderFemale = binding.radioButtonFemale
            if (radioGenderMale.isChecked){
                selectedGender = "Male"

            }else if (radioGenderFemale.isChecked){
                selectedGender = "Female"
            }

            var achievements = binding.etAchievements.text.toString()
            var needs = binding.etNeeds.text.toString()

            if (name == newName && age == newAge && school == newSchool && phone == newPhone && from == newFrom && gender == selectedGender && achievements == "null" && needs == "null") {
                Toast.makeText(this, "You haven't changed any values", Toast.LENGTH_SHORT).show()
            }
            else{

                if(newName.isEmpty())
                    binding.etNameInput.error = "Please enter your name"
                else if(newAge.isEmpty())
                    binding.etAgeInput.error = "Please enter your age"
                else if(newSchool.isEmpty())
                    binding.etSclInput.error = "Please enter your school"
                else if(newPhone.isEmpty())
                    binding.etPhoneInput.error = "Please enter your phone number"
                else if(newFrom.isEmpty())
                    binding.etFromInput.error = "Please enter your from"
                else{

                    var data = hashMapOf(
                        "email" to email,
                        "name" to newName,
                        "school" to newSchool,
                        "from" to newFrom,
                        "age" to newAge,
                        "phone" to newPhone,
                        "gender" to selectedGender,
                        "userType" to "Student",
                        "achievements" to achievements,
                        "needs" to needs

                    )


                    val user = FirebaseAuth.getInstance().currentUser // Get the Firebase Authentication user object
                    val fireStoreDatabase = FirebaseFirestore.getInstance() //Get firestore db

                    fireStoreDatabase.collection("Users").document(user?.uid.toString()).set(data)
                    Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()


                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("fragment", R.id.icon_profile)
                    startActivity(intent)
                    finish()


                }


            }


        }

        binding.btnUpdateCancel.setOnClickListener {
            finish()
        }
        binding.ivBackEditProfile.setOnClickListener {
            finish()
        }












    }
}