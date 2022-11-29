package com.example.expensetrackerproject.Categories

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerproject.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.annotation.meta.When


@Composable

fun Categories(userUi:String, categorySelected:String) {
    val db = Firebase.firestore
    val interactionSource = remember { MutableInteractionSource() }
    val categories: List<Categorie> =
        listOf(Categorie.Travel, Categorie.Food, Categorie.Shopping, Categorie.Rent)

    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = true) {
        mainActivityViewModel.setValue(null, "country")
        mainActivityViewModel.setValue(null, "name")
        mainActivityViewModel.setValue(null,"location")
        mainActivityViewModel.setValue(null, "price")
        mainActivityViewModel.setValue(null, "quantity")
        mainActivityViewModel.setValue(null, "day")
        mainActivityViewModel.setValue(null, "month")
        mainActivityViewModel.setValue(null, "year")
        focusManager.clearFocus()
    }

    val locationClearIcon = animateColorAsState(
        targetValue = if (mainActivityViewModel.location.value != null) Color.Gray else Color.Transparent,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )
    val countryClearIcon = animateColorAsState(
        targetValue = if (mainActivityViewModel.country.value != null) Color.Gray else Color.Transparent,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )
    val nameClearIcon = animateColorAsState(
        targetValue = if (mainActivityViewModel.name.value != null) Color.Gray else Color.Transparent,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )
    val priceClearIcon = animateColorAsState(
        targetValue = if (mainActivityViewModel.price.value != null) Color.Gray else Color.Transparent,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )
    val quantityClearIcon = animateColorAsState(
        targetValue = if (mainActivityViewModel.quantity.value != null) Color.Gray else Color.Transparent,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )


    val travelDistance = 25.dp
    val direction = with(LocalDensity.current) { travelDistance.toPx() }


    Column(
        modifier = Modifier
            .fillMaxSize().padding(15.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
//        Text(text = "Categories", fontSize = 25.sp, fontWeight = FontWeight.Bold)
//        LazyRow(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(150.dp),
//            horizontalArrangement = Arrangement.spacedBy(15.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            items(items = categories) { Category ->
//
//
//                Box(modifier = Modifier
//                    .size(95.dp)
//                    .graphicsLayer {
//                        translationY =
//                            if (clickedBox == Category.name) 1f * direction else 0f * direction
//                    }
//                    .clickable(interactionSource = interactionSource, indication = null) {
//                        clickedBox = Category.name
//                        Log.d("Box", clickedBox)
//                    }
//                    .clip(shape = RoundedCornerShape(15.dp))
//                    .background(
//                        color = Category.boxBackgroundColor,
//                        shape = RoundedCornerShape(15.dp)
//                    ), contentAlignment = Alignment.Center) {
//
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(color = Color.Transparent),
//                        verticalArrangement = Arrangement.spacedBy(5.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//
//                        Icon(
//                            painter = painterResource(id = Category.icon),
//                            contentDescription = "${Category.name} icon",
//                            tint = Category.boxContentColor
//                        )
//
//                        Text(
//                            text = Category.name,
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Medium,
//                            color = Category.boxContentColor
//                        )
//
//                    }
//
//
//                }
//
//            }
//
//        }
//        Spacer(modifier = Modifier.height(10.dp))
//        Text(text = clickedBox, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)

//       when(clickedBox){
//           "Travel"-> TravelElement(userUi=userUi)
//           "Food"-> FoodElements(userUi=userUi)
//           "Shopping"-> ShoppingElements(userUi=userUi)
//           else ->RentElements(userUi=userUi)
//       }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {

                OTextField(
                    name = when (categorySelected) {
                        "Travel" -> "country"
                        else -> "name"
                    },
                    placeHolder = when (categorySelected) {
                        "Travel" -> "Enter country"
                        else -> "Enter name "
                    },

                    leadingIcon = when (categorySelected) {
                        "Travel" -> R.drawable.flag
                        "Food" -> Categorie.Food.icon
                        "Shopping" -> Categorie.Shopping.icon
                        else -> Categorie.Rent.icon
                    },
                    trailingIcon = Icons.Filled.Clear,
                    colorAnimation = when(categorySelected){
                        "Travel"->countryClearIcon.value
                        else ->nameClearIcon.value
                                                     },
                    focusManager = focusManager
                )
            }
            item{
                OTextField(
                    name ="location",
                    placeHolder ="Enter the location",
                    leadingIcon =R.drawable.ic_location ,
                    trailingIcon = Icons.Filled.Clear,
                    colorAnimation =locationClearIcon.value ,
                    focusManager = focusManager
                )
            }
            if (categorySelected != "Travel") {
                item {
                    OTextField(
                        name = "quantity",
                        placeHolder = "Enter Quantity",

                        leadingIcon = R.drawable.basket,
                        trailingIcon = Icons.Filled.Clear,
                        colorAnimation = quantityClearIcon.value,
                        focusManager = focusManager
                    )
                }
            }


            item {
                OTextField(
                    name = "price",
                    placeHolder = "Enter price",
                    leadingIcon = R.drawable.ic_dollar,
                    trailingIcon = Icons.Filled.Clear,
                    colorAnimation = priceClearIcon.value,
                    focusManager = focusManager
                )
            }
            item {
                DateTextField(
                    color = when (categorySelected) {
                        "Travel" -> Categorie.Travel.boxContentColor
                        "Food" -> Categorie.Food.boxContentColor
                        "Shopping" -> Categorie.Shopping.boxContentColor
                        else -> Categorie.Rent.boxContentColor
                    }, focusManager = focusManager, lightColor =
                    when (categorySelected) {
                        "Travel" -> Categorie.Travel.boxBackgroundColor
                        "Food" -> Categorie.Food.boxBackgroundColor
                        "Shopping" -> Categorie.Shopping.boxBackgroundColor
                        else -> Categorie.Rent.boxBackgroundColor
                    }
                )
            }
            item {
                Buttons(
                    db = db, userUi = userUi, category = categorySelected
                )
            }
            item {
                Spacer(modifier = Modifier.height(165.dp))
            }
        }
    }
}







