package com.example.finalproject.Activities.EmployerUI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.example.finalproject.Activities.LoginActivity
import com.example.finalproject.Data.PostMatch
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_employer.create_job_button
import kotlinx.android.synthetic.main.activity_home_employer.home_button
import kotlinx.android.synthetic.main.activity_home_employer.logout_button
import kotlinx.android.synthetic.main.activity_home_employer.match_button
import kotlinx.android.synthetic.main.activity_home_employer.profile_button
import kotlinx.android.synthetic.main.activity_post_home_employer_activity.*

import kotlin.math.abs


class PostHomeEmployerActivity: AppCompatActivity() {

    private lateinit var logoutButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var createJobButton: ImageButton
    private lateinit var matchButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var gestureDetector: GestureDetectorCompat
    var auth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()
    var currId = ""

    var i = 0

//    lateinit var postingList: MutableList<Map<String, Any>>
//    lateinit var employerList: MutableList<Map<String, Any>>

    lateinit var employeeList: MutableList<Map<String, Any>>
    lateinit var docIds: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_post_home_employer_activity)
        gestureDetector = GestureDetectorCompat(this, MyGestureListener())

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
        currId = intent.getStringExtra("docId")
        employeeList = arrayListOf()
        docIds = arrayListOf()

        db.collection("Employees").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    docIds.add(document.id)
                    employeeList.add(document.data)
                }
                Log.d("here", "${employeeList}")
                employee_name_home.text = employeeList[i].get("name").toString()
                employee_age_home.text = employeeList[i].get("age").toString()
                employee_school_home.text = employeeList[i].get("school").toString()
                employee_major_home.text = employeeList[i].get("major").toString()
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val xDif = e2.rawX - e1.rawX
            val yDif = e2.rawY - e1.rawY

            //on right swipe
            if (xDif < 0 && abs(xDif) > 500) {
                if(i<employeeList.size - 1) {
                    i += 1
                    employee_name_home.text = employeeList[i].get("name").toString()
                    employee_age_home.text = employeeList[i].get("age").toString()
                    employee_school_home.text = employeeList[i].get("school").toString()
                    employee_major_home.text = employeeList[i].get("major").toString()
                }
                else{
                    i = 0
                    employee_name_home.text = employeeList[i].get("name").toString()
                    employee_age_home.text = employeeList[i].get("age").toString()
                    employee_school_home.text = employeeList[i].get("school").toString()
                    employee_major_home.text = employeeList[i].get("major").toString()
                }
            }

            else if(xDif > 0 && abs(xDif) > 500) {
                val newPostingMatchMap: MutableMap<String, Any> = HashMap()
                var currUserData = db.collection("Employers").document(auth.currentUser!!.uid).get()
                    .addOnSuccessListener { document ->
                        db.collection("Employers").document(auth.currentUser!!.uid).collection(document.data!!.get("company").toString())
                            .document(currId).get()
                            .addOnSuccessListener { document ->
                                var postingMatch = PostMatch(
                                    document.data!!.get("Company").toString(),
                                    true,
                                    document.data!!.get("Job Title").toString(),
                                    document.data!!.get("Salary").toString().toInt()

                                )
                                newPostingMatchMap["company"] = postingMatch.Company
                                newPostingMatchMap["position"] = postingMatch.Position
                                newPostingMatchMap["salary"] = postingMatch.Salary
                                newPostingMatchMap["interested"] = postingMatch.Interested

                                val id = db.collection("Employees").document(docIds[i])
                                    .collection("Matches").document().id

                                db.collection("Employees").document(docIds[i])
                                    .collection("Matches").document(id)
                                    .set(newPostingMatchMap)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            this@PostHomeEmployerActivity,
                                            "Posting Added",
                                            Toast.LENGTH_SHORT
                                        )

                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            this@PostHomeEmployerActivity,
                                            "Failed to insert data!",
                                            Toast.LENGTH_LONG
                                        )
                                    }
                            }
                            }







                    .addOnFailureListener { e ->
                        Log.d("here", e.toString())
                    }


            }




            return true


        }
    }
}