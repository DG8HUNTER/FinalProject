package com.example.expensetrackerproject

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerproject.Categories.Categorie
import com.example.expensetrackerproject.Categories.Categories
import com.example.expensetrackerproject.Categories.mainActivityViewModel
import com.example.expensetrackerproject.Expenses.Expenses
import com.example.expensetrackerproject.Home.Home
import com.example.expensetrackerproject.Settings.Settings
import com.example.expensetrackerproject.ui.theme.*


@Composable
fun MainPage(navController:NavController , userUi:String) {
    val items: List<BottomNavigationItemInfo> = listOf(
        BottomNavigationItemInfo.Home,
        BottomNavigationItemInfo.Categorie,
        BottomNavigationItemInfo.Expenses,
        BottomNavigationItemInfo.Settings
    )

    var state by remember { mutableStateOf(0) }
    val titles = listOf("General", "Statistics")
    var tabSelected by remember {
        mutableStateOf("General")
    }
    var categoryState by remember { mutableStateOf(0) }

    val categories: List<Categorie> =
        listOf(Categorie.Travel, Categorie.Food, Categorie.Shopping, Categorie.Rent)
    var categorySelected: String by remember {
        mutableStateOf("Travel")
    }


    Scaffold(topBar = {
        when (mainActivityViewModel.selectedButton.value) {
            "Home" -> Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Home  ",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                TabRow(selectedTabIndex = state, contentColor = mint , backgroundColor = Color.White) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = state == index,
                            onClick = {
                                state = index
                                tabSelected = title
                            },
                            text = {
                                Text(
                                    text = title,
                                    color = if (state == index) mint else mediumGray
                                )
                            },

                            )

                    }
                }
            }
//
            "Category" -> Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Categories  ",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

//                LazyRow(modifier=Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//                    item {
                        ScrollableTabRow(
                            selectedTabIndex = categoryState,
                            contentColor = mint,
                            backgroundColor = White,
                            edgePadding = 0.dp
                        ) {

                            categories.forEachIndexed { index, category ->

                                Tab(
                                    selected = categoryState == state,
                                    onClick = {
                                        categoryState = index
                                        categorySelected = category.name

                                    },
                                    text = {
                                        Text(
                                            text = category.name,
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            color = if (categoryState == index) mint else mediumGray
                                        )
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = category.icon),
                                            contentDescription = "${category.name} icon",
                                            tint = if (categoryState == index) mint else mediumGray
                                        )
                                    },
                                    modifier = Modifier
                                        .background(color = Color.White),


                                    )


                            }


                        }
                    }
                }


//            }
//        }


    }, bottomBar = {
        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = Color.Black,
            elevation = 0.dp,
            modifier = Modifier.height(50.dp)


        ) {

            items.forEach { Item ->
                BottomNavigationItem(
                    selected = mainActivityViewModel.selectedButton.value == Item.name,
                    onClick = {
                       mainActivityViewModel.setValue(Item.name , "_selectedButton")
                    },
                    label = {
                        Text(text = if (mainActivityViewModel.selectedButton.value == Item.name) "" else Item.name)
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

    }, modifier = Modifier.padding(bottom = 5.dp))
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            when (mainActivityViewModel.selectedButton.value) {
                "Home" -> {
                    Home(userUi = userUi, navController = navController, tabSelected = tabSelected)
                }
                "Category" -> Categories(userUi = userUi, categorySelected = categorySelected)
                "Expenses" -> Expenses(userUi = userUi)
                else -> Settings(navController = navController, userUi = userUi)
            }

        }
    }
}


