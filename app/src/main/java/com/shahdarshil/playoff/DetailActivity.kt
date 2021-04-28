package com.shahdarshil.playoff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.shahdarshil.playoff.databinding.ActivityDetailBinding
import com.shahdarshil.playoff.player.team.CreateTeamDialog
import com.shahdarshil.playoff.player.team.TeamFragment
import com.shahdarshil.playoff.player.tournament.CreateTournamentDialog
import com.shahdarshil.playoff.player.tournament.TournamentFragment
import com.shahdarshil.playoff.player.update.RecentFragment

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailViewModel
    private val args: DetailActivityArgs by navArgs()

    companion object {
        lateinit var EMAIL: String
        var TEAM: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        EMAIL = args.email

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewModel.getPlayerTeam()

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        val fragmentList = arrayListOf<Fragment>(
            RecentFragment(),
            TournamentFragment(),
            TeamFragment()
        )

        supportActionBar?.also {
            it.elevation = 0.0f
        }

        viewModel.teamName.observe(this, Observer {
            it?.let {
                TEAM = it
            }
        })

        viewModel.navigateCreateTeam.observe(this, Observer {
            it?.let {
                val createTeamDialog = CreateTeamDialog()
                createTeamDialog.show(supportFragmentManager, "CreateTeamDialog")
                viewModel.onNavigateComplete()
            }
        })

        viewModel.navigateCreateTournament.observe(this, Observer {
            it?.let {
                val createTournamentDialog = CreateTournamentDialog()
                createTournamentDialog.show(supportFragmentManager, "CreateTournamentDialog")
                viewModel.onNavigateComplete()
            }
        })

        binding.viewPager.adapter = ViewPagerAdapter(fragmentList, supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = getString(R.string.recent_text)
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.baseline_history_24)
                }
                1 -> {
                    tab.text = getString(R.string.tournament_text)
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.baseline_emoji_events_24)
                }
                2 -> {
                    tab.text = getString(R.string.team_text)
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.baseline_groups_24)
                }
            }
        }.attach()
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}

class ViewPagerAdapter(
    list: ArrayList<Fragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList = list

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}