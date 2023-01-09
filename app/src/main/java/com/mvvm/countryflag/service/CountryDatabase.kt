package com.mvvm.countryflag.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mvvm.countryflag.model.Country

@Database(entities = arrayOf(Country::class), version = 1, exportSchema = false)
abstract class CountryDatabase:RoomDatabase() {
    abstract fun countryDao():CountryDao


    //singleton

    companion object{

        @Volatile private var instance: CountryDatabase? =null

        operator fun invoke(context: Context)= instance?: synchronized(Any()){

            instance ?: makeDatabase(context).also{
                instance=it
            }
        }

        fun makeDatabase(context: Context)= Room.databaseBuilder(context,CountryDatabase::class.java,"Countries")
            .build()

    }
}