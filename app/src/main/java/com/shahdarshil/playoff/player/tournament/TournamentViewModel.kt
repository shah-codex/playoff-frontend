package com.shahdarshil.playoff.player.tournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahdarshil.playoff.network.PlayoffApi
import com.shahdarshil.playoff.network.datamodel.Tournament
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * Created by shah on 20 April, 2021.
 */
class TournamentViewModel : ViewModel() {
    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    private val _chipClicked = MutableLiveData<Boolean?>(null)

    val chipClicked: LiveData<Boolean?>
        get() = _chipClicked

    private val _tournamentList = MutableLiveData<List<Tournament>?>()

    val tournamentList: LiveData<List<Tournament>?>
        get() = _tournamentList

    fun onChipClicked() {
        _chipClicked.value = true
    }

    fun revokeChipClick() {
        _chipClicked.value = null
    }

    fun getTournaments(location: String = "Any") {
        coroutineScope.launch {
            val response = try {
                PlayoffApi.retrofitService.getTournaments(location)
            } catch(e: ConnectException) {
                null
            }

            if(response != null && response.isSuccessful) {
                viewModelScope.launch {
                    _tournamentList.value = response.body()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
    }
}