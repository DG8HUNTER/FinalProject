package com.example.expensetrackerproject

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerproject.Categories.Categories
import com.example.expensetrackerproject.Home.Home
import com.example.expensetrackerproject.Settings.Settings
import com.example.expensetrackerproject.ui.theme.ExpenseTrackerProjectTheme
import com.example.expensetrackerproject.ui.theme.lightGreen


@Composable
fun MainPage(navController:NavController , userUi:String) {
        val items: List<BottomNavigationItemInfo> = listOf(
            BottomNavigationItemInfo.Home,
            BottomNavigationItemInfo.Categorie,
            BottomNavigationItemInfo.Settings
        )
        var selectedButton by remember {
            mutableStateOf("Home")
        }

        Scaffold(bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,

                contentColor = Color.Black,
               
                modifier = Modifier.height(60.dp)
            ) {

                items.forEach { Item ->
                    BottomNavigationItem(
                        selected = selectedButton == Item.name,
                        onClick = { selectedButton = Item.name


                        },
                        label = {
                            Text(text = if (selectedButton == Item.name) "" else Item.name)
                        },
                        icon = {

                            Icon(
                                painter = painterResource(id = Item.iconId),
                                contentDescription = "${Item.name} icon",

                                )
                        }
                    )
                }
            }

        })
        {
            Column(modifier=Modifier.fillMaxSize().background(color=Color.White)) {
                when (selectedButton) {
                    "Home" ->{ Home(userUi = userUi, navController = navController)
                    }
                    "Category" -> Categories(userUi=userUi)
                    "Settings" -> Settings(navController =navController , userUi=userUi)
                }

            }
        }
    }

        