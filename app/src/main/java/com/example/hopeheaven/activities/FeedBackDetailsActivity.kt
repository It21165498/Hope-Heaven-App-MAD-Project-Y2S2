package com.example.hopeheaven.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.hopeheaven.R
import com.example.hopeheaven.models.FeedbackModel
import com.google.firebase.database.FirebaseDatabase

class FeedBackDetailsActivity : AppCompatActivity() {
    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpDonationtype: TextView
    private lateinit var tvEmpAdditional: TextView

    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back_details)
        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(

                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empTitle").toString(),
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

    }
    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("FeedBack").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "FeedBack data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()

        }
    }
    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpDonationtype = findViewById(R.id.tvEmpDonationtype)
        tvEmpAdditional = findViewById(R.id.tvEmpAdditional)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }
    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpName.text = intent.getStringExtra("empFullName")
        tvEmpDonationtype.text = intent.getStringExtra("empType")
        tvEmpAdditional.text = intent.getStringExtra("empAddtional")

    }
    private fun openUpdateDialog(
        empId:String,
        empFullName:String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etEmpFullName = mDialogView.findViewById<EditText>(R.id.etEmpFullName)
        val etEmpDonation = mDialogView.findViewById<EditText>(R.id.etEmpDonation)
        val etEmpAddtional = mDialogView.findViewById<EditText>(R.id.etEmpAddtional)


        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etEmpFullName.setText(  intent.getStringExtra("empFullName").toString())
        etEmpDonation.setText(  intent.getStringExtra("empType").toString())
        etEmpAddtional.setText(  intent.getStringExtra("empAddtional").toString())


        mDialog.setTitle("Updating $empFullName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()
        btnUpdateData.setOnClickListener {
            updateTaskData(
                empId,
                etEmpFullName.text.toString(),
                etEmpDonation.text.toString(),
                etEmpAddtional.text.toString(),

            )

            Toast.makeText(applicationContext, "FeedBack Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvEmpName.text = etEmpFullName.text.toString()
            tvEmpDonationtype.text = etEmpDonation.text.toString()
            tvEmpAdditional.text = etEmpAddtional.text.toString()


            alertDialog.dismiss()
        }

    }
    private fun updateTaskData(
        id: String,
        FullName: String,
        donationtype: String,
        additinal: String,

    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("FeedBack").child(id)
        val taskInfo = FeedbackModel(id, FullName, donationtype, additinal)
        dbRef.setValue(taskInfo)
    }
}