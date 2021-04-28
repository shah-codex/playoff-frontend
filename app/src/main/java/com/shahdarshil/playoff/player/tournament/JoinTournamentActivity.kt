package com.shahdarshil.playoff.player.tournament

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shahdarshil.playoff.R
import com.shahdarshil.playoff.databinding.ActivityJoinTournamentBinding

class JoinTournamentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout for this fragment
        val binding: ActivityJoinTournamentBinding = DataBindingUtil.setContentView(this, R.layout.activity_join_tournament)

        binding.lifecycleOwner = this

        val viewModel = ViewModelProvider(this).get(JoinTournamentViewModel::class.java)

        binding.viewModel = viewModel

        val tournamentId = intent.getStringExtra("tournamentId")!!

        viewModel.getTournament(tournamentId)

        viewModel.cancelButtonClicked.observe(this, Observer {
            it?.let {
                finish()
            }
        })

        viewModel.tournamentJoined.observe(this, Observer {
            it?.let {
                val message = if(it) {
                    "Tournament joined successfully"
                } else {
                    "Failed to join tournament"
                }
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                viewModel.onTournamentJoined()
            }
        })

        viewModel.tournament.observe(this, Observer {
            it?.let {
                binding.tournament = it
            }
        })
    }
}