package com.example.finalproject.Data

import java.io.Serializable

data class EmployeeMatch(
        var name: String = "",
        var school: String = "",
        var major: String = "",
        var age: Int = 0,
        var interested: Boolean = true,
        var general: GeneralEmployee = GeneralEmployee(null, null, null, null, null),
        var educations: MutableList<Education> = mutableListOf<Education>(),
        var hobbies: MutableList<Hobby> = mutableListOf<Hobby>(),
        var projects: MutableList<Project> = mutableListOf<Project>(),
        var technicalSkills: MutableList<TechnicalSkill> = mutableListOf<TechnicalSkill>(),
        var workExperiences: MutableList<WorkExperience> = mutableListOf<WorkExperience>(),
        var matchList: MutableList<PostMatch> = mutableListOf<PostMatch>(),
        var id: String = ""
) : Serializable