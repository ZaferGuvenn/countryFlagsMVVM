package com.mvvm.countryflag.service

import com.mvvm.countryflag.model.Country
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryAPIService {

    val BASE_URL="https://raw.githubusercontent.com/"

    val api=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build().create(CountryAPI::class.java)

    fun getCountriesFromRetrofitApi():Single<List<Country>>{
        return api.getCountries()
    }
}