package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailLogin: EditText
    private lateinit var passwordLogin: EditText

    private lateinit var signUpButton: Button
    private lateinit var loginButton: Button




    private lateinit var db: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        db.setFirestoreSettings(settings)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        val twitterAuthProvider = OAuthProvider.newBuilder("twitter.com")
            .build()

        emailLogin = login_email
        passwordLogin = login_Password


        loginButton = login_btn
        signUpButton = signup_btn2



        loginButton.setOnClickListener {
            var emailLogin: String = emailLogin.text.toString()
            var passwordLogin: String = passwordLogin.text.toString()

            if (emailLogin == "" || passwordLogin == "") {
                Toast.makeText(this, "Please enter valid email or password", Toast.LENGTH_LONG)
                    .show()
            } else {
                auth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }




    }


}
