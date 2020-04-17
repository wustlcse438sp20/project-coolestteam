package com.example.finalproject.Activities.EmployerUI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.Activities.LoginActivity
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_employer.*

class HomeEmployerActivity : AppCompatActivity() {
    private lateinit var logoutButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var createJobButton: ImageButton
    private lateinit var matchButton: ImageButton
    private lateinit var homeButton: ImageButton

//    lateinit var postingList: MutableList<Map<String, Any>>
//    lateinit var employerList: MutableList<Map<String, Any>>

    lateinit var employeeList: MutableList<Map<String, Any>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home_employer)

        logoutButton = logout_button
        profileButton = profile_button
        createJobButton = create_job_button
        matchButton = match_button
        homeButton = home_button
        logoutButton.setOnClickListener { v -> changeActivity(v, LoginActivity::class.java, true) }
        createJobButton.setOnClickListener { v -> changeActivity(v, CreateJobActivity::class.java, false) }
        profileButton.setOnClickListener { v -> changeActivity(v, ProfileEmployerActivity::class.java, false) }
        matchButton.setOnClickListener { v -> changeActivity(v, MatchesEmployerActivity::class.java, false)}
        homeButton.setOnClickListener{ v -> changeActivity(v, HomeEmployerActivity::class.java, false)}

        var auth = FirebaseAuth.getInstance()
        var db = FirebaseFirestore.getInstance()



        //For Employers idk why I did this lmao
        /*
        employeeList = arrayListOf()
        postingList = arrayListOf()
        db.collection("Employers").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("here", "${document.data.get("company").toString()}")
                    var companyName = document.data.get("company").toString()
                    db.collection("Employers").document(document.id).collection(companyName).get()
                        .addOnSuccessListener { result ->
                            for(document2 in result) {
                                postingList.add(document2.data)
                                Log.d("here", "${postingList}")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d("here", "Error getting documents: ", exception)
                        }
                }

                R.layout.activity
            }
            .addOnFailureListener { exception ->
                Log.d("here", "Error getting documents: ", exception)
            } */

        //For employees
        employeeList = arrayListOf()
        db.collection("Employees").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    employeeList.add(document.data)
                }
                Log.d("here", "${employeeList}")
                employee_name_home.text = employeeList[0].get("name").toString()
                employee_age_home.text = employeeList[0].get("age").toString()
                employee_school_home.text = employeeList[0].get("school").toString()
                employee_major_home.text = employeeList[0].get("major").toString()
            }
            .addOnFailureListener { exception ->
                Log.d("here", "Error getting documents: ", exception)
            }


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
