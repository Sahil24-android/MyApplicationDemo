package com.example.demoapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.event.eventmanagement.apis.Result
import com.example.demoapplication.model.LoginUserBody
import com.example.demoapplication.model.RegisterUserResponse
import com.example.demoapplication.model.TweetBody
import com.example.demoapplication.model.TweetResponse
import com.example.demoapplication.model.UserRegisterBody
import com.example.demoapplication.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private var repository: UserRepository = UserRepository()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> get() = _isLoading


    private val _registerUserResponse: MutableLiveData<RegisterUserResponse> = MutableLiveData()
    val registerUserResponse: LiveData<RegisterUserResponse> get() = _registerUserResponse

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> get() = _error

    fun registerUser(userRegisterBody: UserRegisterBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.registerUser(userRegisterBody)
            if (result is Result.Success) {
                _registerUserResponse.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

    private val _loginUserResponse: MutableLiveData<RegisterUserResponse> = MutableLiveData()
    val loginUserResponse: LiveData<RegisterUserResponse> get() = _loginUserResponse

    fun login(loginUserBody: LoginUserBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.login(loginUserBody)
            if (result is Result.Success) {
                _loginUserResponse.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

    private val _tweetResponse: MutableLiveData<TweetResponse> = MutableLiveData()
    val tweetResponse: LiveData<TweetResponse> get() = _tweetResponse

    fun postTweet(token: String, tweetBody: TweetBody) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.postTweet(tweetBody, token)
            if (result is Result.Success) {
                _tweetResponse.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

    private val _getAllTweetResponse: MutableLiveData<List<TweetResponse>> = MutableLiveData()
    val allTweetResponse: LiveData<List<TweetResponse>> get() = _getAllTweetResponse

    fun getTweets(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getTweets(token)
            if (result is Result.Success) {
                _getAllTweetResponse.value = result.value
                _isLoading.value = false
            } else if (result is Result.Error) {
                _error.value = result.message
                _isLoading.value = false
            }
            _isLoading.value = false
        }
    }

}