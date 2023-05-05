package com.example.hopeheaven

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.hopeheaven.databinding.ActivityStudentLoginBinding
import com.example.hopeheaven.databinding.ActivityStudentProfileEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso

class StudentProfileEdit : AppCompatActivity() {
    private lateinit var binding : ActivityStudentProfileEditBinding
    private var storageReference = Firebase.storage
    private lateinit var uri: Uri
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
        val proPic = intent.getStringExtra("proPic")


        Log.d("myresult", "DATA" + name + age + school + phone + email + from + gender)

        if (proPic != "null") {
            Picasso.get().load(proPic).into(binding.ivProPic)
        } else {
            // Handle this case accordingly, such as displaying a default image instead
        }

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



        // Set the fixed size of the ImageView container
        val imageSize = resources.getDimensionPixelSize(R.dimen.image_size)
        binding.ivProPic.layoutParams.width = imageSize
        binding.ivProPic.layoutParams.height = imageSize

        // Set the scaleType to centerCrop
        binding.ivProPic.scaleType = ImageView.ScaleType.CENTER_CROP

        // Set the left margin of the ImageView container
        val margin = resources.getDimensionPixelSize(R.dimen.image_margin_left)
        val layoutParams = binding.ivProPic.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(margin, 0, 0, 0)
        binding.ivProPic.layoutParams = layoutParams

        val pickImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback { uri ->
                // Display the selected image in the ImageView
                binding.ivProPic.setImageURI(uri)
                binding.tvUploadImg.visibility = View.VISIBLE

                // Store the selected image URI in a class variable for later use
                if (uri != null) {
                    this.uri = uri
                }
            }
        )

        binding.tvChangePic.setOnClickListener {
            // Launch the activity to select an image
            pickImage.launch("image/*")

        }

        binding.tvUploadImg.setOnClickListener {
            // Check if an image has been selected
            if (uri != null) {
                // Upload the selected image to Firebase Storage
                storageReference = FirebaseStorage.getInstance()
                val user = FirebaseAuth.getInstance().currentUser // Get the Firebase Authentication user object
                val fireStoreDatabase = FirebaseFirestore.getInstance() //Get firestore db
                storageReference.getReference("images").child(user?.uid.toString()).child("profile.jpg")
                    .putFile(uri!!)
                    .addOnSuccessListener { task->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { downloadUrl ->
                                Picasso.get().load(downloadUrl).into(binding.ivProPic)
                                Toast.makeText(this, "Profile Picture Updated Successfully", Toast.LENGTH_SHORT).show()

                                val imageUpload = hashMapOf(
                                    "profilePic" to downloadUrl.toString()
                                )

                                fireStoreDatabase.collection("UsersPhotos")
                                    .document(user?.uid.toString())
                                    .set(imageUpload, SetOptions.mergeFields("profilePic"))
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Image URL stored in Firestore", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(this, "Firestore Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                                    }


                            }
                    }
                    .addOnFailureListener { exception ->
                        // Handle any errors that occur during the image upload
                        Toast.makeText(this, "Image Upload Failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please select an image to upload", Toast.LENGTH_SHORT).show()
            }
        }


        binding.tvDeleteAcc.setOnClickListener {
            val intent = Intent(this, StudentProfileDelete::class.java)
            startActivity(intent)
        }


    }


}

