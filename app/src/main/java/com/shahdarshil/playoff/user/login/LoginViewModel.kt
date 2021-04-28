package com.shahdarshil.playoff.user.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahdarshil.playoff.createHash
import com.shahdarshil.playoff.network.PlayoffApi
import com.shahdarshil.playoff.network.datamodel.User
import com.shahdarshil.playoff.user.isEmail
import kotlinx.coroutines.*
import retrofit2.Response
import java.net.ConnectException

/**
 * Created by shah on 14 April, 2021.
 */
class LoginViewModel : ViewModel() {
    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    private val _emailIsValid = MutableLiveData<Boolean>(false)

    val emailIsValid: LiveData<Boolean>
        get() = _emailIsValid

    private val _login = MutableLiveData<Boolean>()

    val login: LiveData<Boolean>
        get() = _login

    private val _loginSuccessful = MutableLiveData<Boolean?>(null)

    val loginSuccessful: LiveData<Boolean?>
        get() = _loginSuccessful

    private val _navigateToSignup = MutableLiveData<Boolean>()

    val navigateToSignup: LiveData<Boolean>
        get() = _navigateToSignup

    private val _navigateToForgotPassword = MutableLiveData<Boolean?>()

    val navigateToForgotPassword: LiveData<Boolean?>
        get() = _navigateToForgotPassword

    fun checkEmail(email: String) {
        _emailIsValid.value = email.isEmail()
    }

    fun loginButtonPressed() {
        _login.value = true
    }

    fun revokeLoginButton() {
        _login.value = false
    }

    fun validateUserDetails(email: String, password: String) {

         coroutineScope.launch {
            val hashedPassword = password.createHash()

            val user = User(null, hashedPassword, email, null)

            val response: Response<User>? = try {
                PlayoffApi.retrofitService.validateUser(user)
            } catch(e: ConnectException) {
                null
            }

            withContext(Dispatchers.Default) {
                viewModelScope.launch {
                    if (response != null && response.isSuccessful) {
                        _loginSuccessful.value = true

                        println(response.body()?.toString())
                    } else {
                        _loginSuccessful.value = false
                    }
                    _loginSuccessful.value = null
                }
            }
        }
    }

    fun navigateToSignupFragment() {
        _navigateToSignup.value = true
    }

    fun navigateToForgotPassword() {
        _navigateToForgotPassword.value = true
    }

    fun onDoneNavigating() {
        _navigateToSignup.value = false
        _navigateToForgotPassword.value = null
    }

    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
    }
}