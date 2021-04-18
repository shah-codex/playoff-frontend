package com.shahdarshil.playoff

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by shah on 18 April, 2021.
 */
class DetailViewModel(private val application: Application) : ViewModel() {
    private val _createFloatingActionButtonText = MutableLiveData<String>("Create")

    val createFloatingActionButtonText: LiveData<String>
        get() = _createFloatingActionButtonText

    private val _createFloatingActionButtonState = MutableLiveData<Boolean>(true)

    val createFloatingActionButtonState: LiveData<Boolean>
        get() = _createFloatingActionButtonState

    fun changeCreateFloatingActionButtonState() {
        _createFloatingActionButtonState.value = !(_createFloatingActionButtonState.value!!)
        _createFloatingActionButtonText.value = when(_createFloatingActionButtonState.value) {
            true -> "Create"
            else -> "Cancel"
        }
    }
}