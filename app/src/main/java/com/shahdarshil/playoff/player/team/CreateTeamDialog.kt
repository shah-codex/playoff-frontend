package com.shahdarshil.playoff.player.team

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shahdarshil.playoff.databinding.DialogCreateTeamBinding

class CreateTeamDialog : DialogFragment() {
    private lateinit var viewModel: CreateTeamViewModel
    private lateinit var binding: DialogCreateTeamBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater

            binding = DialogCreateTeamBinding.inflate(inflater)

            viewModel = ViewModelProvider(this).get(CreateTeamViewModel::class.java)

            binding.viewModel = viewModel

            viewModel.cancelClicked.observe(this, Observer { cancelClicked ->
                cancelClicked?.let {
                    viewModel.revokeCancelButton()
                    dismiss()
                }
            })

            viewModel.createTeamPressed.observe(this, Observer { createTeamPressed ->
                createTeamPressed?.let {
                    viewModel.createTeam(binding.createTeamEditText.text.toString().trim())
                    viewModel.revokeCreateTeam()
                }
            })

            viewModel.teamCreated.observe(this, Observer { teamCreated ->
                teamCreated?.let { teamCreationSuccessful ->
                    if(teamCreationSuccessful) {
                        Toast.makeText(activity, "Team created successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "Team name already exist\nOR\nAlready in a team", Toast.LENGTH_SHORT).show()
                    }
                    viewModel.teamCreationDone()
                    dismiss()
                }
            })

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}