package com.shahdarshil.playoff.player.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shahdarshil.playoff.databinding.FragmentRecentBinding

// TODO add the code to display all the joined and created tournaments and teams
class RecentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentRecentBinding.inflate(inflater)

        return binding.root
    }
}