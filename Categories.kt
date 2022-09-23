package com.example.expensetrackerproject.Categories

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable

fun Categories(userUi:String){

    var categories :List<Categorie> = listOf(Categorie.Travel,Categorie.Food, Categorie.Shopping,Categorie.Rent)
    var clickedBox:String  by remember{
        mutableStateOf("Travel")
    }



    val travelDistance=25.dp
    val direction=with(LocalDensity.current){travelDistance.toPx()}



    Column(modifier= Modifier
        .fillMaxSize()
        .padding(20.dp), horizontalAlignment = Alignment.Start , verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = "Categories", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(items = categories) { Category ->


                Box(modifier = Modifier
                    .size(95.dp)
                    .graphicsLayer {
                        translationY =
                            if (clickedBox == Category.name) 1f * direction else 0f * direction
                    }
                    .clickable {
                        clickedBox = Category.name
                        Log.d("Box", clickedBox)
                    }
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(
                        color = Category.boxBackgroundColor,
                        shape = RoundedCornerShape(15.dp)
                    ), contentAlignment = Alignment.Center) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Transparent),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            painter = painterResource(id = Category.icon),
                            contentDescription = "${Category.name} icon",
                            tint = Category.boxContentColor
                        )

                        Text(
                            text = Category.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Category.boxContentColor
                        )

                    }


                }

            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = clickedBox , fontSize = 20.sp , fontWeight = FontWeight.Bold, color=Color.Black )

       when(clickedBox){
           "Travel"-> TravelElement(userUi=userUi)
           "Food"-> FoodElements(userUi=userUi)
           "Shopping"-> ShoppingElements(userUi=userUi)
           else ->RentElements(userUi=userUi)
       }
            }
        }







