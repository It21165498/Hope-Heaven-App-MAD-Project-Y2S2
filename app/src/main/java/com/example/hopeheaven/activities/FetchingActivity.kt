package com.example.hopeheaven.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hopeheaven.models.FeedbackModel
import com.example.hopeheaven.R
import com.example.hopeheaven.adapters.FeedAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {
    private lateinit var feedRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var feedList: ArrayList<FeedbackModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        feedRecyclerView = findViewById(R.id.rvTask)
        feedRecyclerView.layoutManager = LinearLayoutManager(this)
        feedRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        feedList = arrayListOf<FeedbackModel>()

        getFeedBackData()

    }

    private fun getFeedBackData() {
        feedRecyclerView.visibility= View.GONE
        tvLoadingData.visibility= View.VISIBLE
        dbRef= FirebaseDatabase.getInstance().getReference("FeedBack")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                feedList.clear()
                if(snapshot.exists()) {
                    for (taskSnap in snapshot.children) {
                        val taskData = taskSnap.getValue(FeedbackModel::class.java)
                        feedList.add(taskData!!)
                    }
                    val mAdapter = FeedAdapter(feedList)
                    feedRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : FeedAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(this@FetchingActivity, FeedBackDetailsActivity::class.java)

                            //put extra
                            intent.putExtra("empId", feedList[position].empId)
                            intent.putExtra("empFullName", feedList[position].empFullName)
                            intent.putExtra("empType", feedList[position].empType)
                            intent.putExtra("empAddtional", feedList[position].empAddtional)

                            startActivity(intent)
                        }

                    })

                    feedRecyclerView.visibility= View.VISIBLE
                    tvLoadingData.visibility= View.GONE


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}