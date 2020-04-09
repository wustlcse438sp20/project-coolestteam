package com.example.finalproject.Activities.EmployeeUI


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import kotlinx.android.synthetic.main.activity_profile_employee.*

class ProfileEmployeeActivity : AppCompatActivity() {

    private lateinit var homeButton: ImageButton
    private lateinit var projectButton: ImageButton
    private lateinit var matchButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_employee)


        homeButton = home_button
        projectButton = project_button
        matchButton = match_button

        homeButton.setOnClickListener { v -> employeeHomeClicked(v) }
        projectButton.setOnClickListener { v -> employeeProjectClicked(v) }
        matchButton.setOnClickListener { v -> employeeMatchesClicked(v) }

    }

    fun employeeHomeClicked(view: View){
        val intent = Intent(this, HomeEmployeeActivity::class.java)
        startActivity(intent)
    }
    fun employeeProjectClicked(view: View){
        val intent = Intent(this, ProjectsEmployeeActivity::class.java)
        startActivity(intent)
    }
    fun employeeMatchesClicked(view: View){
        val intent = Intent(this, MatchesEmployeeActivity::class.java)
        startActivity(intent)
    }
}
