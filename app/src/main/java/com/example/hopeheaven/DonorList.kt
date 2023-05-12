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
import com.example.hopeheaven.adapter.DonorAdapter
import com.example.hopeheaven.models.DonorViewModel

private lateinit var viewModel: DonorViewModel
private lateinit var donorRecyclerView: RecyclerView
private lateinit var adapter: DonorAdapter
class DonorList : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donor_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        donorRecyclerView= view.findViewById(R.id.readDonorsRecyclerView)
        donorRecyclerView.layoutManager=LinearLayoutManager(context)
        adapter= DonorAdapter()
        donorRecyclerView.adapter= adapter

        viewModel=ViewModelProvider(this).get(DonorViewModel::class.java)

        viewModel.allDonors.observe(viewLifecycleOwner, Observer {
            adapter.updateDonorList(it)
        })

    }
}