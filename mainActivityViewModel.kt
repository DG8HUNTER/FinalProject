package com.example.expensetrackerproject.Categories

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.expensetrackerproject.addTo

class MainActivityViewModel {

    private val _country : MutableState<String?> =
        mutableStateOf(null)

    val country: State<String?> =_country

    private val _name : MutableState<String?> =
        mutableStateOf(null)

    val name: State<String?> =_name
    private val _location : MutableState<String?> =
        mutableStateOf(null)

    val location: State<String?> =_location

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
     private  val _password:MutableState<String?> = mutableStateOf(null)
    val password:State<String?> =_password

    fun setValue(newValue:Any?, name:String){
        when(name){
            "country" -> if(newValue!=null)_country.value=newValue.toString()else _country.value=null
            "name" ->if(newValue!=null) _name.value=newValue.toString() else  _name.value=null
            "location" ->if(newValue!=null)_location.value=newValue.toString() else _location.value=null
            "price" -> if(newValue!=null&& newValue!=0.0f) _price.value=newValue.toString().toFloat()  else _price.value=null
            "quantity"-> if(newValue!=null && newValue!=0.0f)_quantity.value=newValue.toString().toFloat()else _quantity.value=null
            "day"->if(newValue!=null)_day.value=newValue.toString().toInt() else _day.value=null
            "month"->if(newValue!=null)_month.value=newValue.toString().toInt() else _month.value=null
            "year" ->if(newValue!=null) _year.value=newValue.toString().toInt() else _year.value=null
            "password"->if(newValue!=null) _password.value=newValue.toString() else _password.value=null
            else -> _expense.value= newValue as MutableList<HashMap<String, Any>>
        }
    }
    fun AddTo_expense(newValue: HashMap<String,Any>?){
        _expense.value= addTo(_expense, document = newValue as HashMap<String,Any>)
    }

    @SuppressLint("MutableCollectionMutableState")
    private val _expense :MutableState<MutableList<HashMap<String,Any>>> =
        mutableStateOf(
            mutableListOf()
        )

    val expense :State<MutableList<HashMap<String,Any>>> = _expense














}