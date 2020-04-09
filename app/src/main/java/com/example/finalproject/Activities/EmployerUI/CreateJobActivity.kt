package com.example.finalproject.Activities.EmployerUI


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.Activities.MainActivity
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_create_job.*

class CreateJobActivity : AppCompatActivity() {
    private lateinit var logoutButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var createJobButton: ImageButton
    private lateinit var matchButton: ImageButton
    private lateinit var homeButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_job)
        logoutButton = logout_button
        profileButton = profile_button
        createJobButton = create_job_button
        matchButton = match_button
        homeButton = home_button
        logoutButton.setOnClickListener { v -> changeActivity(v, MainActivity::class.java, true) }
        createJobButton.setOnClickListener { v -> changeActivity(v, CreateJobActivity::class.java, false) }
        profileButton.setOnClickListener { v -> changeActivity(v, ProfileEmployerActivity::class.java, false) }
        matchButton.setOnClickListener { v -> changeActivity(v, MatchesEmployerActivity::class.java, false)}
        homeButton.setOnClickListener{ v -> changeActivity(v, HomeEmployerActivity::class.java, false)}
    }
    fun changeActivity(view: View, activity: Class<*>, isLogout: Boolean){
        val intent = Intent(this, activity)
        if(isLogout){
            FirebaseAuth.getInstance().signOut()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
