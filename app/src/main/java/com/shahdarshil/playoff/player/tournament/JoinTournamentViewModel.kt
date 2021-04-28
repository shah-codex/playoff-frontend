package com.shahdarshil.playoff.player.tournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahdarshil.playoff.DetailActivity
import com.shahdarshil.playoff.network.PlayoffApi
import com.shahdarshil.playoff.network.datamodel.Team
import com.shahdarshil.playoff.network.datamodel.Tournament
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * Created by shah on 24 April, 2021.
 */
class JoinTournamentViewModel : ViewModel() {
    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    private val _tournamentJoined = MutableLiveData<Boolean?>(null)

    val tournamentJoined: LiveData<Boolean?>
        get() = _tournamentJoined

    private val _tournament = MutableLiveData<Tournament?>(null)

    val tournament: LiveData<Tournament?>
        get() = _tournament

    private val _cancelButtonClicked = MutableLiveData<Boolean?>(null)

    val cancelButtonClicked: LiveData<Boolean?>
        get() = _cancelButtonClicked

    fun getTournament(tournamentId: String) {
        coroutineScope.launch {
            val response = try {
                PlayoffApi.retrofitService.getTournament(tournamentId)
            } catch(e: ConnectException) {
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

    fun cancelClicked() {
        _cancelButtonClicked.value = true
    }

    fun onTournamentJoined() {
        _tournamentJoined.value = null
    }

    fun joinTournament(tournament: Tournament) {
        if(DetailActivity.TEAM == null) {
            return
        }
        coroutineScope.launch {
            val response = try {
                (if(DetailActivity.TEAM != null) DetailActivity.TEAM else "")?.let {
                    Team(
                        name = it,
                        tournamentId = tournament._id
                    )
                }?.let {
                    PlayoffApi.retrofitService.joinTournament(it)
                }
            } catch(e: ConnectException) {
                null
            }

            viewModelScope.launch {
                _tournamentJoined.value = response != null && response.isSuccessful
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
    }
}