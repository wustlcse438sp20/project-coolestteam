package com.example.finalproject.Activities.EmployeeUI


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Activities.LoginActivity
import com.example.finalproject.Adapters.EducationAdapter
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
    private lateinit var mergeAdapter: MergeAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var educationList = ArrayList<Education>()
    private var workExperienceList = ArrayList<WorkExperience>()
    private lateinit var educationAdapter: EducationAdapter
    private lateinit var workExperienceAdapter: WorkExperienceAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_employee)

        viewManager = LinearLayoutManager(this)
        educationAdapter = EducationAdapter(educationList)
        workExperienceAdapter = WorkExperienceAdapter(workExperienceList)
        mergeAdapter = MergeAdapter(educationAdapter, workExperienceAdapter)
        recyclerView = findViewById<RecyclerView>(R.id.section_list).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = mergeAdapter
        }


        db = Firebase.firestore
        logoutButton = logout_button
        homeButton = home_button
        matchButton = match_button
        displayName = user_display_name
        email = user_email
        logoutButton.setOnClickListener { v -> changeActivity(v, LoginActivity::class.java, true) }
        homeButton.setOnClickListener { v -> changeActivity(v, HomeEmployeeActivity::class.java, false) }
        matchButton.setOnClickListener { v -> changeActivity(v, MatchesEmployeeActivity::class.java, false)}
        loadProfile()


    }


    fun loadProfile(){
        var currentUser = firebase.currentUser
        var uid = currentUser!!.uid
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
    fun renderProfile(){
        Log.d("blah", "Render")
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

        educationAdapter.notifyDataSetChanged()
        workExperienceAdapter.notifyDataSetChanged()
//        mergeAdapter.notifyDataSetChanged()


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
