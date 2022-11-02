package com.example.expensetrackerproject.Home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerproject.Categories.mainActivityViewModel
import com.example.expensetrackerproject.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import java.util.*
import kotlin.collections.HashMap


@SuppressLint("MutableCollectionMutableState")
@Composable

fun DisplayExpenses(navController: NavController,category:String , userUi:String) {
    val db = Firebase.firestore
    var launched:Boolean by remember {
        mutableStateOf(false)
    }
    var deletetintColor:Color by remember{
        mutableStateOf(Color.Black)
    }
    val travelDistance = 25.dp
    val direction = with(LocalDensity.current) { travelDistance.toPx() }

    val rotate = animateFloatAsState(targetValue =if(!launched)0f else 720f, animationSpec =tween(durationMillis = 4000, easing = FastOutSlowInEasing))
    LaunchedEffect(key1 =true ){
        launched=!launched
    }
var value:Float by remember {
    mutableStateOf(0f)

}
    var expense:Float by remember{
        mutableStateOf(0f)
    }
    val docRef = db.collection("expenses").orderBy("tempStamp", Query.Direction.DESCENDING)
    docRef.get()
        .addOnSuccessListener { documents ->
//            expenses = mutableListOf()
            mainActivityViewModel.setValue(mutableListOf<HashMap<String,Any>>(),"expense")
            for (document in documents) {
                if (document["category"] == category.capitalize(Locale.ROOT) && document["id"] == userUi) {
                    Log.d("TAG", "${document.id} => ${document.data}")
//                    expenses = addTo(expenses, document.data as HashMap<String, Any>)
                    mainActivityViewModel.AddTo_expense(document.data as HashMap<String,Any>)
                }
            }
        }
    docRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w("user", "Listen failed.", e)
            return@addSnapshotListener
        }

        if (snapshot != null && snapshot.documents.isNotEmpty()) {
//            expenses = mutableListOf()
            mainActivityViewModel.setValue(mutableListOf<HashMap<String,Any>>(),"expense")
            Log.d("user", "Current data: ${snapshot.documents}")
            for (document in snapshot.documents) {
                if (document["category"] == category.capitalize(Locale.ROOT) && document["id"] == userUi) {
//                    expenses = addTo(expenses, document.data as HashMap<String, Any>)
                    mainActivityViewModel.AddTo_expense(document.data as HashMap<String,Any>)
                }
            }
        } else {
            Log.d("user", "Current data: null")
        }
    }

   val doc= db.collection("Users").document(userUi)
       doc .get()
        .addOnSuccessListener { document ->
            if (document != null) {
                 value=document.data?.get(category).toString().toFloat()
                expense= document.data?.get("expenses").toString().toFloat()
            } else {
                Log.d("TAG", "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d("TAG", "get failed with ", exception)
        }

    doc.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w("user", "Listen failed.", e)
            return@addSnapshotListener
        }

        if (snapshot != null && snapshot.exists()) {
            Log.d("user", "Current data: ${snapshot.data}")
            value=snapshot.data?.get(category).toString().toFloat()
            expense = snapshot.data?.get("expenses").toString().toFloat()
            Log.d("exp",expense.toString())
        } else {
            Log.d("user", "Current data: null")
        }
    }
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { navController.navigate(route = "MainPage/$userUi"){
                    popUpTo(0)
                } }) {


                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "arrow left",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                  if(category!="rent") {
                      Text(
                          text = category.replaceFirstChar {
                              if (it.isLowerCase()) it.titlecase(
                                  Locale.ROOT
                              ) else it.toString()
                          },
                          fontSize = 20.sp, fontWeight = FontWeight.Bold
                      )
                  } else {
                      Column(verticalArrangement = Arrangement.Center) {
                          Spacer(modifier=Modifier.height(35.dp))
                          Row( horizontalArrangement = Arrangement.Start) {
                              Text(
                                  text = category.replaceFirstChar {
                                      if (it.isLowerCase()) it.titlecase(
                                          Locale.ROOT
                                      ) else it.toString()
                                  },
                                  fontSize = 20.sp, fontWeight = FontWeight.Bold
                              )
                          }
                          Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start){
                              Spacer(modifier=Modifier.width(30.dp))
                              Icon(
                                  painter = painterResource(id = R.drawable.for_rent),
                                  contentDescription = "rent icon",
                                  tint = Color.Unspecified,
                                  modifier = Modifier.size(42.dp)
                              )

                          }
                      }

                  }



//                    when (category) {
//                        "travel" -> Image(
//                            painter = painterResource(id = R.drawable.planet_earth),
//                            contentDescription = "earth icon",
//                            modifier = Modifier
//                                .size(42.dp)
//                                .clip(shape = CircleShape)
//                                .shadow(elevation = 10.dp, shape = CircleShape)
//                                .rotate(rotate.value)
//
//                        )
////
//
//                    }

                    Image(
                        painter = painterResource(id = when(category){
                            "travel" ->R.drawable.planet_earth
                            "food" -> R.drawable.grocery_cart
                            "shopping"->R.drawable.shopping_cart
                            else -> R.drawable.for_rent
                        }),
                        contentDescription = "earth icon",
                        modifier = Modifier
                            .size(42.dp)
                            .clip(shape = CircleShape)
                            .shadow(elevation = when(category){
                                "travel" -> 10.dp
                                else ->0.dp
                            } , shape = if(category=="travel") CircleShape else RectangleShape)
                            .rotate(if(category=="travel") rotate.value else 0f)
//                            .graphicsLayer {
//                               translationX= when(category){
//                                   "food" ->direction
//                                   "shopping" ->direction
//                                   else ->0f
//                               }
//                            }

                    )
                }
            }










        if (mainActivityViewModel.expense.value.size == 0) {
            Column(modifier= Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                 , horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(
                    text = "No $category expenses yet.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, color = Color.Black,

                )

            }
        } else {
            Spacer(modifier=Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(items = mainActivityViewModel.expense.value) { expense ->
                    val delete = SwipeAction(
                        onSwipe = {
                            if (category.capitalize(Locale.ROOT) == "Travel") {
                                Log.d("ex", expense.toString())
                                db.collection("expenses")
                                    .whereEqualTo("category", expense["category"])
                                    .whereEqualTo("country", expense["country"])
                                    .whereEqualTo("id", expense["id"])
                                    .whereEqualTo("date", expense["date"])
                                    .whereEqualTo("price", expense["price"])
                                    .get()
                                    .addOnSuccessListener { result ->
                                        for (document in result) {
                                            Log.d("dd", "${document.id} => ${document.data}")
                                            db.collection("Users")
                                                .document(userUi)
                                                .update(
                                                    "travel",
                                                    FieldValue.increment(
                                                        -(expense["price"].toString().toFloat()
                                                            .toLong())
                                                    )
                                                )
                                            db.collection("Users")
                                                .document(userUi)
                                                .update(
                                                    "expenses",
                                                    FieldValue.increment(
                                                        -(expense["price"].toString().toFloat()
                                                            .toLong())
                                                    )
                                                )
                                            db.collection("expenses").document(document.id).delete()
                                            break

                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w("TAG", "Error getting documents.", exception)
                                    }
                            } else {
                                db.collection("expenses")
                                    .whereEqualTo("category", expense["category"])
                                    .whereEqualTo("name", expense["name"])
                                    .whereEqualTo("id", expense["id"])
                                    .whereEqualTo("price", expense["price"])
                                    .whereEqualTo("date", expense["date"])
                                    .whereEqualTo("quantity", expense["quantity"])
                                    .get()
                                    .addOnSuccessListener { result ->
                                        for (document in result) {
                                            db.collection("Users").document(userUi)
                                                .update(
                                                    category,
                                                    FieldValue.increment(
                                                        -(expense["price"]).toString().toFloat()
                                                            .toLong()
                                                    )
                                                )

                                            db.collection("Users")
                                                .document(userUi)
                                                .update(
                                                    "expenses",
                                                    FieldValue.increment(
                                                        -(expense["price"].toString().toFloat()
                                                            .toLong())
                                                    )
                                                )
                                            db.collection("expenses").document(document.id).delete()
                                            break
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.d("TAG", "Error getting documents: ", exception)
                                    }

                            }

                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.delete),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.padding(start = 15.dp)
                            )

                        },
                        background = Color.Red,
                    )


                    ExpenseViewHolder(category = category, expense = expense, delete = delete)
                }
            }

            }
//            Spacer(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(1.dp)
//                    .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
//                    .clip(shape = RoundedCornerShape(5.dp))
//            )
       Row(modifier=Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround){
           Text(text = "${category.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }} : $value",
               fontSize = 18.sp,
               fontWeight = FontWeight.Medium,
               color = Color.Gray

           )
           Text(text ="Expenses : $expense",
               fontSize = 18.sp,
               fontWeight = FontWeight.Medium,
               color = Color.Gray
           )
       }
//            Spacer(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(1.dp)
//                    .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
//                    .clip(shape = RoundedCornerShape(5.dp))
//            )



    }
}


@Composable
fun ExpenseViewHolder(category: String,expense:HashMap<String,Any> , delete:SwipeAction) {

    SwipeableActionsBox(
        modifier = Modifier.fillMaxWidth(),
        swipeThreshold = 100.dp,
        endActions = listOf(delete)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(9f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(
                        id = when (category) {
                            "travel" -> R.drawable.map
                            "food"-> R.drawable.foodbag
                            "shopping" -> R.drawable.shopping_bags
                            else ->R.drawable.coins
                        }
                    ),
                    contentDescription = "map icon",
                    modifier = Modifier.size(42.dp)
                )


                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        if (category.capitalize(Locale.ROOT) == "Travel") {
                            Text(
                                text = expense["country"].toString()
                                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        } else {
                            Text(
                                text = expense["name"].toString()
                                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                        Text(
                            text = expense["date"].toString(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    }
                    Row( horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically){
                 Icon(painter = painterResource(id = R.drawable.ic_location), contentDescription ="location icon",tint=Color(0xFF7180D1).copy(0.6f))
                        Spacer(modifier =Modifier.width(3.dp))
                        Text(text= expense["location"].toString()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color =Color(0xFF7180D1).copy(0.6f))
                    }
                    if (category.capitalize(Locale.ROOT) != "Travel") {
                        Text(
                            text = "Quantity: ${expense["quantity"].toString()}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    }
                    Text(
                        text = "Price: ${expense["price"].toString()}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )

                }

                Icon(
                    painter = painterResource(id = R.drawable.swipeabledelete),
                    contentDescription = " swipe delete icon",
                    tint = Color.Black,
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .clip(shape = RoundedCornerShape(5.dp))
            )

        }
    }
}





