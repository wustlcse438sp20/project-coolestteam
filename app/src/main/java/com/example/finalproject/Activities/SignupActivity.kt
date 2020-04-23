package com.example.finalproject.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var emailSignUp: EditText
    private lateinit var passwordSignUp: EditText
    private lateinit var Employee_signUpButton: Button
    private lateinit var Employer_signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build()
        db.setFirestoreSettings(settings)

        emailSignUp = newEmail
        passwordSignUp = newPassword
        Employee_signUpButton = employee_signup_btn
        Employee_signUpButton.setOnClickListener {
            var emailSign: String = emailSignUp.text.toString()
            var passwordSign: String = passwordSignUp.text.toString()

            if (emailSign == "" || passwordSign == "") {
                Toast.makeText(this, "Please enter valid email or password", Toast.LENGTH_LONG).show()
            } else {
                auth.createUserWithEmailAndPassword(emailSign, passwordSign).addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                        val userId = task.result!!.user!!.uid
                        val intent = Intent(this, EmployeeProfileActivity::class.java)
                        intent.putExtra("uid", userId)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e("here", "onComplete: Failed=" + task.exception!!.message)
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }

        }

        Employer_signUpButton = employer_signup_btn
        Employer_signUpButton.setOnClickListener {
            var emailSign: String = emailSignUp.text.toString()
            var passwordSign: String = passwordSignUp.text.toString()

            if (emailSign == "" || passwordSign == "") {
                Toast.makeText(this, "Please enter valid email or password", Toast.LENGTH_LONG)
                        .show()
            } else {
                auth.createUserWithEmailAndPassword(emailSign, passwordSign)
                        .addOnCompleteListener(this, OnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG)
                                        .show()
                                val userId = task.result!!.user!!.uid
                                val intent = Intent(this, EmployerProfileActivity::class.java)
                                intent.putExtra("uid", userId)
                                startActivity(intent)
                                finish()
                            } else {
                                Log.d("here", "onComplete: Failed=" + task.exception!!.message)
                                Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                            }
                        })
            }
        }
    }
}


