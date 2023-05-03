package com.example.hopeheaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DonationHistory : AppCompatActivity() {

    private lateinit var donationRecycleView:RecyclerView
    private lateinit var dbRef:DatabaseReference
    private lateinit var donationArrayList: ArrayList<DonationModel>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation_history)

        donationRecycleView= findViewById(R.id.readDonationsRecview)
        donationRecycleView.layoutManager=LinearLayoutManager(this)
        donationRecycleView.hasFixedSize()

        donationArrayList = arrayListOf<DonationModel>()
        getDonationData()

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
}