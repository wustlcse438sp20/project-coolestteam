package com.example.finalproject.Database

import com.example.finalproject.Data.*

interface EmployeeDAO{

    fun getUserGeneral(uid:String): GeneralEmployee

    fun getUserEducations(uid: String): MutableList<Education>

    fun getUserHobbies(uid: String): MutableList<Hobby>

    fun getUserProjects(uid: String): MutableList<Project>

    fun getUserTechnicalSkills(uid: String): MutableList<TechnicalSkill>

    fun getUserWorkExperiences(uid: String): MutableList<WorkExperience>

}

