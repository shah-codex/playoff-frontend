package com.shahdarshil.playoff.player.tournament

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.shahdarshil.playoff.R
import com.shahdarshil.playoff.network.datamodel.Team
import com.shahdarshil.playoff.network.datamodel.Tournament
import com.shahdarshil.playoff.toDate

/**
 * Created by shah on 20 April, 2021.
 */
@BindingAdapter("tournamentRequiredPlayers")
fun TextView.formatRequiredPlayers(tournament: Tournament) {
    this.text = resources.getString(
        R.string.players_required_text_placeholder,
        tournament.minimumPlayers,
        tournament.maximumPlayers
    )
}

@BindingAdapter("startDate")
fun TextView.formatStartDate(tournament: Tournament) {
    this.text = ": ${tournament.startDate.toDate()}"
}

@BindingAdapter("minimumPlayers")
fun TextView.formatMinimumPlayers(tournament: Tournament?) {
    this.text = resources.getString(R.string.minimum_teams_text_placeholder, tournament?.minimumPlayers)
}

@BindingAdapter("maximumPlayers")
fun TextView.formatMaximumPlayers(tournament: Tournament?) {
    this.text = resources.getString(R.string.maximum_teams_text_placeholder, tournament?.maximumTeams)
}

@BindingAdapter("formatStartDate")
fun TextView.formatStartingDate(tournament: Tournament?) {
    this.text = resources.getString(R.string.start_date_text_placeholder, tournament?.startDate?.toDate() ?: 23)
}

@BindingAdapter("formatEndDate")
fun TextView.formatEndingDate(tournament: Tournament?) {
    this.text = resources.getString(R.string.end_date_text_placeholder, tournament?.endDate?.toDate() ?: 33)
}

@BindingAdapter("minimumTeams")
fun TextView.formatMinimumTeams(tournament: Tournament?) {
    this.text = "Joined Teams\n(minimum : ${tournament?.minimumTeams})"
}