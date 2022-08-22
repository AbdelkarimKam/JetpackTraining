package com.example.jetpacktrainning.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.jetpacktrainning.R
import com.example.jetpacktrainning.databinding.CountryDetailLayoutBinding
import com.example.jetpacktrainning.tools.Status
import com.example.jetpacktrainning.tools.displayError
import com.example.jetpacktrainning.tools.displayLoading
import com.example.jetpacktrainning.ui.viewmodel.MainViewModel

class DetailFragment : Fragment() {

    private var _myFragmentBinding: CountryDetailLayoutBinding? = null
    private val binding
        get() = _myFragmentBinding!!

    private val mainViewModel: MainViewModel by hiltNavGraphViewModels(R.id.nav_graph)

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
        _myFragmentBinding = CountryDetailLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.countryState.observe(viewLifecycleOwner) {

            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { country -> binding.country = country }
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

    override fun onDestroy() {
        super.onDestroy()
        //Fragments outlive their views.
        // Make sure you clean up any references to the binding class instance
        // in the fragment's onDestroyView() method.
        _myFragmentBinding = null
    }
}