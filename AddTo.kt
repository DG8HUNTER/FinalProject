package com.example.expensetrackerproject

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

@SuppressLint("MutableCollectionMutableState")
fun addTo(array: MutableState<MutableList<HashMap<String, Any>>>, document:HashMap<String,Any>): MutableList<HashMap<String, Any>> {
    val newArray: MutableList<HashMap<String, Any>> =array.value
//   newArray[newArray.size]=document
    newArray.add(index = newArray.size , element = document)
    Log.d("newArray" , newArray.toString())
    return  newArray
}


