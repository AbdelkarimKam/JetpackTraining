package com.example.jetpacktrainning.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpacktrainning.R
import com.example.jetpacktrainning.model.Country

class CountrriesAdapter : RecyclerView.Adapter<CountrriesAdapter.CountriesViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null

    private val countries = ArrayList<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        return CountriesViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.countries_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        Log.d("Karim","${countries[position]} \n")
        holder.bind(countries[position])
    }

    override fun getItemCount() = countries.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateRecyclerData(postList:List<Country>){
        countries.clear()
        countries.addAll(postList)
        notifyDataSetChanged()
    }
    inner  class CountriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mContinent: TextView
        private val mCountryCode: TextView
        private val mCountryId: TextView
        private val mName: TextView

        init {
            mContinent = itemView.findViewById(R.id.continent)
            mCountryCode = itemView.findViewById(R.id.country_code)
            mCountryId = itemView.findViewById(R.id.country_id)
            mName = itemView.findViewById(R.id.name)
            itemView.setOnClickListener {
                onItemClick?.invoke(countries[adapterPosition].countryId)
            }
        }

        fun bind(country: Country) {
            mContinent.text = country.continent
            mCountryCode.text = country.countryCode
            mCountryId.text = country.countryId.toString()
            mName.text = country.name
        }
    }
}

