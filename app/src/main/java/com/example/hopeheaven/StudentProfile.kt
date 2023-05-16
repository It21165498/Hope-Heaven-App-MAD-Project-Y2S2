package com.example.hopeheaven

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.hopeheaven.databinding.FragmentStudentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class StudentProfile : Fragment() {

    private lateinit var binding: FragmentStudentProfileBinding
    // Get the Firebase Authentication user object
    lateinit var auth : FirebaseAuth
    lateinit var age: String
    lateinit var email: String
    lateinit var from: String
    lateinit var gender: String
    lateinit var name: String
    lateinit var phone: String
    lateinit var school: String
    lateinit var achievements : String
    lateinit var needs : String
    lateinit var proPic : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentProfileBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        var user = auth.currentUser
        val fireStoreDatabase = FirebaseFirestore.getInstance()

        fireStoreDatabase.collection("UsersPhotos").document(user?.uid.toString()).get()
            .addOnSuccessListener {
                proPic = it.getString("profilePic").toString()
            }

        fireStoreDatabase.collection("Users").document(user?.uid.toString()).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Get the data from the document snapshot
                    age = documentSnapshot.getString("age").toString()
                    email = documentSnapshot.getString("email").toString()
                    from = documentSnapshot.getString("from").toString()
                    gender = documentSnapshot.getString("gender").toString()
                    name = documentSnapshot.getString("name").toString()
                    phone = documentSnapshot.getString("phone").toString()
                    school = documentSnapshot.getString("school").toString()
                    achievements = documentSnapshot.getString("achievements").toString()
                    needs = documentSnapshot.getString("needs").toString()



                    binding.tvNameInput.text = name
                    binding.tvAgeInput.text = age
                    binding.tvSclInput.text = school
                    binding.tvPhoneInput.text = phone
                    binding.tvEmailInput.text = email
                    binding.tvFromInput.text = from
                    binding.tvGenderInput.text = gender
                    if (achievements == ""){
                        binding.tvAchievements.text = "No Achievements"
                    }else{
                        binding.tvAchievements.text = achievements
                    }
                    if (needs == ""){
                        binding.tvNeeds.text = "No Needs"
                    }else{
                        binding.tvNeeds.text = needs
                    }

                    if(proPic != "null"){
                        // Set the fixed size of the ImageView container
                        val imageSize = resources.getDimensionPixelSize(R.dimen.image_size)
                        binding.ivProfilePic.layoutParams.width = imageSize
                        binding.ivProfilePic.layoutParams.height = imageSize

                        // Set the scaleType to centerCrop
                        binding.ivProfilePic.scaleType = ImageView.ScaleType.CENTER_CROP

                        // Set the left margin of the ImageView container
                        val margin = resources.getDimensionPixelSize(R.dimen.image_margin_left)
                        val layoutParams = binding.ivProfilePic.layoutParams as ViewGroup.MarginLayoutParams
                        layoutParams.setMargins(margin, 0, 0, 0)
                        binding.ivProfilePic.layoutParams = layoutParams
                        Picasso.get().load(proPic).into(binding.ivProfilePic)

                    }





                } else {
                    Log.d("", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("", "Error getting document: $exception")
            }


        binding.tvEditProfile.setOnClickListener {

            // Get the hosting Activity
            val activity = requireActivity()
            val i = Intent(activity, StudentProfileEdit::class.java)
            i.putExtra("name", name)
            i.putExtra("age", age)
            i.putExtra("school", school)
            i.putExtra("phone", phone)
            i.putExtra("email", email)
            i.putExtra("from", from)
            i.putExtra("gender", gender)
            i.putExtra("achievements",achievements)
            i.putExtra("needs",needs)
            if(proPic != "null"){
                i.putExtra("proPic",proPic)
            }

            activity.startActivity(i)// Start the StudentLogin activity

        }

        binding.tvLogOut.setOnClickListener {
            auth.signOut()
            // Get the hosting Activity
            val activity = requireActivity()
            val intent = Intent(activity, StudentLogin::class.java)
            activity.startActivity(intent)// Start the StudentLogin activity
            activity.finish() //finish current activity

        }





        return binding.root
    }

}