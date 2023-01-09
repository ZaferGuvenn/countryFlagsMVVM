package com.mvvm.countryflag.adapter

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.mvvm.countryflag.R
import com.mvvm.countryflag.databinding.RecyclerRowBinding
import com.mvvm.countryflag.model.Country
import com.mvvm.countryflag.view.CountriesMenuFragment
import com.mvvm.countryflag.view.CountriesMenuFragmentDirections

class CountryListAdapter(val countryList: ArrayList<Country>):RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    class CountryViewHolder(val view:RecyclerRowBinding):RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=DataBindingUtil.inflate<RecyclerRowBinding>(inflater,R.layout.recycler_row,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        /*eski yöntem
        holder.view.RowCountryNameTextView.text= countryList.get(position).name
        holder.view.RowCountryRegionTextView.text=countryList.get(position).region

        holder.view.RowImageView.downloadWithGlide(
            countryList.get(position).flag, circularProgressDrawableMaker(holder.view.context))
         */

        //databinding ile

        holder.view.country=countryList.get(position)




       holder.view.RowLinearLayout.setOnClickListener {

            val uuid=countryList.get(position).uuid.toLong()

            val action= CountriesMenuFragmentDirections
                .actionCountriesMenuFragmentToShowSelectedCountryFragment(uuid)

            Navigation.findNavController(it).navigate(action)
        }


    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountries(list: List<Country>){
        countryList.clear()
        countryList.addAll(list)
        notifyDataSetChanged()
    }



}