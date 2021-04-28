package com.shahdarshil.playoff.player.tournament

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shahdarshil.playoff.R
import com.shahdarshil.playoff.databinding.DialogCreateTournamentBinding
import java.lang.IllegalStateException

/**
 * Created by shah on 25 April, 2021.
 */
class CreateTournamentDialog : DialogFragment() {
    private lateinit var viewModel: CreateTournamentViewModel
    private lateinit var binding: DialogCreateTournamentBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater

            binding = DialogCreateTournamentBinding.inflate(inflater)

            viewModel = ViewModelProvider(this).get(CreateTournamentViewModel::class.java)

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding.viewModel = viewModel

        viewModel.cancelPressed.observe(viewLifecycleOwner, Observer {
            it?.let {
                dismiss()
            }
        })

        viewModel.tournamentCreated.observe(viewLifecycleOwner, Observer {
            it?.let {
                val message = if(it) {
                    "Successfully created the tournament"
                } else {
                    "failed to create the tournament"
                }

                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                viewModel.onTournamentCreated()
                dismiss()
            }
        })

        viewModel.createPressed.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.createTournament(
                    binding.titleEditTextCreateTournament.text.toString(),
                    binding.gameNameEditTextCreateTournament.text.toString(),
                    binding.minimumPlayersEditTextCreateTournament.text.toString().toInt(),
                    binding.maximumPlayersEditTextCreateTournament.text.toString().toInt(),
                    binding.minimumTeamsEditTextCreateTournament.text.toString().toInt(),
                    binding.maximumTeamEditTextCreateTournament.text.toString().toInt(),
                    "${binding.startDateEditTextCreateTournament.text} ${binding.startTimeEditTextCreateTournament.text}",
                    "${binding.endDateEditTextCreateTournament.text} ${binding.endTimeEditTextCreateTournament.text}",
                    binding.locationSpinnerCreateTournament.selectedItem.toString(),
                    binding.descriptionEditTextCreateTournament.text.toString()
                )

                viewModel.onDoneCreating()
            }
        })

        return binding.root
    }
}