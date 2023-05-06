package com.example.hopeheaven

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hopeheaven.adapter.StudentAdapter
import com.example.hopeheaven.models.StudentViewModel

class StudentList : Fragment() {

    private lateinit var viewModel: StudentViewModel
    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentRecyclerView= view.findViewById(R.id.studentRecylerView)
        studentRecyclerView.layoutManager= LinearLayoutManager(context)
        adapter= StudentAdapter()
        studentRecyclerView.adapter= adapter

        viewModel= ViewModelProvider(this).get(StudentViewModel::class.java)

        viewModel.allStudents.observe(viewLifecycleOwner, Observer {
            adapter.updateStudentList(it)
        })

    }



}