package com.shahdarshil.playoff.player.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shahdarshil.playoff.databinding.ListItemTeamBinding
import com.shahdarshil.playoff.network.datamodel.Team

/**
 * Created by shah on 22 April, 2021.
 */
class TeamListAdapter(private val teamItemClickListener: TeamItemClickListener) : ListAdapter<Team, TeamListAdapter.ViewHolder.TeamViewHolder>(TeamDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = getItem(position)
        holder.bind(team, teamItemClickListener)
    }

    companion object ViewHolder {
        class TeamViewHolder(val binding: ListItemTeamBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(team: Team, teamItemClickListener: TeamItemClickListener) {
                binding.team = team
                binding.teamItemClickListener = teamItemClickListener
            }

            companion object {
                fun from(parent: ViewGroup): TeamViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = ListItemTeamBinding.inflate(layoutInflater, parent, false)

                    return TeamViewHolder(binding)
                }
            }
        }
    }
}

class TeamDiffCallback() : DiffUtil.ItemCallback<Team>() {
    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean
        = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean
        = oldItem == newItem
}

class TeamItemClickListener(private val clickListener: (teamName: String) -> Unit) {
    fun onClick(teamName: String) = clickListener(teamName)
}