package com.shahdarshil.playoff.player.team

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shahdarshil.playoff.databinding.FragmentTeamBinding

class TeamFragment : Fragment() {

    private lateinit var viewModel: TeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentTeamBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(TeamViewModel::class.java)

        val adapter = TeamListAdapter(TeamItemClickListener { name ->
            Toast.makeText(context, "clicked $name", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, JoinTeamActivity::class.java)
            intent.putExtra("teamName", name)
            startActivity(intent)
        })

        binding.teamsRecyclerView.adapter = adapter

        viewModel.getTeams()

        viewModel.teams.observe(viewLifecycleOwner, Observer {
            it?.let { teams ->
                adapter.submitList(teams)
            }
        })

        return binding.root
    }
}