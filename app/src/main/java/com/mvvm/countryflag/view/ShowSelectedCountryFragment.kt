package com.mvvm.countryflag.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.mvvm.countryflag.R
import com.mvvm.countryflag.databinding.FragmentShowSelectedCountryBinding
import com.mvvm.countryflag.viewmodel.SelectedCountryViewModel


class ShowSelectedCountryFragment : Fragment() {

    private lateinit var viewModel:SelectedCountryViewModel
    private lateinit var showSelectedBinding:FragmentShowSelectedCountryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        showSelectedBinding=DataBindingUtil.inflate<FragmentShowSelectedCountryBinding>(inflater,
        R.layout.fragment_show_selected_country,container,false)

        return showSelectedBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selectedUuid=0L
        arguments?.let {
            selectedUuid=ShowSelectedCountryFragmentArgs.fromBundle(it).uuid
        }

        viewModel=ViewModelProvider(this).get(SelectedCountryViewModel::class.java)
        viewModel.getCountry(selectedUuid.toInt())


        observeLiveData()

    }

    private fun observeLiveData(){

        viewModel.selectedCountry.observe(viewLifecycleOwner){

            it?.let {
                showSelectedBinding.selectedCountry=it
            }
        }

    }


}