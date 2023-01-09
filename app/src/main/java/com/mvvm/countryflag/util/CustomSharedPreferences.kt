package com.mvvm.countryflag.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class CustomSharedPreferences {


    companion object{

        private const val PREFERENCES_TIME="preferences_time"

        private var sharedPreferences:SharedPreferences?=null

        @Volatile private var instance:CustomSharedPreferences?=null

        operator fun invoke(context: Context)= instance?: synchronized(Any()){

            instance?: makeCustomSharedPreferences(context).also{
                instance=it
            }
        }

        private fun makeCustomSharedPreferences(context: Context):CustomSharedPreferences{

            sharedPreferences=context.getSharedPreferences("mySp",MODE_PRIVATE)
            return CustomSharedPreferences()

        }

    }

    fun saveTime(time:Long){

        sharedPreferences?.edit()?.putLong(PREFERENCES_TIME,time)?.commit()
    }

    fun getTime()= sharedPreferences?.getLong(PREFERENCES_TIME,0)


}