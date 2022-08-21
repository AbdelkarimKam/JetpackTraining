package com.example.jetpacktrainning.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.jetpacktrainning.R
import com.example.jetpacktrainning.model.Country
import com.example.jetpacktrainning.tools.Status
import com.example.jetpacktrainning.tools.displayError
import com.example.jetpacktrainning.tools.displayLoading
import com.example.jetpacktrainning.ui.viewmodel.MainViewModel

class DetailFragment  : Fragment() {

    private val mainViewModel : MainViewModel by hiltNavGraphViewModels(R.id.nav_graph)

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
        return inflater.inflate(R.layout.countries_item_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.countryState.observe(viewLifecycleOwner) {

            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.run { updateViews(view,this) }
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

    private fun updateViews(view: View, country: Country) {
        view.findViewById<TextView>(R.id.country_id).text = country.countryId.toString()
        view.findViewById<TextView>(R.id.country_code).text = country.countryCode
        view.findViewById<TextView>(R.id.continent).text = country.continent
        view.findViewById<TextView>(R.id.name).text = country.name
    }
}