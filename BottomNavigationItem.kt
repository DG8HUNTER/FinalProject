package com.example.expensetrackerproject

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItemInfo(var name:String,var iconId: Int){
    object Home:BottomNavigationItemInfo(name = "Home" , iconId =R.drawable.home)
    object Categorie:BottomNavigationItemInfo(name="Category" , iconId=R.drawable.categorie)
    object Expenses:BottomNavigationItemInfo(name="Expenses" , iconId= R.drawable.ic_calendar)
    object Settings:BottomNavigationItemInfo(name = "Settings" , iconId=R.drawable.settings)

}
