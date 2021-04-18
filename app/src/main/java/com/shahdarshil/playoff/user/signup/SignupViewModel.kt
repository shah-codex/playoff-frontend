package com.shahdarshil.playoff.user.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahdarshil.playoff.createHash
import com.shahdarshil.playoff.network.PlayoffApi
import com.shahdarshil.playoff.network.datamodel.User
import kotlinx.coroutines.*
import java.net.ConnectException

/**
 * Created by shah on 16 April, 2021.
 */
class SignupViewModel : ViewModel() {
    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    private val _navigateToLogin = MutableLiveData<Boolean>()

    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    private val _generateOtpPressed = MutableLiveData<Boolean?>(null)

    val generateOtpPressed: LiveData<Boolean?>
        get() = _generateOtpPressed

    private val _signupPressed = MutableLiveData<Boolean?>(null)

    val signupPressed: LiveData<Boolean?>
        get() = _signupPressed

    fun generateOtp() {
        _generateOtpPressed.value = true
    }

    fun onOtpGenerated() {
        _generateOtpPressed.value = null
    }

    fun signupPressed() {
        _signupPressed.value = true
    }

    fun onSignupDone() {
        _signupPressed.value = null
    }

    fun callRegisterUser(username: String, password: String, email: String, otp: String) {
        registerUser(username, password, email, otp)
    }

    private fun registerUser(username: String, password: String, email: String, otp: String) {
        coroutineScope.launch {
            val hashedPassword = password.createHash()

            val user = User(username, hashedPassword, email, otp)

            val response = try {
                println(user.toString())
                PlayoffApi.retrofitService.registerUser(user)
            } catch(e: ConnectException) {
                null
            }

            withContext(Dispatchers.Default) {
                response?.let {
                    viewModelScope.launch {
                        if(response.isSuccessful) {
                            _signupPressed.value = false
                        }
                    }
                }
            }
        }
    }

    fun callOtpGeneration(email: String) {
        requestOtpGeneration(email)
    }

    private fun requestOtpGeneration(email: String) {

        coroutineScope.launch {
            val user = User(null, "", email, null)

            try {
                PlayoffApi.retrofitService.requestOtp(user)
            } catch(e: ConnectException) {

            }
        }
    }

    fun navigateToLoginFragment() {
        _navigateToLogin.value = true
    }

    fun onDoneNavigating() {
        _navigateToLogin.value = false
    }
}