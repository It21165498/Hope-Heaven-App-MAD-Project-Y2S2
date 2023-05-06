package com.example.hopeheaven.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hopeheaven.R
import com.example.hopeheaven.models.StudentModel

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.ViewHolder>(){

    private val studentList= ArrayList<StudentModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  studentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentStudent = studentList[position]

        holder.studentName.text = currentStudent.name
        holder.studentNeeds.text=currentStudent.needs



    }

    fun updateStudentList(studentlist:List<StudentModel>){
        this.studentList.clear()
        this.studentList.addAll(studentlist)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var studentProPic : ImageView
        var studentName : TextView
        var studentNeeds : TextView

        init {
            studentProPic = itemView.findViewById(R.id.StudentPhoto)
            studentName = itemView.findViewById(R.id.StudentName)
            studentNeeds = itemView.findViewById(R.id.StudentNeeds)

        }

    }
}

