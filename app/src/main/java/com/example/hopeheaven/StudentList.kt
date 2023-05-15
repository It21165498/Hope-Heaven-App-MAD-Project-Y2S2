package com.example.hopeheaven

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hopeheaven.adapter.StudentAdapter
import com.example.hopeheaven.models.StudentModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class StudentList : Fragment() {

    private lateinit var studentRecyclerView: RecyclerView
    private var studentList = mutableListOf<StudentModel>()
    private val firestoreReference: CollectionReference = FirebaseFirestore.getInstance().collection("Users")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        studentRecyclerView = view.findViewById(R.id.studentRecylerView)
        studentRecyclerView.layoutManager = LinearLayoutManager(context)

        studentList = arrayListOf()

        getStudentData()
    }

    private fun getStudentData() {
        firestoreReference.whereEqualTo("userType", "Student").get().addOnSuccessListener { documents ->

            for (document in documents) {
                val student = document.toObject(StudentModel::class.java)
                studentList.add(student)
            }

            val sAdapter = StudentAdapter(ArrayList(studentList))
            studentRecyclerView.adapter = sAdapter

            sAdapter.setOnItemClickListener(object : StudentAdapter.OnItemClickListener {
                override fun onDonateButtonClick(student: StudentModel) {
                    // Handle the donate button click here
                    val intent = Intent(requireContext(), StudentProfile2::class.java)
                    intent.putExtra("student", student)
                    startActivity(intent)
                }
            })

        }.addOnFailureListener { e ->
            Log.e(TAG, "Error getting student data: ", e)
        }
    }
}
