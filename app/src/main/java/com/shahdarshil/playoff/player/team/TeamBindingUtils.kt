package com.shahdarshil.playoff.player.team

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.shahdarshil.playoff.R
import com.shahdarshil.playoff.network.PlayoffApi
import com.shahdarshil.playoff.network.datamodel.Team
import com.shahdarshil.playoff.network.datamodel.Tournament
import kotlinx.coroutines.*
import java.net.ConnectException

/**
 * Created by shah on 22 April, 2021.
 */
@BindingAdapter("playingText")
fun TextView.playingText(team: Team) {
    this.text = resources.getString(R.string.team_playing_text_placeholder, team.playing, team.joinedPlayers)
}

@BindingAdapter("gameName")
fun TextView.gameName(team: Team) {
    if(team.tournamentId == null) {
        this.text = resources.getString(R.string.no_team_placeholder_text)
    } else {
        GlobalScope.launch {
            val response = try {
                PlayoffApi.retrofitService.getTournament(team.tournamentId)
            } catch(e: ConnectException) {
                null
            }

            withContext(Dispatchers.Main) {
                if (response != null && response.isSuccessful) {
                    this@gameName.text = response.body()?.game
                } else {
                    this@gameName.text = resources.getString(R.string.no_team_placeholder_text)
                }
            }
        }
    }
}

@BindingAdapter("totalTeams")
fun TextView.formatTotalTeams(tournament: Tournament?) {
    this.text = resources.getString(R.string.total_teams_joined_text_placeholder, tournament?.teamsParticipated, tournament?.maximumTeams)
}

@BindingAdapter("teamPlayingPlayers")
fun TextView.formatPlayingPlayers(team: Team?) {
    this.text = resources.getString(R.string.team_playing_players_text, team?.playing, team?.joinedPlayers)
}