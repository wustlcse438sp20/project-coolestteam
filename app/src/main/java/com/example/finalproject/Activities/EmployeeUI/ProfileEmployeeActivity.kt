package com.example.finalproject.Activities.EmployeeUI


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.Activities.MainActivity
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile_employee.*

class ProfileEmployeeActivity : AppCompatActivity() {
    private lateinit var logoutButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var projectButton: ImageButton
    private lateinit var matchButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_employee)

        logoutButton = logout_button
        homeButton = home_button
        projectButton = project_button
        matchButton = match_button
        logoutButton.setOnClickListener { v -> changeActivity(v, MainActivity::class.java, true) }
        homeButton.setOnClickListener { v -> changeActivity(v, HomeEmployeeActivity::class.java, false) }
        projectButton.setOnClickListener { v -> changeActivity(v, ProjectsEmployeeActivity::class.java, false) }
        matchButton.setOnClickListener { v -> changeActivity(v, MatchesEmployeeActivity::class.java, false)}

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
