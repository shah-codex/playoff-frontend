package com.shahdarshil.playoff.player.tournament

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.shahdarshil.playoff.R
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

@BindingAdapter("setStartDate")
fun TextView.formatStartDate(tournament: Tournament) {
    this.text = tournament.startDate.toDate()
}