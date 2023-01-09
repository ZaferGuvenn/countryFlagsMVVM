package com.mvvm.countryflag.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.mvvm.countryflag.model.Country
import com.mvvm.countryflag.service.CountryAPIService
import com.mvvm.countryflag.service.CountryDatabase
import com.mvvm.countryflag.util.CustomSharedPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class CountriesMenuFragmentViewModel(application: Application):BaseViewModel(application) {

    private var composite=CompositeDisposable()

    private var countryAPIService=CountryAPIService()

    private var customPreferences=CustomSharedPreferences(getApplication())
    private var refreshTime=1*60*1000*1000*1000L//60 saniye

    val countries= MutableLiveData<List<Country>>()
    val isError=MutableLiveData<Boolean>()
    val isLoading=MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime=customPreferences.getTime()

        if (updateTime!=0L&&updateTime!=null&&refreshTime>System.nanoTime()-updateTime){
            getWithSQLite()

        }else{
            getFromInternet()

        }
    }

    fun getFromInternet(){

        isLoading.value=true

        composite.add(
            countryAPIService.getCountriesFromRetrofitApi()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        isLoading.value=false
                        storeInSQLite(t)
                        Toast.makeText(getApplication(),"From internet",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        isLoading.value=false
                        isError.value=true
                        e.printStackTrace()
                    }

                })
        )




    }

    fun storeInSQLite(countryList:List<Country>){

        isLoading.value=true

        launch {
            val dao= CountryDatabase(getApplication()).countryDao()

            dao.deleteAll()

            val listLong=dao.insertAll(*countryList.toTypedArray())

            var i=0
            for (j in listLong){
                countryList.get(i).uuid= j.toInt()
                i += 1
            }

            customPreferences.saveTime(System.nanoTime())



            showCountries(countryList)
        }


    }

    fun getWithSQLite(){


        launch {
            val dao=CountryDatabase(getApplication()).countryDao()
            val countries=dao.getAllCountries()
            showCountries(countries)

            Toast.makeText(getApplication(), "From SQLite",Toast.LENGTH_LONG).show()
        }

    }

    fun showCountries(countryList: List<Country>){

        isLoading.value=false
        isError.value=false
        countries.value=countryList

    }

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }
}