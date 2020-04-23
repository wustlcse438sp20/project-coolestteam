package com.example.finalproject.Data

import java.io.Serializable


data class GeneralEmployee(
        var name: String? = "",
        var age: Int? = null,
        var bio: String? = "",
        var github: String? = "",
        var pic: String? = "",
        var repos: String? = ""
) : Serializable