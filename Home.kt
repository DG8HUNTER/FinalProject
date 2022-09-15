package com.example.expensetrackerproject.Home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerproject.Categories.Categorie
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.User
import com.example.expensetrackerproject.ui.theme.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap
import kotlin.reflect.KProperty



@Composable
fun Home() {
    var user: MutableMap<String, Any> by remember {
        mutableStateOf(
            hashMapOf(
                "firstName" to "" ,
                "lastName" to "",
                "total" to 0,
                "expenses" to 0,
                "travel" to 0,
                "food" to 0,
                "shopping" to 0,
                "rent" to 0

            )
        )
    }

    var firstName :String by remember{
        mutableStateOf(user["firstName"].toString())
    }
    var lastName :String by remember{
        mutableStateOf(user["lastName"].toString())
    }
    var budget :Int  by remember {
        mutableStateOf(user["total"].toString().toInt())
    }
    var expenses :Int by remember {
        mutableStateOf(user["expenses"].toString().toInt())
        }
    var travel:Int by remember {
        mutableStateOf(user["travel"].toString().toInt())
    }
    var food :Int by remember{
        mutableStateOf(user["food"].toString().toInt())
    }
    var shopping :Int by remember{
        mutableStateOf(user["shopping"].toString().toInt())
    }
    var rent :Int by remember{
        mutableStateOf(user["rent"].toString().toInt())
    }

    val db = Firebase.firestore
    val docRef = db.collection("Users").document("maykel_fayad")
    docRef.get()
        .addOnSuccessListener { document ->
            if (document != null) {
             user = document.data as MutableMap<String, Any>
          Log.d("user" , user.toString())

            } else {
                Log.d("User", "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d("User", "get failed with ", exception)
        }
//    val u = hashMapOf(
//        "first" to "Ada",
//        "last" to "Lovelace",
//        "born" to 1815
//    )
//
//// Add a new document with a generated ID
//    db.collection("User")
//        .add(u)
//        .addOnSuccessListener { documentReference ->
//            Log.d("u", "DocumentSnapshot added with ID: ${documentReference.id}")
//        }
//        .addOnFailureListener { e ->
//            Log.w("u", "Error adding document", e)
//        }
//    db.collection("User")
//        .get()
//        .addOnSuccessListener { result ->
//            for (document in result) {
//                Log.d("u", "${document.id} => ${document.data}")
//            }
//        }
//        .addOnFailureListener { exception ->
//            Log.w("u", "Error getting documents.", exception)
//        }

    Log.d("user" ,user.toString() )
    firstName=user["firstName"].toString()
    Log.d("FirstName" ,firstName.toString() )
    lastName=user["lastName"].toString()
    Log.d("lastName" , lastName.toString())
   budget = user["total"].toString().toInt()
    Log.d("budget" , user["total"].toString())
    expenses=user["expenses"].toString().toInt()
    Log.d("expenses", expenses.toString())
    travel=user["travel"].toString().toInt()
    Log.d("Travel" , travel.toString())
    food =user["food"].toString().toInt()
    Log.d("food" , food.toString())
   shopping =user["shopping"].toString().toInt()
    Log.d("shopping" , shopping.toString())
   rent=user["rent"].toString().toInt()
    Log.d("rent" , travel.toString())



    CreateHome(firstName = firstName, lastName =lastName , budget =budget  , expenses =expenses , travel =travel , food=food , shopping=shopping , rent = rent)
}


@SuppressLint("DefaultLocale")
@Composable
fun CreateHome(firstName:String, lastName:String ,budget:Int , expenses:Int , travel:Int , food:Int  ,shopping:Int , rent:Int ) {
 val travelPercentage = animateFloatAsState(targetValue =if(travel==0) 0f else (travel.toFloat()/expenses.toFloat()) ,
 animationSpec = tween(durationMillis = 300 , easing = FastOutSlowInEasing)
 )
    val travelIndicatorColor= animateColorAsState(targetValue =if(travelPercentage.value==0f)Color.Transparent else Red)
    val travelIndicatorWidth =if(expenses!=0) ((travel*290)/expenses).dp else 0.dp


    val foodPercentage = animateFloatAsState(targetValue =if(food==0) 0f else (food.toFloat()/expenses.toFloat()) ,
        animationSpec = tween(durationMillis = 300 , easing = FastOutSlowInEasing)
    )

    val foodIndicatorColor= animateColorAsState(targetValue =if(foodPercentage.value==0f)Color.Transparent else Darkblue)

    val foodIndicatorWidth =if(expenses!=0) ((food*290)/expenses).dp else 0.dp


    val shoppingPercentage = animateFloatAsState(targetValue =if(shopping==0) 0f else (shopping.toFloat()/expenses.toFloat()) ,
        animationSpec = tween(durationMillis = 300 , easing = FastOutSlowInEasing)
    )

    val shoppingIndicatorColor= animateColorAsState(targetValue =if(shoppingPercentage.value==0f)Color.Transparent else orange)

    val shoppingIndicatorWidth =if(expenses!=0) ((shopping*290)/expenses).dp else 0.dp

    val rentPercentage = animateFloatAsState(targetValue =if(rent==0) 0f else (rent.toFloat()/expenses.toFloat()) ,
        animationSpec = tween(durationMillis = 300 , easing = FastOutSlowInEasing)
    )

    val rentIndicatorColor= animateColorAsState(targetValue =if(rentPercentage.value==0f)Color.Transparent else violet)

    val rentIndicatorWidth =if(expenses!=0) ((rent*290)/expenses).dp else 0.dp










    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "Welcome  ${firstName.capitalize()}   ",
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {

       HomeCircularIndicator(budget =budget, expenses = expenses)


            }
            item {
                Text(
                    text = "Categories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(shape = RoundedCornerShape(25.dp)), color = pink
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(Modifier.weight(9f) , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.spacedBy(10.dp)){
                            Icon(
                                painter = painterResource(id = R.drawable.travel),
                                contentDescription = "travel icon",
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(travelIndicatorWidth).height(15.dp)
                                .background(color=travelIndicatorColor.value , shape = RoundedCornerShape(5.dp))
                                .clip(shape = RoundedCornerShape(5.dp)))
                        }
                        Text(
                            text = "${(travelPercentage.value*100).toLong()} %",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Red
                        )
                    }
                }
            }
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(shape = RoundedCornerShape(25.dp)), color = lightBlue
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(Modifier.weight(9f) , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.spacedBy(10.dp)){
                            Icon(
                                painter = painterResource(id = R.drawable.restaurant),
                                contentDescription = "food icon",
                                tint = Darkblue
                            )
                            Spacer(modifier = Modifier.width(foodIndicatorWidth).height(15.dp)
                                .background(color=foodIndicatorColor.value , shape = RoundedCornerShape(5.dp))
                                .clip(shape = RoundedCornerShape(5.dp)))
                        }
                        Text(
                            text = "${(foodPercentage.value*100).toLong()} %",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Darkblue
                        )
                    }
                }

            }

            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(shape = RoundedCornerShape(25.dp)), color = lightOrange
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(Modifier.weight(9f) , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.spacedBy(10.dp)){
                            Icon(
                                painter = painterResource(id = R.drawable.shopping),
                                contentDescription = "food icon",
                                tint = orange
                            )
                            Spacer(modifier = Modifier.width(shoppingIndicatorWidth).height(15.dp)
                                .background(color=shoppingIndicatorColor.value , shape = RoundedCornerShape(5.dp))
                                .clip(shape = RoundedCornerShape(5.dp)))
                        }
                        Text(
                            text = "${(shoppingPercentage.value*100).toLong()} %",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = orange
                        )
                    }
                }


            }

            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(shape = RoundedCornerShape(25.dp)), color = lightViolet
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(Modifier.weight(9f) , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.spacedBy(10.dp)){
                            Icon(
                                painter = painterResource(id = R.drawable.deal),
                                contentDescription = "deal icon",
                                tint = violet
                            )
                            Spacer(modifier = Modifier.width(rentIndicatorWidth).height(15.dp)
                                .background(color=rentIndicatorColor.value , shape = RoundedCornerShape(5.dp))
                                .clip(shape = RoundedCornerShape(5.dp)))
                        }
                        Text(
                            text = "${(rentPercentage.value*100).toLong()} %",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = violet
                        )
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))

            }
        }

    }
}




