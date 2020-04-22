package com.example.finalproject.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.Data.Employer
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_employer_profile.*
import java.util.HashMap

class EmployerProfileActivity: AppCompatActivity() {

    lateinit var createPosting: Button
    lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employer_profile)

        //Firebase Initializations
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        createPosting = create_posting_button
        createPosting.setOnClickListener {
            //Get values from the profile page
            var name: String = employer_name.text.toString()
            var company: String = employer_company.text.toString()


            //Get UID of current user to place values in correct doc
            var currUser = auth.currentUser?.uid.toString()

            //Get values and put them in map to insert into collection
            val newEmployerMap: MutableMap<String, Any> = HashMap()


            var newEmployer = Employer(
                name,
                company

            )

            newEmployerMap["name"] = newEmployer.name
            newEmployerMap["company"] = newEmployer.company


            //add to the database
            db.collection("Employers")
                .document(currUser).set(newEmployerMap)
                .addOnSuccessListener{
                    Toast.makeText(this, "Profile created", Toast.LENGTH_LONG)
                    val intent = Intent(this, CreateJobPostingActivity::class.java)
                    intent.putExtra("uid", currUser)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed to insert data!", Toast.LENGTH_LONG)
                }
        }
    }
}