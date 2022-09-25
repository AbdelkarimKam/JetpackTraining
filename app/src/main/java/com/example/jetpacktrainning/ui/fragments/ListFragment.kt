package com.example.jetpacktrainning.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpacktrainning.R
import com.example.jetpacktrainning.databinding.CountriesListLayoutBinding
import com.example.jetpacktrainning.model.Country
import com.example.jetpacktrainning.tools.Status
import com.example.jetpacktrainning.tools.displayError
import com.example.jetpacktrainning.tools.displayLoading
import com.example.jetpacktrainning.ui.adapter.CountrriesAdapter
import com.example.jetpacktrainning.ui.viewmodel.MainStateEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@ExperimentalComposeApi
@AndroidEntryPoint
class ListFragment : BaseFragment<CountriesListLayoutBinding>(CountriesListLayoutBinding::inflate) {

    lateinit var mRecyclerAdapter: CountrriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setRecycler()
        subscribeObserver()
        mRecyclerAdapter.onItemClick = { countryId ->
            mainViewModel.setStateEvent(MainStateEvent.GetCountryEvent(countryId))
            displayFragmentCountry()
        }
    }

    private fun subscribeObserver() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.countriesState.collectLatest {
                when (it.status) {
                    Status.SUCCESS -> {
                        displayCountries(it.data!!)
                    }
                    Status.FAILURE -> {
                        displayError(_context, it.message)
                    }
                    Status.LOADING -> {
                        displayLoading(_context)
                    }
                }
            }
        }
    }

    private fun displayCountries(countries: List<Country>) = mRecyclerAdapter.updateRecyclerData(countries)

    private fun displayFragmentCountry() = findNavController().navigate(R.id.action_listFragment_to_detailFragment)

    private fun setRecycler() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(false)
            adapter = mRecyclerAdapter
        }
    }

    private fun initData() {
        mRecyclerAdapter = CountrriesAdapter()
    }
}
