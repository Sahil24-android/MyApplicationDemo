package com.example.demoapplication.model

import com.google.gson.annotations.SerializedName

data class WelcomeResponse(
    @SerializedName("message") val message: String
)
