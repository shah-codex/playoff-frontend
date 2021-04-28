package com.shahdarshil.playoff.player.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahdarshil.playoff.DetailActivity
import com.shahdarshil.playoff.network.PlayoffApi
import com.shahdarshil.playoff.network.datamodel.Player
import com.shahdarshil.playoff.network.datamodel.Team
import com.shahdarshil.playoff.network.datamodel.Tournament
import com.shahdarshil.playoff.network.datamodel.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * Created by shah on 26 April, 2021.
 */
class JoinTeamViewModel : ViewModel() {
    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    private val _team = MutableLiveData<Team>()

    val team: LiveData<Team>
        get() = _team

    private val _players = MutableLiveData<List<User>?>()

    val players: LiveData<List<User>?>
        get() = _players

    private val _tournament = MutableLiveData<Tournament?>()

    val tournament: LiveData<Tournament?>
        get() = _tournament

    private val _cancelPressed = MutableLiveData<Boolean?>()

    val cancelPressed: LiveData<Boolean?>
        get() = _cancelPressed

    private val _teamJoined = MutableLiveData<Boolean?>()

    val teamJoined: LiveData<Boolean?>
        get() = _teamJoined

    fun getTeamDetails(teamName: String) {
        coroutineScope.launch {
            val response = try {
                PlayoffApi.retrofitService.getTeam(teamName)
            } catch(e: ConnectException) {
                null
            }

            viewModelScope.launch {
                if(response != null && response.isSuccessful) {
                    _team.value = response.body()
                    getTournament(response.body()?.tournamentId)
                }
            }
        }
    }

    private suspend fun getTournament(tournamentId: String?) {
        tournamentId?.let {
            coroutineScope.launch {
                val response = try {
                    PlayoffApi.retrofitService.getTournament(tournamentId)
                } catch (e: ConnectException) {
                    null
                }

                viewModelScope.launch {
                    if (response != null && response.isSuccessful) {
                        _tournament.value = response.body()
                    } else {
                        _tournament.value = null
                    }
                }
            }
        }
    }

    fun getPlayers(teamName: String) {
        coroutineScope.launch {
            val response = try {
                PlayoffApi.retrofitService.getTeamPlayers(teamName)
            } catch(e: ConnectException) {
                null
            }

            viewModelScope.launch {
                if (response != null && response.isSuccessful) {
                    _players.value = response.body()
                } else {
                    _players.value = null
                }
            }
        }
    }

    fun onCancelPressed() {
        _cancelPressed.value = true
    }

    fun onCancelled() {
        _cancelPressed.value = null
    }

    fun joinTeam() {
        coroutineScope.launch {
            val player = Player(DetailActivity.EMAIL, _team.value?.name)
            val response = try {
                PlayoffApi.retrofitService.joinTeam(player)
            } catch(e: ConnectException) {
                null
            }

            viewModelScope.launch {
                if(response != null && response.isSuccessful) {
                    _teamJoined.value = true
                    DetailActivity.TEAM = team.value?.name
                } else {
                    _teamJoined.value = false
                }
            }
        }
    }

    fun onJoinedTeam() {
        _teamJoined.value = null
    }

    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
    }
}