package com.example.expensetrackerproject.Home

import android.system.Os.remove
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Delete
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerproject.Categories.Categorie
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.addTo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable

fun DisplayExpenses(navController: NavController,category:String , userUi:String){
    val db = Firebase.firestore

    var expenses:MutableList<HashMap<String,Any>> by remember{
        mutableStateOf(
            mutableListOf(
                hashMapOf()
            )
        )
    }
    val delete = SwipeAction(
        onSwipe = {

        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(8.dp)
            )

        },
        background = Color.Red,


        )

    db.collection("expenses")
        .whereEqualTo("category", category)
        .whereEqualTo("id",userUi)
        .get()
        .addOnSuccessListener { documents ->
            expenses= mutableListOf()
            for (document in documents) {
                Log.d("TAG", "${document.id} => ${document.data}")
                expenses= addTo(expenses,document.data as HashMap<String,Any>)
            }
        }
        .addOnFailureListener { exception ->
            Log.w("TAG", "Error getting documents: ", exception)
        }
    Column(modifier= Modifier
        .fillMaxSize()
        .padding(20.dp) , horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.spacedBy(20.dp)){
        Row(modifier=Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.Start){
            IconButton(onClick = {navController.navigate(route="MainPage/$userUi") }) {


                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "arrow left",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(20.dp))


            Text(text =category , fontSize = 20.sp, fontWeight = FontWeight.Bold)

        }
        LazyColumn(modifier=Modifier.fillMaxSize() , horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.spacedBy(15.dp)  ){

            items(items=expenses){
                expense ->
                ExpenseViewHolder( category = category , expense=expense, delete = delete)
            }

        }


    }
}

@Composable
fun ExpenseViewHolder(category: String,expense:HashMap<String,Any> , delete:SwipeAction) {

    SwipeableActionsBox(
        modifier = Modifier.fillMaxWidth(),
        swipeThreshold = 100.dp,
        endActions = listOf(delete),
        backgroundUntilSwipeThreshold = Color.Transparent
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                if (category == "Travel") {
                    Text(
                        text = expense["country"].toString(),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                } else {
                    Text(
                        text = expense["name"].toString(),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
                Text(
                    text = expense["date"].toString(),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }
            if (category != "Travel" && category != "Rent") {
                Text(
                    text = "Quantity: ${expense["quantity"].toString()}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }
            Text(
                text = "Price: ${expense["price"].toString()}",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )


            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(1.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .clip(shape = RoundedCornerShape(5.dp))
            )
        }

    }
}


