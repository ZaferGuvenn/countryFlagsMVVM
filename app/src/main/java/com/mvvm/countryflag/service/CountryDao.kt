package com.mvvm.countryflag.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mvvm.countryflag.model.Country

@Dao
interface CountryDao {


    @Insert
    suspend fun insertAll(vararg country:Country):List<Long>

    @Query("Select * from country")
    suspend fun getAllCountries():List<Country>

    @Query("select * from country where uuid=:countryUuid")
    suspend fun getSelectedCountry(countryUuid:Int):Country

    @Query("Delete from country")
    suspend fun deleteAll()


}