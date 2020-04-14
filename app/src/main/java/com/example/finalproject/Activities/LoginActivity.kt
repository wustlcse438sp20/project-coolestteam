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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity: AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var db: FirebaseFirestore

    private lateinit var emailLogin: EditText
    private lateinit var passwordLogin: EditText
//
    private lateinit var signUpButton: Button
    private lateinit var loginButton: Button
    private lateinit var githubLoginButton: Button

//    val RC_SIGN_IN = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        githubLoginButton = github_login_btn
        signUpButton = signup_btn2
        loginButton = login_btn
        emailLogin = login_email
        passwordLogin = login_Password



//        provider.setScopes(scopes)

        var currentUser = firebaseAuth.currentUser
        Log.d("blah", currentUser.toString())
        if (currentUser != null) {
            startNewActivity(MainActivity::class.java)
        } else {
            githubLoginButton.setOnClickListener{
                githubLogin()
            }
            signUpButton.setOnClickListener {
                startNewActivity(SignupActivity::class.java)
            }
            loginButton.setOnClickListener{
                emailLoginFlow()
            }

        }

    }




    fun githubLogin(){

        val provider = OAuthProvider.newBuilder("github.com")
        var scopes = listOf("read:user") //Github auto grants read access to public info (Public repos)
        provider.setScopes(scopes)

        val pendingResultTask = firebaseAuth.pendingAuthResult
        if (pendingResultTask != null) { // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener {
                        Log.d("blah", "SUCCESS")
                        startNewActivity(MainActivity::class.java)
                    }
                    .addOnFailureListener {e ->
                        // Handle failure.
                        Log.d("blah", "Error: " + e.toString())
                    }
        } else { // There's no pending result so you need to start the sign-in flow.
            firebaseAuth.startActivityForSignInWithProvider(this, provider.build())
                    .addOnSuccessListener {res ->
//                        var currentU = firebaseAuth.currentUser
                        Log.d("blah", "TEST HERE: "+res.user.toString())

//                        Log.d("blah", res.additionalUserInfo.toString())
                        Log.d("blah", "USER SIGNED IN")
                        Log.d("blah", firebaseAuth.currentUser?.uid.toString() ) //r7P3lORglbPGqVN9SVzMCdOOxAk2
                        Log.d("blah", firebaseAuth.currentUser?.displayName.toString() ) //Joe Frazier
                        Log.d("blah", firebaseAuth.currentUser?.email.toString()) //frazierj@wustl.edu
                        Log.d("blah", firebaseAuth.currentUser?.phoneNumber.toString() ) //null
                        Log.d("blah", firebaseAuth.currentUser?.providerId.toString() ) //firebase

                        startNewActivity(MainActivity::class.java)
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




}




//        auth = FirebaseAuth.getInstance()
//        var currentUser = auth.currentUser
//        Log.d("blah",currentUser.toString())
//        if(currentUser != null){
//            val intent = Intent(this, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//        }
//        else{
//            Log.d("blah"," HIHIHIB")
//            showSignInOptions()
//        }
//
//        showSignInOptions()
//    }
//
//    override fun onResume(){
//        super.onResume()
//
//        var currentUser = FirebaseAuth.getInstance().currentUser
//        if(currentUser != null){
//            val intent = Intent(this, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//        }
//        Log.d("blah", "RESUMED")
//
//
//    }
//
//
//    fun showSignInOptions(){
//        val providers = arrayListOf(
//                AuthUI.IdpConfig.EmailBuilder().build(),
//                AuthUI.IdpConfig.GitHubBuilder().build())
//
//        startActivityForResult(
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(providers)
//                        .setIsSmartLockEnabled(false)
//                        .build(),
//                RC_SIGN_IN
//        )
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val intent = Intent(this, MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//        if (requestCode == RC_SIGN_IN) {
////            val response = IdpResponse.fromResultIntent(data)
////            Log.d("BLAH", data.toString())
//            if (resultCode == Activity.RESULT_OK) {
//                // Successfully signed in
////                var currentUser = FirebaseAuth.getInstance().currentUser
////                var userId = currentUser!!.uid
////                Log.d("BLAH", currentUser.toString())
//                val intent = Intent(this, MainActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
//
//                // ...
//            } else {
//                Log.d("blah", "ACTIVITY FOR RESULT NOT OKAY")
//                showSignInOptions()
//                // Sign in failed. If response is null the user canceled the
//                // sign-in flow using the back button. Otherwise check
//                // response.getError().getErrorCode() and handle the error.
//                // ...
//            }
//        }
//        else{
//            Log.d("blah", "ERRORORORR")
//        }
//
//    }








//        auth = FirebaseAuth.getInstance()
//        db = FirebaseFirestore.getInstance()
//        setContentView(R.layout.activity_login)
//
//        emailLogin = login_email
//        passwordLogin = login_Password
//        loginButton = login_btn
//        signUpButton = signup_btn2
//
////        auth = FirebaseAuth.getInstance()
////        val twitterAuthProvider = OAuthProvider.newBuilder("twitter.com")
////            .build()
//
//
//        loginButton.setOnClickListener {
//            var emailLogin: String = emailLogin.text.toString()
//            var passwordLogin: String = passwordLogin.text.toString()
//
//            if (emailLogin == "" || passwordLogin == "") {
//                Toast.makeText(this, "Please enter valid email or password", Toast.LENGTH_LONG)
//                    .show()
//            } else {
//                auth.signInWithEmailAndPassword(emailLogin, passwordLogin)
//                    .addOnCompleteListener(this, OnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
//                            val intent = Intent(this, MainActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        } else {
//                            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
//                        }
//                    })
//            }
//        }
//
//        signUpButton.setOnClickListener {
//            val intent = Intent(this, SignupActivity::class.java)
//            startActivity(intent)
//            finish()
//        }











//}


