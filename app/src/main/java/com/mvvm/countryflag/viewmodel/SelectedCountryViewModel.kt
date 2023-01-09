package com.mvvm.countryflag.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mvvm.countryflag.model.Country
import com.mvvm.countryflag.service.CountryDatabase
import kotlinx.coroutines.launch

class SelectedCountryViewModel(application: Application):BaseViewModel(application) {

    val selectedCountry=MutableLiveData<Country>()


    fun getCountry(uuid:Int){
        launch {

            val dao=CountryDatabase(getApplication()).countryDao()
            selectedCountry.value=dao.getSelectedCountry(uuid)
        }
    }
}