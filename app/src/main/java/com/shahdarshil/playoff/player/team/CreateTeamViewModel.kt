package com.shahdarshil.playoff.player.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahdarshil.playoff.DetailActivity
import com.shahdarshil.playoff.network.PlayoffApi
import com.shahdarshil.playoff.network.datamodel.Team
import kotlinx.coroutines.*
import java.net.ConnectException

/**
 * Created by shah on 23 April, 2021.
 */
class CreateTeamViewModel : ViewModel() {
    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    private val _teamCreated = MutableLiveData<Boolean?>(null)

    val teamCreated: LiveData<Boolean?>
        get() = _teamCreated

    private val _createTeamPressed = MutableLiveData<Boolean?>(null)

    val createTeamPressed: LiveData<Boolean?>
        get() = _createTeamPressed

    private val _cancelClicked = MutableLiveData<Boolean?>(null)

    val cancelClicked: LiveData<Boolean?>
        get() = _cancelClicked

    fun createTeam(name: String, tournamentId: String? = null) {
        coroutineScope.launch {
            val team = Team(name, DetailActivity.EMAIL, tournamentId)

            val response = try {
                PlayoffApi.retrofitService.createTeam(team)
            } catch(e: ConnectException) {
                null
            }

            withContext(Dispatchers.Main) {
                viewModelScope.launch {
                    if(response != null && response.isSuccessful) {
                        _teamCreated.value = true
                        DetailActivity.TEAM = name
                    } else {
                        _teamCreated.value = false
                    }
                }
            }
        }
    }

    fun onCancelClicked() {
        _cancelClicked.value = true
    }

    fun revokeCancelButton() {
        _cancelClicked.value = null
    }

    fun onCreateTeamPressed() {
        _createTeamPressed.value = true
    }

    fun revokeCreateTeam() {
        _createTeamPressed.value = null
    }

    fun teamCreationDone() {
        _teamCreated.value = null
    }

    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
    }
}