package com.example.finalproject.Adapters


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Data.EmployeeMatch
import com.example.finalproject.Fragments.EmployerViewEmployeeProfile
import com.example.finalproject.R
import kotlinx.android.synthetic.main.match_item_employer.view.*


class MatchesViewHolderEmployer(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.match_item_employer, parent, false)) {
    private lateinit var profileMajor: TextView
    private lateinit var profileName: TextView
    private lateinit var profileSchool: TextView


    init {
        profileMajor = itemView.employee_major
        profileName = itemView.employee_display_name
        profileSchool = itemView.employee_school

    }

    fun bind(employee: EmployeeMatch) {
        profileName.text = employee.name
        profileSchool.text = employee.school
        profileMajor.text = employee.major


            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("employee", employee)
                var fragment = EmployerViewEmployeeProfile()
                fragment.arguments = bundle
                val transaction: FragmentTransaction = (it.context as FragmentActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
                transaction.commit()
            }

    }
}

//create the listener for the recycler view
class EmployerMatchesAdapter(private val list: ArrayList<EmployeeMatch>) : RecyclerView.Adapter<MatchesViewHolderEmployer>() {
//    private var listEvents: ArrayList<EmployeeMatch>? = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolderEmployer {
        val inflater = LayoutInflater.from(parent.context)
        return MatchesViewHolderEmployer(inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: MatchesViewHolderEmployer, position: Int) {
        val employeeMatchObj: EmployeeMatch = list[position]
        holder.bind(employeeMatchObj)
    }

    //set the count
    override fun getItemCount(): Int = list.size

}