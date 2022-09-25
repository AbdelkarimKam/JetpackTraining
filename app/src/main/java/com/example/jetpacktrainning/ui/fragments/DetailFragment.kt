package com.example.jetpacktrainning.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.lifecycle.lifecycleScope
import com.example.jetpacktrainning.databinding.CountryDetailLayoutBinding
import com.example.jetpacktrainning.tools.Status
import com.example.jetpacktrainning.tools.displayError
import com.example.jetpacktrainning.tools.displayLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@ExperimentalComposeApi
@AndroidEntryPoint
class DetailFragment : BaseFragment<CountryDetailLayoutBinding>(CountryDetailLayoutBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            mainViewModel.countryState.collectLatest {
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
    }
}
