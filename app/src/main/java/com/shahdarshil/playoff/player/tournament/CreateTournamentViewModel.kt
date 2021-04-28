package com.shahdarshil.playoff.player.tournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahdarshil.playoff.DetailActivity
import com.shahdarshil.playoff.network.PlayoffApi
import com.shahdarshil.playoff.network.datamodel.Tournament
import com.shahdarshil.playoff.toUnixTimestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * Created by shah on 25 April, 2021.
 */
class CreateTournamentViewModel : ViewModel() {
    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    private val _tournamentCreated = MutableLiveData<Boolean?>(null)

    val tournamentCreated: LiveData<Boolean?>
        get() = _tournamentCreated

    private val _cancelPressed = MutableLiveData<Boolean?>(null)

    val cancelPressed: LiveData<Boolean?>
        get() = _cancelPressed

    private val _createPressed = MutableLiveData<Boolean?>(null)

    val createPressed: LiveData<Boolean?>
        get() = _createPressed

    fun onCancelPressed() {
        _cancelPressed.value = true
    }

    fun onCreatePressed() {
        _createPressed.value = true
    }

    fun onDoneCreating() {
        _createPressed.value = null
    }

    fun onTournamentCreated() {
        _tournamentCreated.value = null
    }

    fun createTournament(
        title: String, game: String,
        minimumPlayers: Int, maximumPlayers: Int,
        minimumTeams: Int, maximumTeams: Int,
        startDate: String, endDate: String,
        location: String, description: String
    ) {
        val tournament = Tournament(
            title = title, game = game,
            minimumPlayers = minimumPlayers,
            maximumPlayers = maximumPlayers,
            minimumTeams = minimumTeams,
            maximumTeams = maximumTeams,
            startDate = startDate.toUnixTimestamp(),
            endDate = endDate.toUnixTimestamp(),
            location = location,
            description = description,
            creator = DetailActivity.EMAIL
        )

        coroutineScope.launch {
            val response = try {
                PlayoffApi.retrofitService.createTournament(tournament)
            } catch(e: ConnectException) {
                null
            }

            viewModelScope.launch {
                _tournamentCreated.value = response != null && response.isSuccessful
            }
        }
    }
}