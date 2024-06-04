package com.example.demoapplication.model

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(
    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("_id") var id: String? = null,
    @SerializedName("token") var token: String? = null,
)
