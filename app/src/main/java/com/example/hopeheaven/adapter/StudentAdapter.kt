package com.example.hopeheaven.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hopeheaven.R
import com.example.hopeheaven.models.StudentModel

class StudentAdapter(private val studentList: ArrayList<StudentModel>) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onDonateButtonClick(student: StudentModel)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.student_card, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentStudent = studentList[position]

        holder.studentName.text = currentStudent.name
        holder.studentNeeds.text = currentStudent.needs

        holder.button.setOnClickListener {
            listener?.onDonateButtonClick(currentStudent)
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var studentProPic: ImageView = itemView.findViewById(R.id.StudentPhoto)
        var studentName: TextView = itemView.findViewById(R.id.StudentName)
        var studentNeeds: TextView = itemView.findViewById(R.id.StudentNeeds)
        var button: Button = itemView.findViewById(R.id.btnDonate)
    }
}
