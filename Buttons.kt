package com.example.expensetrackerproject.Categories

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerproject.addTo
import com.example.expensetrackerproject.ui.theme.pink
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@Composable

fun Buttons(db:FirebaseFirestore,userUi:String,category:String,color:Color){
    Spacer(modifier = Modifier.height(20.dp))
    Row(modifier= Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.SpaceAround){
        Button(onClick = {

            if(category=="Travel"){
            if(mainActivityViewModel.country.value!=null && mainActivityViewModel.price.value!=null && mainActivityViewModel.day.value!=null && mainActivityViewModel.month.value!=null && mainActivityViewModel.year.value!=null){
                val data = hashMapOf(
                    "userUID" to userUi,
                    "category" to "Travel" ,
                    "country" to mainActivityViewModel.country.value,
                    "location" to mainActivityViewModel.location.value,
                    "price" to mainActivityViewModel.price.value,
                    "date"  to "${mainActivityViewModel.day.value}/${mainActivityViewModel.month.value}/${mainActivityViewModel.year.value}",
                    "tempStamp" to SimpleDateFormat("dd-MM-yyyy").parse("${mainActivityViewModel.day.value!!}-${mainActivityViewModel.month.value!!}-${mainActivityViewModel.year.value!!}"))

                db.collection("expenses")
                    .add(data)
                    .addOnSuccessListener { documentReference ->
                        Log.d("expenses", "DocumentSnapshot written with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w("expenses", "Error adding document", e)
                    }

            }
            }else{
                val  data =    hashMapOf("userUID" to userUi,
                "category" to category,
                "name" to mainActivityViewModel.name.value,
                    "location" to mainActivityViewModel.location.value,
                    "quantity" to mainActivityViewModel.quantity.value,
                "price" to mainActivityViewModel.price.value,
                "date"  to "${mainActivityViewModel.day.value}/${mainActivityViewModel.month.value}/${mainActivityViewModel.year.value}",
                "tempStamp" to SimpleDateFormat("dd-MM-yyyy").parse("${mainActivityViewModel.day.value!!}-${mainActivityViewModel.month.value!!}-${mainActivityViewModel.year.value!!}"))

                db.collection("expenses")
                    .add(data)
                    .addOnSuccessListener { documentReference ->
                        Log.d("expenses", "DocumentSnapshot written with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w("expenses", "Error adding document", e)
                    }

            }
                db.collection("expenses")
                    .whereEqualTo("category" ,category)
                    .whereEqualTo("userUID" , userUi)
                    .get()
                    .addOnSuccessListener { documents ->
                        mainActivityViewModel.setValue(
                            mutableListOf<HashMap<String,Any>>(),"expense")
                        Log.d("documents" , documents.toString())
                        for (document in documents) {
                            Log.d("user", "${document.id} => ${document.data}")
                            Log.d("data" , document.data.toString())
                            mainActivityViewModel.AddTo_expense(document.data as HashMap<String,Any>)
                        }

                        if(mainActivityViewModel.expense.value.size>0){
                            val docRef = db.collection("Users").document(userUi)
                            docRef.get()
                                .addOnSuccessListener { document ->
                                    if (document != null) {
                                        Log.d("TAG", "DocumentSnapshot data: ${document.data}")

                                        docRef
                                            .update("expenses", FieldValue.increment(-(document.data?.get(category.lowercase()).toString().toLong())))
                                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                                    } else {
                                        Log.d("TAG", "No such document")
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.d("TAG", "get failed with ", exception)
                                }
                            docRef
                                .update(category.lowercase(),0)
                                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                                .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }

                           mainActivityViewModel.expense.value.forEachIndexed { index,  expense ->

                                docRef
                                    .update(category.lowercase(), FieldValue.increment(expense["price"].toString().toFloat().toLong()))
                                    .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                                    .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }

                                if(index== mainActivityViewModel.expense.value.size-1)

                                    docRef.get()
                                        .addOnSuccessListener { document ->
                                            if (document != null) {
                                                Log.d("TAG", "DocumentSnapshot data: ${document.data}")

                                                docRef
                                                    .update("expenses", FieldValue.increment(document.data?.get(category.lowercase()).toString().toLong()))
                                                    .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                                            } else {
                                                Log.d("TAG", "No such document")
                                            }
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.d("TAG", "get failed with ", exception)
                                        }
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w("user", "Error getting documents: ", exception)
                    }

            mainActivityViewModel.setValue(null , "country")
            mainActivityViewModel.setValue(null,"name")
            mainActivityViewModel.setValue(null,"location")
            mainActivityViewModel.setValue(null , "quantity")
            mainActivityViewModel.setValue(null , "price")
            mainActivityViewModel.setValue(null , "day")
            mainActivityViewModel.setValue(null , "month")
            mainActivityViewModel.setValue(null , "year")
        } , colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ) , contentPadding = PaddingValues(), modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)) ) {
            Box(modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp))
                .width(150.dp)
                .background(color = color)
                .height(50.dp) , contentAlignment = Alignment.Center){
                Text(text = "Add" , fontSize =18.sp , fontWeight = FontWeight.Bold , color= Color.White )
            }
        }
        Button(onClick = {
            mainActivityViewModel.setValue(null , "country")
            mainActivityViewModel.setValue(null,"name")
            mainActivityViewModel.setValue(null,"location")
            mainActivityViewModel.setValue(null , "quantity")
            mainActivityViewModel.setValue(null , "price")
            mainActivityViewModel.setValue(null , "day")
            mainActivityViewModel.setValue(null , "month")
            mainActivityViewModel.setValue(null , "year")} , colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        ) , contentPadding = PaddingValues(), modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)) ) {
            Box(modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp))
                .width(150.dp)
                .background(color = Color.White)
                .border(width = 2.dp, shape = RoundedCornerShape(20.dp), color = Color.Red)
                .height(50.dp) , contentAlignment = Alignment.Center){
                Text(text = "Reset" , fontSize =18.sp , fontWeight = FontWeight.Bold , color= Color.Red )
            }
        }
    }
}