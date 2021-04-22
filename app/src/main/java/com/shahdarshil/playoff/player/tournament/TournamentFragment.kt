package com.shahdarshil.playoff.player.tournament

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.shahdarshil.playoff.databinding.FragmentTournamentBinding

class TournamentFragment : Fragment() {

    private lateinit var viewModel: TournamentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentTournamentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(TournamentViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.getTournaments()

        val adapter = TournamentListAdapter(TournamentClickListener {
            // TODO change this to redirect user to next page using tournament _id.
            Toast.makeText(context, "pressed $it", Toast.LENGTH_SHORT).show()
        })

        binding.tournamentList.adapter = adapter

        viewModel.chipClicked.observe(viewLifecycleOwner, Observer {
            it?.let {
                val chip: Chip = binding.chipGroup.findViewById(binding.chipGroup.checkedChipId) as Chip

                val location = chip.text.toString()

                viewModel.getTournaments(location)
                viewModel.revokeChipClick()
            }
        })

        viewModel.tournamentList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }
}