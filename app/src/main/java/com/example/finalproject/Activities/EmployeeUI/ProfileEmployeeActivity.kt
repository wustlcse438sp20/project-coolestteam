package com.example.finalproject.Activities.EmployeeUI


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Activities.LoginActivity
import com.example.finalproject.Adapters.EducationAdapter
import com.example.finalproject.Adapters.StringListAdapter
import com.example.finalproject.Adapters.WorkExperienceAdapter
import com.example.finalproject.Data.Education
import com.example.finalproject.Data.Employee
import com.example.finalproject.Data.WorkExperience
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_employee.*
import kotlinx.coroutines.flow.merge

class ProfileEmployeeActivity : AppCompatActivity() {
    private lateinit var logoutButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var matchButton: ImageButton
    private lateinit var db: FirebaseFirestore
    private  var firebase = FirebaseAuth.getInstance()
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_employee)

        //Recycler View Setup
        viewManager = LinearLayoutManager(this)
        educationAdapter = EducationAdapter(educationList)
        workExperienceAdapter = WorkExperienceAdapter(workExperienceList)
        technicalSkillListAdapter = StringListAdapter(technicalSkillList, this, "Technical Skills")
        hobbyStringListAdapter = StringListAdapter(hobbyList, this, "Hobbies")
        mergeAdapter.addAdapter(educationAdapter)
        mergeAdapter.addAdapter(workExperienceAdapter)
        mergeAdapter.addAdapter(hobbyStringListAdapter)
        mergeAdapter.addAdapter(technicalSkillListAdapter)
        recyclerView = findViewById<RecyclerView>(R.id.section_list).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = mergeAdapter
        }

        //Firestore
        db = Firebase.firestore

        //Navigation Buttons
        logoutButton = logout_button
        homeButton = home_button
        matchButton = match_button
        displayName = user_display_name
        email = user_email
        logoutButton.setOnClickListener { v -> changeActivity(v, LoginActivity::class.java, true) }
        homeButton.setOnClickListener { v -> changeActivity(v, HomeEmployeeActivity::class.java, false) }
        matchButton.setOnClickListener { v -> changeActivity(v, MatchesEmployeeActivity::class.java, false)}

        //Add data to recycler view
        loadProfile()

    }

    //Get current user data
    fun loadProfile(){
        var currentUser = firebase.currentUser
        var uid = currentUser!!.uid
        email.text = currentUser.email
        var userDoc = db.collection("Employees").document(uid)
        userDoc.get()
                .addOnSuccessListener{ docSnap->
                    Log.d("blah", docSnap.data.toString())
                    currentEmployee = docSnap.toObject<Employee>()!!
                    Log.d("blah", currentEmployee.toString())
                    renderProfile()
                }
                .addOnFailureListener { e->
                    Log.d("blah", e.toString())
                    currentEmployee = Employee()
                }
    }

    //Display user profile
    fun renderProfile(){

        displayName.text = currentEmployee.name
        var profilePicView: ImageView = user_profile_image
        if(currentEmployee.general.pic != "") {
            Picasso.get().load(currentEmployee.general.pic).into(profilePicView)
        }
        for(item in currentEmployee.educations){
            educationList.add(item)

        }
        for(item in currentEmployee.workExperiences){
            workExperienceList.add(item)

        }
        for(hobby in currentEmployee.hobbies){
            if(hobby.type != null && hobby.type !=""){
                hobbyList.add(hobby.type!!)
            }

        }
        for(item in currentEmployee.technicalSkills){
            if(item.skill != null && item.skill !=""){
                technicalSkillList.add(item.skill!!)
            }


        }
        educationAdapter.notifyDataSetChanged()
        workExperienceAdapter.notifyDataSetChanged()
        hobbyStringListAdapter.notifyDataSetChanged()
        technicalSkillListAdapter.notifyDataSetChanged()
        if(technicalSkillList.size == 0){
            mergeAdapter.removeAdapter(technicalSkillListAdapter)
        }
        if(hobbyList.size == 0){
            mergeAdapter.removeAdapter(hobbyStringListAdapter)
        }
        if(educationList.size == 0){
            mergeAdapter.removeAdapter(educationAdapter)
        }
        if(workExperienceList.size == 0){
            mergeAdapter.removeAdapter(workExperienceAdapter)
        }
    }


    fun addSection(view: View){
        Toast.makeText(this, "HELLO", Toast.LENGTH_LONG).show()
    }


    fun changeActivity(view: View, activity: Class<*>, isLogout: Boolean){
        val intent = Intent(this, activity)
        if(isLogout){
            FirebaseAuth.getInstance().signOut()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
