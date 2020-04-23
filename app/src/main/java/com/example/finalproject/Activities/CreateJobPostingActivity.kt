package com.example.finalproject.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.Data.Posting
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_posting.*
import java.util.HashMap

class CreateJobPostingActivity: AppCompatActivity() {

    lateinit var createPosting: Button
    lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_posting)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        createPosting = job_create_posting_button

        createPosting.setOnClickListener {
        //Get values from the profile page
            var position: String = job_title.text.toString()
            var company: String = job_company.text.toString()
            var education: String = job_education.text.toString()
            var salary: Int = job_salary.text.toString().toInt()

            //Get UID of current user to place values in correct doc
            var currUser = intent.getStringExtra("uid")

            if(currUser == null){
                currUser = auth.currentUser!!.uid
            }


            //Get values and put them in map to insert into collection
            val newPostingMap: MutableMap<String, Any> = HashMap()

            var newPosting = Posting(
                company,
                position,
                education,
                salary

            )
            newPosting.email =  auth.currentUser?.email.toString()

            newPostingMap["position"] = newPosting.position
            newPostingMap["company"] = newPosting.company
            newPostingMap["education"] = newPosting.education
            newPostingMap["salary"] = newPosting.salary
            newPostingMap["email"] = newPosting.email

            //add to the database
            //TODO make sure this change no break stuff
            db.collection("Employers").document(currUser)
                .collection("Postings").add(newPostingMap)
                .addOnSuccessListener{
                    Toast.makeText(this, "Posting created", Toast.LENGTH_LONG)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed to insert data!", Toast.LENGTH_LONG)
                }

        }
    }
}