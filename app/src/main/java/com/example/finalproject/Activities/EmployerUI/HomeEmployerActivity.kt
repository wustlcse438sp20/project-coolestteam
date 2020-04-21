package com.example.finalproject.Activities.EmployerUI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.example.finalproject.Activities.LoginActivity
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_employer.*
import kotlin.math.abs

class HomeEmployerActivity : AppCompatActivity() {
    private lateinit var logoutButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var createJobButton: ImageButton
    private lateinit var matchButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var gestureDetector: GestureDetectorCompat
    var auth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    var i = 0

//    lateinit var postingList: MutableList<Map<String, Any>>
//    lateinit var employerList: MutableList<Map<String, Any>>

    lateinit var employeeList: MutableList<Map<String, Any>>
    lateinit var docIds: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home_employer)
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

            else if(xDif > 0 && abs(xDif) > 500){
                val newPostingMatchMap: MutableMap<String, Any> = HashMap()
                var currUserData = db.collection("Employers").document(auth.currentUser!!.uid).get()

                    .addOnSuccessListener { document ->
                        newPostingMatchMap["name"] = document.data!!.get("name")

                    }
                    .addOnFailureListener { e ->
                        Log.d("blah", e.toString())
                        curEmployeeMatch = EmployeeMatch()
                    }
                newPostingMatchMap["name"] = ""


                db.collection("Employee").document(docIds[i]).
                    .collection("Matches").document(postingList[postIndex].id)
                    .collection("Matches").add(newEmployeeMatchMap)
                    .addOnSuccessListener{
                        Toast.makeText(this, "Posting Discarded", Toast.LENGTH_SHORT)

                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "Failed to insert data!", Toast.LENGTH_LONG)
                    }
            }
            }


            return true


        }
    }
}
