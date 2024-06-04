package com.example.demoapplication.model

import com.google.gson.annotations.SerializedName

data class LoginUserBody(

    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String

)
