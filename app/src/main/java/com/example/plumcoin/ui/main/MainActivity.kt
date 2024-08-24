package com.example.plumcoin.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.plumcoin.R
import com.example.plumcoin.databinding.ActivityMainBinding
import com.example.plumcoin.ui.dashboard.DashboardFragment
import com.example.plumcoin.utils.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainNavigator {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val dashboardFragment: DashboardFragment by lazy { DashboardFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initCompat()
    }

    /**
     * Created by Darshan on 9/20/2022.
     * Desc: Initialize components
     */
    private fun initCompat() {
        viewModel.navigator = this
        binding.fab.setOnClickListener {
            loadFragment(dashboardFragment)
        }

        loadFragment(dashboardFragment)
    }

    /**
     * Created by Darshan on 9/20/2022.
     * Desc: Load fragment in container
     */
    private fun loadFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }

    override fun showToastMessage(message: String) {
        shortToast(message)
    }
}