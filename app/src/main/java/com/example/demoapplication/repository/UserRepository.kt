package com.example.demoapplication.repository

import com.event.eventmanagement.apis.Result
import com.event.eventmanagement.apis.RetrofitClient
import com.example.demoapplication.model.LoginUserBody
import com.example.demoapplication.model.RegisterUserResponse
import com.example.demoapplication.model.TweetBody
import com.example.demoapplication.model.TweetResponse
import com.example.demoapplication.model.UserRegisterBody

class UserRepository() {

    private val apiInterface = RetrofitClient.network

    suspend fun registerUser(userRegisterBody: UserRegisterBody): Result<RegisterUserResponse> {
        return try {
            val response = apiInterface.registerUser(userRegisterBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun login(loginUserBody: LoginUserBody): Result<RegisterUserResponse> {
        return try {
            val response = apiInterface.login(loginUserBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun postTweet(tweetBody: TweetBody, token: String): Result<TweetResponse> {
        return try {
            val response = apiInterface.postTweet(token, tweetBody)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }

    suspend fun getTweets(token: String): Result<List<TweetResponse>> {
        return try {
            val response = apiInterface.getTweets(token)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to get services: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception occurred: ${e.message}")
        }
    }
}


