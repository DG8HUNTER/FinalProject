package com.example.expensetrackerproject.Categories

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class MainActivityViewModel {
    private val _country : MutableState<String?> =
        mutableStateOf(null)

    val country: State<String?> =_country

    private val _name : MutableState<String?> =
        mutableStateOf(null)

    val name: State<String?> =_name

    private val _price :MutableState<Float?> = mutableStateOf(null)
    val price:State<Float?> =_price
    private val _quantity :MutableState<Float?> = mutableStateOf(null)
    val quantity:State<Float?> =_quantity

    private val _day :MutableState<Int?> = mutableStateOf(null)
    private val _month :MutableState<Int?> = mutableStateOf(null)
    private val _year :MutableState<Int?> = mutableStateOf(null)

    val day:State<Int?> =_day
    val month:State<Int?> =_month
    val year:State<Int?> =_year

    fun setValue(newValue:Any?, name:String){
        when(name){
            "country" -> if(newValue!=null)_country.value=newValue.toString()else _country.value=null
            "name" ->if(newValue!=null) _name.value=newValue.toString() else  _name.value=null
            "price" -> if(newValue!=null) _price.value=newValue.toString().toFloat()  else _price.value=null
            "quantity"-> if(newValue!=null)_quantity.value=newValue.toString().toFloat()else _quantity.value=null
            "day"->if(newValue!=null)_day.value=newValue.toString().toInt() else _day.value=null
            "month"->if(newValue!=null)_month.value=newValue.toString().toInt() else _month.value=null
            else ->if(newValue!=null) _year.value=newValue.toString().toInt() else _year.value=null
        }
    }











}