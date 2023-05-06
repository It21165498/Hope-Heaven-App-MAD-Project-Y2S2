package com.example.hopeheaven.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hopeheaven.Repository.StudentRepository

class StudentViewModel : ViewModel() {

    private val repository: StudentRepository = StudentRepository().getInstance()
    private val studentLists= MutableLiveData<List<StudentModel>>()
    val allStudents: LiveData<List<StudentModel>> = studentLists

    init {
        repository.loadStudents(studentLists)

    }
}