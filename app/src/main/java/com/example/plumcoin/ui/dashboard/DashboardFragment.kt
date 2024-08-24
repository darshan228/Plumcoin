package com.example.plumcoin.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.plumcoin.R
import com.example.plumcoin.databinding.FragmentDashboardBinding
import com.example.plumcoin.utils.Status
import com.example.plumcoin.utils.shortToast
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : Fragment(), DashboardNavigator {
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        initCompat()
        setUpObservers()
        return binding.root
    }

    /**
     * Created by Darshan on 9/21/2022.
     * Desc: Initialize components
     */
    private fun initCompat() {
        viewModel.navigator = this
    }

    /**
     * Created by Darshan on 9/21/2022.
     * Desc: Setup viewmodel observers to listen data
     */
    private fun setUpObservers() {
        viewModel.getUsers().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.screenLayout.visibility = View.VISIBLE
                    showDummyGraphData()
                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.screenLayout.visibility = View.GONE
                }

                Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    binding.screenLayout.visibility = View.VISIBLE
                    showToastMessage(it.message.toString())
                }
            }
        }
    }

    /**
     * Created by Darshan on 9/21/2022.
     * Desc: Display data  in graph
     */
    private fun showDummyGraphData() {
        // variable for our bar data set.
        var barDataSet = BarDataSet(getBarEntries(), "").apply {
            setDrawValues(false)
        }
        // variable for our bar data.
        val barData = BarData(barDataSet).apply {
            barWidth = 0.2f
        }
        binding.barChart.xAxis.apply {
            textColor = ContextCompat.getColor(requireContext(), R.color.white)
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            valueFormatter =
                IndexAxisValueFormatter(listOf("", "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"))
        }
        binding.barChart.axisLeft.apply {
            textColor = ContextCompat.getColor(requireContext(), R.color.white)
            setDrawGridLines(false)
        }
        binding.barChart.axisRight.apply {
            isEnabled = false
            setDrawGridLines(false)
        }
        binding.barChart.apply {
            description.isEnabled = false
            setTouchEnabled(false)
            isDragEnabled = false
        }.also { it.data = barData }
    }

    private fun getBarEntries(): ArrayList<BarEntry> {
        // creating a new array list
        val barEntriesArrayList = arrayListOf<BarEntry>()

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(BarEntry(1f, 8000f))
        barEntriesArrayList.add(BarEntry(2f, 9000f))
        barEntriesArrayList.add(BarEntry(3f, 6500f))
        barEntriesArrayList.add(BarEntry(4f, 8000f))
        barEntriesArrayList.add(BarEntry(5f, 9000f))
        barEntriesArrayList.add(BarEntry(6f, 6500f))
        barEntriesArrayList.add(BarEntry(7f, 7000f))
        return barEntriesArrayList
    }

    /**
     * Created by Darshan on 9/21/2022.
     * Desc: Show toast message to user
     */
    override fun showToastMessage(message: String) {
        shortToast(message)
    }
}