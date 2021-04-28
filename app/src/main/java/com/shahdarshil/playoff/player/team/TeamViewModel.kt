package com.shahdarshil.playoff.player.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahdarshil.playoff.network.PlayoffApi
import com.shahdarshil.playoff.network.datamodel.Team
import kotlinx.coroutines.*
import java.net.ConnectException

/**
 * Created by shah on 22 April, 2021.
 */
class TeamViewModel : ViewModel() {
    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    private val _teams = MutableLiveData<List<Team>?>()

    val teams: LiveData<List<Team>?>
        get() = _teams

    fun getTeams() {
        coroutineScope.launch {
            val response = try {
                PlayoffApi.retrofitService.getTeams()
            } catch(e: ConnectException) {
                null
            }

            viewModelScope.launch {
                if (response != null && response.isSuccessful) {
                    _teams.value = response.body()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
    }
}