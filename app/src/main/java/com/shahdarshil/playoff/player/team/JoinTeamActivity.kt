package com.shahdarshil.playoff.player.team

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shahdarshil.playoff.DetailActivity
import com.shahdarshil.playoff.R
import com.shahdarshil.playoff.databinding.ActivityJoinTeamBinding

class JoinTeamActivity : AppCompatActivity() {
    private lateinit var viewModel: JoinTeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityJoinTeamBinding = DataBindingUtil.setContentView(this, R.layout.activity_join_team)

        binding.lifecycleOwner = this
//
//        if(DetailActivity.TEAM != null) {
//            binding.joinButtonJoinTeam.isEnabled = false
//        }

        val teamName = intent.getStringExtra("teamName")!!

        viewModel = ViewModelProvider(this).get(JoinTeamViewModel::class.java)

        viewModel.getTeamDetails(teamName)
        viewModel.getPlayers(teamName)

        binding.viewModel = viewModel

        val adapter = PlayerListAdapter()

        binding.playerListRecyclerViewJoinTeam.adapter = adapter

        viewModel.tournament.observe(this, Observer {
            it?.let {
                binding.tournament = it
            }
        })

        viewModel.team.observe(this, Observer {
            binding.team = it
        })

        viewModel.players.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.cancelPressed.observe(this, Observer {
            it?.let {
                finish()
                viewModel.onCancelled()
            }
        })

        viewModel.teamJoined.observe(this, Observer {
            it?.let {
                val message = if(it) {
                    "Successfully joined the team"
                } else {
                    "Failed to join the team"
                }
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                viewModel.onJoinedTeam()
            }
        })
    }
}