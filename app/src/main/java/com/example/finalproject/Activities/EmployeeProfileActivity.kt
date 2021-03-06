package com.example.finalproject.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.Data.*
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_employee_profile.*
import java.util.*

class EmployeeProfileActivity: AppCompatActivity() {

    lateinit var createProfile: Button
    lateinit var auth: FirebaseAuth
    private var generalProfile: GeneralEmployee = GeneralEmployee()
    private lateinit var db: FirebaseFirestore
    private var intentHasProfileData = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_profile)
        if(intent.hasExtra("profile")){
            generalProfile = intent.getSerializableExtra("profile") as GeneralEmployee
            intentHasProfileData = true
        }

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
            var currUser = auth.currentUser?.uid.toString()
            var email = FirebaseAuth.getInstance().currentUser?.email.toString()


            //Get values and put them in map to insert into collection
            val newEmployeeMap: MutableMap<String, Any> = HashMap()

            var newEmployee = Employee(
                name,
                school,
                major,
                age,
                    email,
                    GeneralEmployee(),
                    mutableListOf<Education>(),
                    mutableListOf<Hobby>(),
                    mutableListOf<Project>(),
                    mutableListOf<TechnicalSkill>(),
                    mutableListOf<WorkExperience>()
            )

            if(intentHasProfileData){
                newEmployee.general = generalProfile
            }

            newEmployeeMap["name"] = newEmployee.name
            newEmployeeMap["age"] = newEmployee.age
            newEmployeeMap["school"] = newEmployee.school
            newEmployeeMap["major"] = newEmployee.major
            newEmployeeMap["email"] = newEmployee.email
            newEmployeeMap["general"] = newEmployee.general
            newEmployeeMap["educations"] = newEmployee.educations
            newEmployeeMap["hobbies"] = newEmployee.hobbies
            newEmployeeMap["projects"] = newEmployee.projects
            newEmployeeMap["technicalSkills"] = newEmployee.technicalSkills
            newEmployeeMap["workExperiences"] = newEmployee.workExperiences

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