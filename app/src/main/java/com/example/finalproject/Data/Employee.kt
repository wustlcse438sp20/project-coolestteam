package com.example.finalproject.Data

data class Employee(
    val name: String,
    val school: String,
    val major: String,
    val age: Int,
    var general: GeneralEmployee,
    var educations: MutableList<Education>,
    var hobbies: MutableList<Hobby>,
    var projects: MutableList<Project>,
    var technicalSkills: MutableList<TechnicalSkill>,
    var workExperiences: MutableList<WorkExperience>
)