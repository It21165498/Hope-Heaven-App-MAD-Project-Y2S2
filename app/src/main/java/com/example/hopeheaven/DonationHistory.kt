package com.example.hopeheaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.fragment.app.FragmentTransaction
class DonationHistory : AppCompatActivity() {

    private lateinit var donationRecycleView:RecyclerView
    private lateinit var dbRef:DatabaseReference
    private lateinit var donationArrayList: ArrayList<DonationModel>
    private lateinit var backBtn : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation_history)

        donationRecycleView= findViewById(R.id.readDonationsRecview)
        donationRecycleView.layoutManager=LinearLayoutManager(this)
        donationRecycleView.hasFixedSize()

        donationArrayList = arrayListOf<DonationModel>()
        getDonationData()

        backBtn = findViewById(R.id.btnBack)
        backBtn.setOnClickListener {
            navigateToHomeFragment()
        }
        val fragmentToLoad = intent.getStringExtra("FRAGMENT_TO_LOAD")
        if (fragmentToLoad == "MY_FRAGMENT") {
            supportFragmentManager.beginTransaction().replace(R.id.dnr, DonorProfile()).commit()
        }




    }

    private fun getDonationData(){
    dbRef=FirebaseDatabase.getInstance().getReference("Donations")
    dbRef.addValueEventListener(object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {

            if(snapshot.exists()){
                for(donationSnapshot in snapshot.children){
                    val don = donationSnapshot.getValue(DonationModel::class.java)
                    donationArrayList.add(don!!)

                }
                donationRecycleView.adapter=DonationAdapter(donationArrayList)

            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }


    })
    }

    private fun navigateToHomeFragment() {
        val homeFragment = Home() // Replace `HomeFragment` with your actual fragment class
        supportFragmentManager.beginTransaction()
            .replace(R.id.home, homeFragment) // Replace `R.id.fragment_container` with the actual ID of the container in your layout
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

}