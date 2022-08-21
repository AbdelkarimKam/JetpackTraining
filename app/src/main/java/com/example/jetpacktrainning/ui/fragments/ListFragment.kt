package com.example.jetpacktrainning.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
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
import com.example.jetpacktrainning.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeApi
@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _myFragmentBinding: CountriesListLayoutBinding? = null
    private val binding
        get() = _myFragmentBinding!!

    private val mainViewModel: MainViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    lateinit var mRecyclerAdapter: CountrriesAdapter
    private lateinit var _context: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _myFragmentBinding = CountriesListLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

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
        mainViewModel.countriesState.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    displayCountries(it.data!!)
                }
                Status.FAILURE -> {
                    displayError(_context,it.message)
                }
                Status.LOADING -> {
                    displayLoading(_context )
                }
            }
        }
    }

    private fun displayCountries(countries: List<Country>) = mRecyclerAdapter.updateRecyclerData(countries)

    private fun displayFragmentCountry() = findNavController().navigate(R.id.action_listFragment_to_detailFragment)

    override fun onDestroy() {
        super.onDestroy()
        //Fragments outlive their views.
        // Make sure you clean up any references to the binding class instance
        // in the fragment's onDestroyView() method.
        _myFragmentBinding = null
    }

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