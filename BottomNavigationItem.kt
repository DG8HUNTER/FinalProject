package com.example.expensetrackerproject

import androidx.annotation.DrawableRes

sealed class BottomNavigationItemInfo(var name:String,@DrawableRes var iconId: Int){
    object Home:BottomNavigationItemInfo(name = "Home" , iconId =R.drawable.home)
    object Categorie:BottomNavigationItemInfo(name="Categorie" , iconId=R.drawable.categorie)
    object Settings:BottomNavigationItemInfo(name = "Settings" , iconId=R.drawable.settings)
}
