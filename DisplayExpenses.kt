package com.example.expensetrackerproject.Home

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.system.Os.remove
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerproject.Categories.Categorie
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.addTo
import com.google.apphosting.datastore.testing.DatastoreTestTrace
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
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

    var expenses: MutableList<HashMap<String, Any>> by remember {
        mutableStateOf(

            mutableListOf(

            )
        )
    }

    val docRef = db.collection("expenses").orderBy("tempStamp", Query.Direction.DESCENDING)
    docRef.get()
        .addOnSuccessListener { documents ->
            expenses = mutableListOf()
            for (document in documents) {
                if (document["category"] == category.capitalize(Locale.ROOT) && document["id"] == userUi) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    expenses = addTo(expenses, document.data as HashMap<String, Any>)
                }
            }
        }
    docRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w("user", "Listen failed.", e)
            return@addSnapshotListener
        }

        if (snapshot != null && snapshot.documents.isNotEmpty()) {
            expenses = mutableListOf()
            Log.d("user", "Current data: ${snapshot.documents}")
            for (document in snapshot.documents) {
                if (document["category"] == category.capitalize(Locale.ROOT) && document["id"] == userUi) {
                    expenses = addTo(expenses, document.data as HashMap<String, Any>)
                }
            }
        } else {
            Log.d("user", "Current data: null")
        }
    }


    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.navigate(route = "MainPage/$userUi") }) {


                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "arrow left",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(20.dp))


            Text(
                text = category.capitalize(Locale.ROOT),
                fontSize = 20.sp, fontWeight = FontWeight.Bold
            )

        }

        if (expenses.size == 0) {
            Column(modifier=Modifier.fillMaxSize() , horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(
                    text = "No $category expenses yet.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, color = Color.Black,
                   
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {

                items(items = expenses) { expense ->
                    val delete = SwipeAction(
                        onSwipe = {
                            expenses = remove(expenses, expense)
                            Log.d("ex", expense.toString())
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
    }
}

@Composable
fun ExpenseViewHolder(category: String,expense:HashMap<String,Any> , delete:SwipeAction) {

    SwipeableActionsBox(
        modifier = Modifier.fillMaxWidth(),
        swipeThreshold = 100.dp,
        endActions = listOf(delete)
    ) {
        Column(modifier= Modifier
            .fillMaxWidth()
            .background(color = Color.White) , verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.fillMaxWidth(9f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                                text = expense["country"].toString().capitalize(Locale.ROOT),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        } else {
                            Text(
                                text = expense["name"].toString().capitalize(Locale.ROOT),
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
                    if (category.capitalize(Locale.ROOT) != "Travel") {
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

                }

                Icon(
                    painter = painterResource(id = R.drawable.swipeabledelete),
                    contentDescription = " swipe delete icon",
                    tint = Color.Black
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
fun remove(expenses:MutableList<HashMap<String,Any>> ,expense:HashMap<String,Any>):MutableList<HashMap<String,Any>>{
    val newArray:MutableList<HashMap<String,Any>> = mutableListOf()
    expenses.forEach {
        exp ->
          if(exp!= expense){
              newArray.add( index=newArray.size , element = exp)
          }
    }
    Log.d("newArray" , newArray.toString())
    return newArray
}




