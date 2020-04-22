package com.example.finalproject.Activities.EmployeeUI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.example.finalproject.Activities.LoginActivity
import com.example.finalproject.Data.EmployeeMatch
import com.example.finalproject.Data.Posting
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_home_employee.*
import kotlinx.android.synthetic.main.activity_home_employee.logout_button
import kotlinx.android.synthetic.main.activity_home_employee.match_button
import kotlinx.android.synthetic.main.activity_home_employee.profile_button
import java.util.HashMap

class HomeEmployeeActivity : AppCompatActivity(), GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{
    private lateinit var logoutButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var matchButton: ImageButton
    private lateinit var company: TextView
    private lateinit var position: TextView
    private lateinit var education: TextView
    private lateinit var salary: TextView
    private lateinit var postingList : ArrayList<Posting>
    private lateinit var firestore: FirebaseFirestore
    private lateinit var mDetector: GestureDetectorCompat
    private var swipedistance = 100
    private var postIndex: Int = 0
    private lateinit var curEmployeeMatch : EmployeeMatch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_employee)
        logoutButton = logout_button
        profileButton = profile_button
        matchButton = match_button
        logoutButton.setOnClickListener { v -> changeActivity(v, LoginActivity::class.java, true) }
        profileButton.setOnClickListener { v -> changeActivity(v, ProfileEmployeeActivity::class.java, false) }
        matchButton.setOnClickListener { v -> changeActivity(v, MatchesEmployeeActivity::class.java, false)}

        //text for textviews
        company = home_company_text
        position = home_position_text
        education = home_education_text
        salary = home_salary_text

        //prepare firestore and list to populate with listings
        firestore = FirebaseFirestore.getInstance()
        postingList = arrayListOf()

        populateList()

        //get current user information
        var userDoc = firestore.collection("Employees").document(FirebaseAuth.getInstance().currentUser!!.uid)
        userDoc.get()
                .addOnSuccessListener { docSnap ->
                    Log.d("blah", docSnap.data.toString())
                    curEmployeeMatch = docSnap.toObject<EmployeeMatch>()!!
                    Log.d("blah", curEmployeeMatch.toString())
                }
                .addOnFailureListener { e ->
                    Log.d("blah", e.toString())
                    curEmployeeMatch = EmployeeMatch()
                }

        // get detector ready for gestures and adding matches
        mDetector = GestureDetectorCompat(this, this)
        mDetector.setOnDoubleTapListener(this)
    }

    // populate the list of potential postings
    fun populateList(){
        firestore.collection("Employers").get().addOnSuccessListener { result ->
            for (document in result) {
                firestore.collection("Employers").document(document.id)
                        .collection("Postings").get()
                        .addOnSuccessListener { documents ->
                            Log.d("check success", "main check")
                            for (doc in documents) {
                                Log.d("check", doc.data.toString())
                                var curPost = doc.toObject<Posting>()

                                curPost.companyid = document.id
                                curPost.id = doc.id
                                Log.d("check company id", document.id)
                                Log.d("check id", doc.id)
                                if(curPost.company != "") {
                                    postingList.add(curPost)
                                }
                                loadPosting()
                            }
                        }
            }
        }.addOnFailureListener { exception -> Log.w("TAG", "ERROR", exception) }
    }

    // choose a random posting to show the user
    fun loadPosting(){
        if(postingList.size > 0) {
            Log.d("check", "inside load")
            postIndex = (0..postingList.size-1).random()

            company.text = "Company: " + postingList[postIndex].company
            position.text = "Position: " + postingList[postIndex].position
            education.text = "Education: " + postingList[postIndex].education
            salary.text = "Salary: " + postingList[postIndex].salary.toString()
        }
        else{
            Log.d("check", "inside load else")
            Toast.makeText(this, "No postings to show", Toast.LENGTH_LONG).show()
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

    fun addMatch(){
        val newEmployeeMatchMap: MutableMap<String, Any> = HashMap()
        curEmployeeMatch.interested = true

        newEmployeeMatchMap["name"] = curEmployeeMatch.name
        newEmployeeMatchMap["school"] = curEmployeeMatch.school
        newEmployeeMatchMap["age"] = curEmployeeMatch.age
        newEmployeeMatchMap["major"] = curEmployeeMatch.major
        newEmployeeMatchMap["interested"] = curEmployeeMatch.interested

//        var id = postingList[postIndex].companyid
//        Log.d("check addMatch id", postingList[postIndex].companyid)

        //TODO add to the database
        firestore.collection("Employers").document(postingList[postIndex].companyid)
                .collection("Postings").document(postingList[postIndex].id)
                .collection("Matches").add(newEmployeeMatchMap)
                .addOnSuccessListener{
                    Toast.makeText(this, "Posting Liked", Toast.LENGTH_SHORT)
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed to insert data!", Toast.LENGTH_LONG)
                }
    }

    fun addNoMatch(){
        val newEmployeeMatchMap: MutableMap<String, Any> = HashMap()
        curEmployeeMatch.interested = false

        newEmployeeMatchMap["name"] = curEmployeeMatch.name
        newEmployeeMatchMap["school"] = curEmployeeMatch.school
        newEmployeeMatchMap["age"] = curEmployeeMatch.age
        newEmployeeMatchMap["major"] = curEmployeeMatch.major
        newEmployeeMatchMap["interested"] = curEmployeeMatch.interested

        //TODO add to the database
        firestore.collection("Employers").document(postingList[postIndex].companyid)
                .collection("Postings").document(postingList[postIndex].id)
                .collection("Matches").add(newEmployeeMatchMap)
                .addOnSuccessListener{
                    Toast.makeText(this, "Posting Discarded", Toast.LENGTH_SHORT)

                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed to insert data!", Toast.LENGTH_LONG)
                }
    }

    // gesture detection below
    companion object {
        private const val TAG = "MainActivity"
        private const val DEBUG_TAG = "Gestures"
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onFling(
            event1: MotionEvent,
            event2: MotionEvent,
            velocityX: Float,
            velocityY: Float
    ): Boolean {
        if(event2.x - event1.x > swipedistance) {
            addMatch()
        }
        else if (event1.x - event2.x > swipedistance){
            addNoMatch()
        }
        return false
    }

    override fun onShowPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onShowPress: $event")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapUp: $event")
        return true
    }

    override fun onDoubleTap(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDoubleTap: $event")
        return true
    }

    override fun onDown(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDown: $event")
        return true
    }

    override fun onScroll(
            event1: MotionEvent,
            event2: MotionEvent,
            distanceX: Float,
            distanceY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onScroll: $event1 $event2")
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onLongPress: $event")
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: $event")
        return true
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: $event")
        return true
    }
}
