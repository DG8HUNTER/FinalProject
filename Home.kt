package com.example.expensetrackerproject.Home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
                "expenses" to 0


            )
        )
    }

    var firstName :String by remember{
        mutableStateOf(user["firstName"].toString())
    }
    var lastName :String by remember{
        mutableStateOf(user["lastName"].toString())
    }
    var total :Int  by remember {
        mutableStateOf(user["total"].toString().toInt())
    }
    var expense :Int by remember {
        mutableStateOf(user["expenses"].toString().toInt())
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
    total = user["total"].toString().toInt()
    Log.d("total" , user["total"].toString())
    expense=user["expenses"].toString().toInt()
    Log.d("expenses", expense.toString())

    CreateHome(firstName = firstName, lastName =lastName , total =total  , expense =expense)
}


@Composable
fun CreateHome(firstName:String?, lastName:String? , total:Int , expense:Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "Welcome  ${firstName} ${lastName}  ",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(text = "$firstName  $lastName $total $expense", fontSize = 18.sp)

        // Text(text = "Budget    " , fontSize = 20.sp , fontWeight = FontWeight.Bold , color=Color.Black)
        //   Text(text = "Expense    " , fontSize = 20.sp , fontWeight = FontWeight.Bold , color=Color.Black)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {

       HomeCircularIndicator(budget =total, expense = expense)


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
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.travel),
                            contentDescription = "travel icon",
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.fillMaxWidth(0.9f))
                        Text(
                            text = "0%",
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
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.restaurant),
                            contentDescription = "restaurant icon",
                            tint = Darkblue
                        )
                        Spacer(modifier = Modifier.fillMaxWidth(0.9f))
                        Text(
                            text = "0%",
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
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.shopping),
                            contentDescription = "shopping icon",
                            tint = orange
                        )
                        Spacer(modifier = Modifier.fillMaxWidth(0.9f))
                        Text(
                            text = "0%",
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
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.key),
                            contentDescription = "rent icon",
                            tint = darkViolet
                        )
                        Spacer(modifier = Modifier.fillMaxWidth(0.9f))
                        Text(
                            text = "0%",
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





