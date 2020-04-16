package com.example.finalproject.Activities


import android.content.Intent
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.Data.GeneralEmployee
import com.example.finalproject.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import kotlin.collections.HashMap


class LoginActivity: AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var emailLogin: EditText
    private lateinit var passwordLogin: EditText
    private lateinit var signUpButton: Button
    private lateinit var loginButton: Button
    private lateinit var githubLoginButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        githubLoginButton = github_login_btn
        signUpButton = signup_btn2
        loginButton = login_btn
        emailLogin = login_email
        passwordLogin = login_Password


        var currentUser = firebaseAuth.currentUser
        Log.d("blah", currentUser.toString())
        if (currentUser != null) {
            startNewActivity(MainActivity::class.java)
        } else {
            loginButton.setOnClickListener{
                emailLoginFlow()
            }
            githubLoginButton.setOnClickListener{
                githubLogin()
            }
            signUpButton.setOnClickListener {
                startNewActivity(SignupActivity::class.java)
            }
        }

    }




    fun githubLogin(){

        val provider = OAuthProvider.newBuilder("github.com")
        var scopes = listOf("read:user, repo") //Github auto grants read access to public info (Public repos)
        provider.setScopes(scopes)

        val pendingResultTask = firebaseAuth.pendingAuthResult
        if (pendingResultTask != null) { // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener {authRes ->
                        Log.d("blah", "AuthRes: "+ authRes.toString())
                        Log.d("blah", "AuthRes.getAdditionalUserInfo: "+ authRes.additionalUserInfo.toString())
                        Log.d("blah", "AuthRes.getAssitionalUserInfo.profile: "+ authRes.additionalUserInfo?.profile.toString())
                        Log.d("blah", "AuthRes.getCredential(): "+ authRes.credential.toString())
//                        Log.d("blah", "AuthRes.credentail.accesToken: "+ authRes.credential.t)
                        Log.d("blah", "SUCCESS")
                        var pic = authRes.additionalUserInfo?.profile!!["avatar_url"].toString()
                        var repos = authRes.additionalUserInfo?.profile!!["repos_url"].toString()
                        var url = authRes.additionalUserInfo?.profile!!["html_url"].toString()
                        var bio = authRes.additionalUserInfo?.profile!!["bio"].toString()
                        var name = authRes.additionalUserInfo?.profile!!["name"].toString()
                        Log.d("blah", authRes.additionalUserInfo?.profile!!::class.qualifiedName.toString())
                        var profile = GeneralEmployee(name, null, bio, url, pic, repos)
                        startNewActivity(MainActivity::class.java, profile)
                    }
                    .addOnFailureListener {e ->
                        // Handle failure.
                        Log.d("blah", "Error: " + e.toString())
                    }
        } else { // There's no pending result so you need to start the sign-in flow.
            firebaseAuth.startActivityForSignInWithProvider(this, provider.build())
                    .addOnSuccessListener {res ->
//                        var currentU = firebaseAuth.currentUser
                        Log.d("blah", "TEST HERE: "+res.additionalUserInfo?.profile.toString())
                        var pic = res.additionalUserInfo?.profile!!["avatar_url"].toString()
                        var repos = res.additionalUserInfo?.profile!!["repos_url"].toString()
                        var url = res.additionalUserInfo?.profile!!["html_url"].toString()
                        var bio = res.additionalUserInfo?.profile!!["bio"].toString()
                        var name = res.additionalUserInfo?.profile!!["name"].toString()
                        Log.d("blah", res.additionalUserInfo?.profile!!::class.qualifiedName.toString())
                        var profile = GeneralEmployee(name, null, bio, url, pic, repos)

//                        Log.d("blah", "acccess Token: "+res.credential.getAccessToken())
                        startNewActivity(MainActivity::class.java, profile)
                    }
                    .addOnFailureListener {e ->
                        Log.d("blah", "Error: "+e.toString())
                    }
        }


    }

    fun emailLoginFlow(){
        var emailLogin: String = emailLogin.text.toString()
        var passwordLogin: String = passwordLogin.text.toString()
        if (emailLogin == "" || passwordLogin == "") {
            Toast.makeText(this, "Please enter valid email or password", Toast.LENGTH_LONG)
                    .show()
        } else {
            firebaseAuth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                            startNewActivity(MainActivity::class.java)
                        } else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                        }
                    })
        }
    }

    fun startNewActivity(activity: Class<*>){
        val intent = Intent(this, activity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    fun startNewActivity(activity: Class<*>, profile: GeneralEmployee){
        val intent = Intent(this, activity)
        intent.putExtra("profile", profile)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }



}





