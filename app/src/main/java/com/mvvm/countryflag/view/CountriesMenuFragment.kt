package com.mvvm.countryflag.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.countryflag.R
import com.mvvm.countryflag.adapter.CountryListAdapter
import com.mvvm.countryflag.databinding.FragmentCountriesMenuBinding
import com.mvvm.countryflag.viewmodel.CountriesMenuFragmentViewModel


class CountriesMenuFragment : Fragment() {


    private lateinit var menuBinding: FragmentCountriesMenuBinding
    private lateinit var viewModel:CountriesMenuFragmentViewModel

    private val adapter=CountryListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        menuBinding=DataBindingUtil.inflate<FragmentCountriesMenuBinding>(inflater,R.layout.fragment_countries_menu,container,false)
        return menuBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProvider(this).get(CountriesMenuFragmentViewModel::class.java)
        viewModel.refreshData()



        menuBinding.FragmentCountriesMenuRecyclerView.layoutManager= GridLayoutManager(context,2)
        menuBinding.FragmentCountriesMenuRecyclerView.adapter=adapter


        //refresh menu with swipe refresh
        menuBinding.FragmentCountriesMenuSwipeRefreshLayout.setOnRefreshListener {


            viewModel.getFromInternet()
            menuBinding.FragmentCountriesMenuSwipeRefreshLayout.isRefreshing=false
        }



        observeLiveData()

    }

    private fun observeLiveData(){

        viewModel.countries.observe(viewLifecycleOwner){

            it?.let {
                menuBinding.FragmentCountriesMenuRecyclerView.visibility=View.VISIBLE
                adapter.updateCountries(it)
                menuBinding.FragmentCountriesMenuTextView.visibility=View.GONE
                menuBinding.FragmentCountriesMenuProgressBar.visibility=View.GONE
            }
        }

        viewModel.isError.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    menuBinding.FragmentCountriesMenuRecyclerView.visibility=View.GONE
                    menuBinding.FragmentCountriesMenuProgressBar.visibility=View.GONE

                    menuBinding.FragmentCountriesMenuTextView.visibility=View.VISIBLE

                }else{
                    menuBinding.FragmentCountriesMenuTextView.visibility=View.GONE
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            it?.let {

                if (it){
                    menuBinding.FragmentCountriesMenuProgressBar.visibility=View.VISIBLE
                    menuBinding.FragmentCountriesMenuTextView.visibility=View.GONE
                }else{
                    menuBinding.FragmentCountriesMenuProgressBar.visibility=View.GONE
                }
            }
        }
    }


}