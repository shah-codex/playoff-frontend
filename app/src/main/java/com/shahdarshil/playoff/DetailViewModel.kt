package com.shahdarshil.playoff

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahdarshil.playoff.network.PlayoffApi
import com.shahdarshil.playoff.network.datamodel.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * Created by shah on 18 April, 2021.
 */
class DetailViewModel() : ViewModel() {
    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    private val _teamName = MutableLiveData<String?>()

    val teamName: LiveData<String?>
        get() = _teamName

    private val _navigateCreateTeam = MutableLiveData<Boolean?>(null)

    val navigateCreateTeam: LiveData<Boolean?>
        get() = _navigateCreateTeam

    private val _navigateCreateTournament = MutableLiveData<Boolean?>(null)

    val navigateCreateTournament: LiveData<Boolean?>
        get() = _navigateCreateTournament

    private val _createFloatingActionButtonText = MutableLiveData<String>("Create")

    val createFloatingActionButtonText: LiveData<String>
        get() = _createFloatingActionButtonText

    private val _createFloatingActionButtonState = MutableLiveData<Boolean>(true)

    val createFloatingActionButtonState: LiveData<Boolean>
        get() = _createFloatingActionButtonState

    fun navigateToCreateTournament() {
        _navigateCreateTournament.value = true
    }

    fun navigateToCreateTeam() {
        _navigateCreateTeam.value = true
    }

    fun onNavigateComplete() {
        _navigateCreateTournament.value = null
        _navigateCreateTeam.value = null
    }

    fun changeCreateFloatingActionButtonState() {
        _createFloatingActionButtonState.value = !(_createFloatingActionButtonState.value!!)
        _createFloatingActionButtonText.value = when(_createFloatingActionButtonState.value) {
            true -> "Create"
            else -> "Cancel"
        }
    }

    fun getPlayerTeam() {
        coroutineScope.launch {
            val response = try {
                PlayoffApi.retrofitService.getPlayerTeam(DetailActivity.EMAIL)
            } catch(e: ConnectException) {
                null
            }

            viewModelScope.launch {
                if (response != null && response.isSuccessful) {
                    _teamName.value = response.body()?.teamName
                } else {
                    _teamName.value = null
                }
            }
        }
    }
}