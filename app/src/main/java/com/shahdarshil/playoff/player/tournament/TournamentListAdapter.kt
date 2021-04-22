package com.shahdarshil.playoff.player.tournament

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shahdarshil.playoff.databinding.ListItemTournamentBinding
import com.shahdarshil.playoff.network.datamodel.Tournament

/**
 * Created by shah on 20 April, 2021.
 */
class TournamentListAdapter(private val tournamentClickListener: TournamentClickListener) : ListAdapter<Tournament, TournamentListAdapter.TournamentViewHolder>(TournamentDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        return TournamentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        val tournament = getItem(position)
        holder.bind(tournament, tournamentClickListener)
    }

    class TournamentViewHolder private constructor(private val binding: ListItemTournamentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tournament: Tournament, tournamentClickListener: TournamentClickListener) {
            binding.tournament = tournament
            binding.tournamentClickListener = tournamentClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TournamentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTournamentBinding.inflate(layoutInflater, parent, false)

                return TournamentViewHolder(binding)
            }
        }
    }
}

class TournamentDiffCallback : DiffUtil.ItemCallback<Tournament>() {
    override fun areItemsTheSame(oldItem: Tournament, newItem: Tournament): Boolean =
        oldItem._id == newItem._id


    override fun areContentsTheSame(oldItem: Tournament, newItem: Tournament): Boolean =
        oldItem == newItem
}

class TournamentClickListener(val tournamentItemClick: (tournamentId: String) -> Unit) {
    fun onClick(tournamentId: String) = tournamentItemClick(tournamentId)
}