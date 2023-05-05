package com.example.hopeheaven

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hopeheaven.databinding.ActivityStudentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class StudentRegister : AppCompatActivity() {

    private lateinit var binding: ActivityStudentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //change the input fields based on the role-----------------------------
        val radioGroup = binding.rgStuOrDon
        val studentContainer = binding.studentContainer
        val donorContainer = binding.donorContainer

        // Check the student radio button initially
        val studentRadioButton = binding.rbStudent
        studentRadioButton.isChecked = true
        var isUserStudent = true

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbStudent -> {
                    studentContainer.visibility = View.VISIBLE
                    donorContainer.visibility = View.GONE
                    isUserStudent = true
                }
                R.id.rbDonor -> {
                    studentContainer.visibility = View.GONE
                    donorContainer.visibility = View.VISIBLE
                    isUserStudent = false
                }
            }
        } //---------------------------------------------------------------------

        firebaseAuth = FirebaseAuth.getInstance()
        val fireStoreDatabase = FirebaseFirestore.getInstance()
        var data: HashMap<String, Any>

        binding.btnRegister.setOnClickListener {
            var email = binding.etEmail.text.toString()
            var pwd = binding.etPwd.text.toString()
            var confirmPwd = binding.etConfirmPwd.text.toString()

            if (pwd == confirmPwd) {
                firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener {
                    if (it.isSuccessful) {

                        if (isUserStudent){

                            //student
                            var fName = binding.etFullName.text.toString()
                            var school = binding.etSchool.text.toString()
                            var from = binding.etFrom.text.toString()
                            var age = binding.etAge.text.toString()
                            var phone = binding.etPhone.text.toString()
                            var selectedGender: String = ""
                            var radioGenderMale = binding.rbMale
                            var radioGenderFemale = binding.rbFemale

                            if (radioGenderMale.isChecked){
                                selectedGender = "Male"

                            }else if (radioGenderFemale.isChecked){
                                selectedGender = "Female"
                            }

                            data = hashMapOf(
                                "email" to email,
                                "name" to fName,
                                "school" to school,
                                "from" to from,
                                "age" to age,
                                "phone" to phone,
                                "gender" to selectedGender,
                                "userType" to "Student",
                                "achievements" to "",
                                "needs" to ""
                            )

                        }else{

                            //donor
                            var fDonorName = binding.etDonorName.text.toString()
                            var ageDonor = binding.etDonorAge.text.toString()
                            var fromDonor = binding.etDonorFrom.text.toString()

                            data = hashMapOf(
                                "email" to email,
                                "name" to fDonorName,
                                "age" to ageDonor,
                                "from" to fromDonor,
                                "userType" to "Donor"
                            )

                        }

                        // Get the Firebase Authentication user object
                        val user = FirebaseAuth.getInstance().currentUser


                        fireStoreDatabase.collection("Users").document(user?.uid.toString()).set(data)
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    }else{

                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()

            }


        }






        binding.btnCancel.setOnClickListener{
            val intent = Intent(this, StudentLogin::class.java)
            startActivity(intent)
        }
    }
}