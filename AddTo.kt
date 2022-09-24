package com.example.expensetrackerproject

import android.util.Log

fun addTo(array:MutableList<HashMap<String,Any>> , document:HashMap<String,Any>):MutableList<HashMap<String,Any>>{
    val newArray:MutableList<HashMap<String,Any>> =array
//   newArray[newArray.size]=document
    newArray.add(index = newArray.size , element = document)
    Log.d("newArray" , newArray.toString())
    return newArray
}


