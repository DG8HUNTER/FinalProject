package com.example.expensetrackerproject.Categories

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.*

sealed class Categorie(var name :String, @DrawableRes var icon:Int, var boxBackgroundColor: Color, var boxContentColor: Color){
    object Travel :Categorie(name="Travel" , icon = R.drawable.travel , boxBackgroundColor = pink , boxContentColor = red)
    object Food :Categorie(name="Food" , icon =R.drawable.restaurant , boxBackgroundColor = lightBlue, boxContentColor = blue1 )
    object Shopping :Categorie(name="Shopping", icon=R.drawable.shopping, boxBackgroundColor = lightOrange, boxContentColor = orange)
    object Rent:Categorie(name="Rent" , icon=R.drawable.key, boxBackgroundColor = lightViolet, boxContentColor = darkViolet)


}

