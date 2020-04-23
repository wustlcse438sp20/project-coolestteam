package com.example.finalproject.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Adapters.EducationAdapter
import com.example.finalproject.Adapters.StringListAdapter
import com.example.finalproject.Adapters.WorkExperienceAdapter
import com.example.finalproject.Data.Education
import com.example.finalproject.Data.Employee
import com.example.finalproject.Data.EmployeeMatch
import com.example.finalproject.Data.WorkExperience

import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_employee.*
import kotlinx.android.synthetic.main.fragment_employer_view_employee_profile.view.*


class EmployerViewEmployeeProfile : Fragment() {
    private lateinit var db: FirebaseFirestore
    private var firebase = FirebaseAuth.getInstance()
    private lateinit var currentEmployee: Employee
    private lateinit var displayName: TextView
    private lateinit var email: TextView
    private lateinit var recyclerView: RecyclerView
    private var mergeAdapter: MergeAdapter = MergeAdapter()
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var educationList = ArrayList<Education>()
    private var workExperienceList = ArrayList<WorkExperience>()
    private var hobbyList = ArrayList<String>()
    private var technicalSkillList = ArrayList<String>()
    private lateinit var educationAdapter: EducationAdapter
    private lateinit var workExperienceAdapter: WorkExperienceAdapter
    private lateinit var hobbyStringListAdapter: StringListAdapter
    private lateinit var technicalSkillListAdapter: StringListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.fragment_employer_view_employee_profile, container, false)
        email = v.user_email
        displayName = v.user_display_name
        // Inflate the layout for this fragment
        var employeeMatch: EmployeeMatch = arguments!!.getSerializable("employee") as EmployeeMatch
        viewManager = LinearLayoutManager(this.context)
        educationAdapter = EducationAdapter(educationList)
        workExperienceAdapter = WorkExperienceAdapter(workExperienceList)
        technicalSkillListAdapter = StringListAdapter(technicalSkillList, this.activity!!.applicationContext, "Technical Skills")
        hobbyStringListAdapter = StringListAdapter(hobbyList, this.activity!!.applicationContext, "Hobbies")
        mergeAdapter.addAdapter(educationAdapter)
        mergeAdapter.addAdapter(workExperienceAdapter)
        mergeAdapter.addAdapter(technicalSkillListAdapter)
        mergeAdapter.addAdapter(hobbyStringListAdapter)
        recyclerView = v.findViewById<RecyclerView>(R.id.section_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = mergeAdapter
        }
        db = Firebase.firestore


        loadProfile(employeeMatch.id)



        return v
    }
    fun loadProfile(id: String) {

        var uid = id
        hobbyStringListAdapter.notifyDataSetChanged()
        technicalSkillListAdapter.notifyDataSetChanged()

        var userDoc = db.collection("Employees").document(uid)
        userDoc.get()
                .addOnSuccessListener { docSnap ->
                    Log.d("blah", docSnap.data.toString())
                    currentEmployee = docSnap.toObject<Employee>()!!
                    Log.d("blah", currentEmployee.toString())
                    renderProfile()

                }
                .addOnFailureListener { e ->
                    Log.d("blah", e.toString())
                    currentEmployee = Employee()
                }
    }

    //Display user profile
    fun renderProfile() {
        technicalSkillList.clear()
        hobbyList.clear()
        educationList.clear()
        workExperienceList.clear()
        email.text = currentEmployee.email
        displayName.text = currentEmployee.name
        var profilePicView: ImageView = user_profile_image
        if (currentEmployee.general.pic != "") {
            Picasso.get().load(currentEmployee.general.pic).into(profilePicView)
        }
        for (item in currentEmployee.educations) {
            educationList.add(item)
        }
        for (item in currentEmployee.workExperiences) {
            workExperienceList.add(item)
        }
        for (hobby in currentEmployee.hobbies) {
            if (hobby.type != null && hobby.type != "") {
                hobbyList.add(hobby.type!!)
            }
        }
        for (item in currentEmployee.technicalSkills) {
            if (item.skill != null && item.skill != "") {
                technicalSkillList.add(item.skill!!)
            }
        }
        educationAdapter.notifyDataSetChanged()
        workExperienceAdapter.notifyDataSetChanged()
        hobbyStringListAdapter.notifyDataSetChanged()
        technicalSkillListAdapter.notifyDataSetChanged()
        mergeAdapter.notifyDataSetChanged()
        if (technicalSkillList.size == 0) {
            mergeAdapter.removeAdapter(technicalSkillListAdapter)

        }
        if (hobbyList.size == 0) {
            mergeAdapter.removeAdapter(hobbyStringListAdapter)

        }
        if (educationList.size == 0) {
            mergeAdapter.removeAdapter(educationAdapter)

        }
        if (workExperienceList.size == 0) {
            mergeAdapter.removeAdapter(workExperienceAdapter)

        }
    }


}
