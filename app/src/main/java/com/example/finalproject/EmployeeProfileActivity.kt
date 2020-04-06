package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.Data.Employee
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_employee_profile.*
import java.util.HashMap

class EmployeeProfileActivity: AppCompatActivity() {

    lateinit var createProfile: Button
    lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_profile)

        //Firebase Initializations
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        createProfile = create_profile_button
        createProfile.setOnClickListener {
            //Get values from the profile page
            var name: String = employee_name.text.toString()
            var school: String = employee_school.text.toString()
            var age: Int = employee_age.text.toString().toInt()
            var major: String = employee_major.text.toString()

            //Get UID of current user to place values in correct doc
            var currUser = intent.getStringExtra("uid")

            //Get values and put them in map to insert into collection
            val newEmployeeMap: MutableMap<String, Any> = HashMap()


            var newEmployee = Employee(
                name,
                school,
                major,
                age
            )

            newEmployeeMap["name"] = newEmployee.name
            newEmployeeMap["age"] = newEmployee.age
            newEmployeeMap["school"] = newEmployee.school
            newEmployeeMap["major"] = newEmployee.major

            //add to the database
            db.collection("Employees")
                .document(currUser).set(newEmployeeMap)
                .addOnSuccessListener{
                    Toast.makeText(this, "Profile created", Toast.LENGTH_LONG)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed to insert data!", Toast.LENGTH_LONG)
                }
        }
    }
}