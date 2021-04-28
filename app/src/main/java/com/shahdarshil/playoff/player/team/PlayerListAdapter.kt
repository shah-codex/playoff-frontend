package com.shahdarshil.playoff.player.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shahdarshil.playoff.databinding.ListItemPlayerBinding
import com.shahdarshil.playoff.network.datamodel.User

/**
 * Created by shah on 26 April, 2021.
 */
class PlayerListAdapter : ListAdapter<User, PlayerListAdapter.PlayerViewHolder>(PlayerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class PlayerViewHolder private constructor(private val binding: ListItemPlayerBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.userNameText.text = user.username
        }

        companion object {
            fun from(parent: ViewGroup): PlayerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPlayerBinding.inflate(layoutInflater, parent, false)

                return PlayerViewHolder(binding)
            }
        }
    }
}

class PlayerDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.email == newItem.email

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
}