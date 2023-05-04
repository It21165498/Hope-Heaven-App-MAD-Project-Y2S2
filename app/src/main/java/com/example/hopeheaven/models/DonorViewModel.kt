package com.example.hopeheaven.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hopeheaven.Repository.DonorRepository

class DonorViewModel:ViewModel() {
    private val repository:DonorRepository
    private val _allDonors= MutableLiveData<List<DonorModel>>()
    val allDonors: LiveData<List<DonorModel>> = _allDonors

    init {
        repository=DonorRepository().getInstance()
        repository.loadDonors(_allDonors)

    }
}