package com.example.expensetrackerproject.Categories

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.addTo
import com.example.expensetrackerproject.ui.theme.darkYellow
import com.example.expensetrackerproject.ui.theme.lightViolet
import com.example.expensetrackerproject.ui.theme.violet
import com.example.expensetrackerproject.ui.theme.yellow
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate


@Composable


fun RentElements(userUi:String){
 val db =Firebase.firestore
    val focusManager = LocalFocusManager.current

    var rentExpenses:MutableList<HashMap<String,Any>> by remember{
        mutableStateOf(
            mutableListOf(
                hashMapOf()
            )
        )
    }




    var name :String?by remember{
        mutableStateOf(null)
    }
    var price:Float? by remember{
        mutableStateOf(null)
    }
    val nameClearIcon = animateColorAsState(targetValue = if(name!=null) Color.Gray else Color.Transparent ,
        animationSpec = tween(durationMillis = 1000 , easing = FastOutSlowInEasing)
    )
    val priceClearIcon = animateColorAsState(targetValue = if(price!=null) Color.Gray else Color.Transparent ,
        animationSpec = tween(durationMillis = 1000 , easing = FastOutSlowInEasing)
    )
    var day:Int? by remember {
        mutableStateOf(null)

    }
    var month:Int?by remember{
        mutableStateOf(null)
    }
    var year: Int? by remember {
        mutableStateOf(null)

    }


    LazyColumn(modifier= Modifier
        .fillMaxSize()
        , verticalArrangement = Arrangement.spacedBy(15.dp) , horizontalAlignment = Alignment.Start) {

        item {
            Card(modifier = Modifier.fillMaxWidth().clip(shape=RoundedCornerShape(5.dp)), elevation = 90.dp ) {


                OutlinedTextField(
                    value = if (name != null) name.toString() else "",
                    onValueChange = {
                        name = if (it.isNotEmpty()) it else null
                    },
                    label = {
                        Text(text = "Name", fontSize = 18.sp, fontWeight = FontWeight.Medium)

                    },
                    placeholder = {
                        Text(
                            text = "Enter Name",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.LightGray
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.key),
                            contentDescription = "key icon",
                            tint = Color.LightGray
                        )

                    },
                    trailingIcon = {
                        IconButton(onClick = { name = null }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear Icon",
                                tint = nameClearIcon.value
                            )

                        }

                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp)),

                    colors = TextFieldDefaults.textFieldColors(

                        backgroundColor = Color.LightGray.copy(0.08f),
                        unfocusedLabelColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = violet,
                        cursorColor = Color.LightGray,
                        focusedLabelColor = violet
                    )
                )

            }


        }
        item{

                OutlinedTextField(
                    value = if (price != null) price.toString() else "",
                    onValueChange = {
                        price = if (it.isNotEmpty()) it.toFloat() else null
                    },
                    label = {
                        Text(text = "Price", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    },
                    placeholder = {
                        Text(
                            text = "Enter Price",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.LightGray
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dollar),
                            contentDescription = "dollar icon",
                            tint = Color.LightGray
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { price = null }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear icon",
                                tint = priceClearIcon.value
                            )

                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.LightGray.copy(0.08f),
                        unfocusedLabelColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = violet,
                        cursorColor = Color.LightGray,
                        focusedLabelColor = violet
                    )

                )
            }

        item {

                Row(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(5.dp))
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "calendar icon",
                        tint = Color.LightGray
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Date",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.LightGray
                    )

                    TextField(value = if (day != null) day.toString() else "", onValueChange = {
                        day = if (it.isNotEmpty()) it.toInt() else null
                    },
                        placeholder = {
                            Text(
                                text = "DD",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.LightGray
                            )

                        },
                        modifier = Modifier.width(60.dp).clip(shape= RoundedCornerShape(5.dp)),
                        colors = TextFieldDefaults.textFieldColors(

                            backgroundColor = Color.LightGray.copy(0.08f),
                            unfocusedLabelColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = violet,
                            cursorColor = Color.LightGray,
                            focusedLabelColor = violet
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions (
                            onDone={
                                focusManager.clearFocus()
                            }
                        )
                    )
                    TextField(value = if (month != null) month.toString() else "", onValueChange = {
                        month = if (it.isNotEmpty()) it.toInt() else null
                    },
                        placeholder = {
                            Text(
                                text = "MM",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.LightGray
                            )

                        },
                        modifier = Modifier.width(70.dp).clip(shape= RoundedCornerShape(5.dp)),
                        colors = TextFieldDefaults.textFieldColors(

                            backgroundColor = Color.LightGray.copy(0.08f),
                            unfocusedLabelColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = violet,
                            cursorColor = Color.LightGray,
                            focusedLabelColor = violet
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions (
                            onDone={
                                focusManager.clearFocus()
                            }
                        )
                    )
                    TextField(value = if (year != null) year.toString() else "", onValueChange = {
                        year = if (it.isNotEmpty()) it.toInt() else null
                    },
                        placeholder = {
                            Text(
                                text = "YYYY",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.LightGray
                            )

                        },
                        modifier = Modifier.width(80.dp).clip(shape= RoundedCornerShape(5.dp)),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray.copy(0.08f),
                            unfocusedLabelColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = violet,
                            cursorColor = Color.LightGray,
                            focusedLabelColor = violet
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions (
                            onDone={
                                focusManager.clearFocus()
                            }
                        )
                    )

                    IconButton(onClick = {
                        day = LocalDate.now().dayOfMonth
                        month = LocalDate.now().monthValue
                        year = LocalDate.now().year

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_today),
                            contentDescription = "today icon",
                            tint = violet
                        )
                    }
                }
        }
        item{
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier= Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.SpaceAround){
                Button(onClick = {
                    if(name!=null && price!=null && day!=null && month!=null && year!=null){

                        val data = hashMapOf(
                            "id" to userUi,
                            "categorie" to "Rent" ,
                            "name" to name,
                            "price" to price,
                            "date"  to "$day/$month/$year",


                            )
                        db.collection("expenses")
                            .add(data)
                            .addOnSuccessListener { documentReference ->
                                Log.d("expenses", "DocumentSnapshot written with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w("expenses", "Error adding document", e)
                            }


                        db.collection("expenses")
                            .whereEqualTo("categorie" ,"Rent")
                            .whereEqualTo("id" , userUi)
                            .get()
                            .addOnSuccessListener { documents ->
                                rentExpenses= mutableListOf()
                                Log.d("documents" , documents.toString())
                                for (document in documents) {
                                    Log.d("user", "${document.id} => ${document.data}")
                                    Log.d("data" , document.data.toString())
                                    rentExpenses=addTo(rentExpenses , document.data as HashMap<String,Any>)
                                }
                                Log.d("travelExpenses" , rentExpenses.toString())
                                Log.d("Size" , rentExpenses.size.toString())
                                if(rentExpenses.size>0){
                                    val docRef = db.collection("Users").document(userUi)
                                    docRef.get()
                                        .addOnSuccessListener { document ->
                                            if (document != null) {
                                                Log.d("TAG", "DocumentSnapshot data: ${document.data}")

                                                docRef
                                                    .update("expenses", FieldValue.increment(-(document.data?.get("rent").toString().toLong())))
                                                    .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                                            } else {
                                                Log.d("TAG", "No such document")
                                            }
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.d("TAG", "get failed with ", exception)
                                        }
                                    docRef
                                        .update("rent", FieldValue.delete())
                                        .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                                        .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
                                    rentExpenses.forEachIndexed { index,  rentExpense ->


                                        docRef
                                            .update("rent", FieldValue.increment(rentExpense["price"].toString().toFloat().toLong()))
                                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                                            .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }

                                        if(index==rentExpenses.size-1)

                                            docRef.get()
                                                .addOnSuccessListener { document ->
                                                    if (document != null) {
                                                        Log.d("TAG", "DocumentSnapshot data: ${document.data}")

                                                        docRef
                                                            .update("expenses", FieldValue.increment(document.data?.get("rent").toString().toLong()))
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




                    }

                    name=null
                    price=null
                    day=null
                    month=null
                    day=null
                    year=null
                } , colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ) , contentPadding = PaddingValues(), modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)) ) {
                    Box(modifier = Modifier
                        .clip(shape = RoundedCornerShape(15.dp))
                        .width(150.dp)
                        .background(color = violet)
                        .height(50.dp) , contentAlignment = Alignment.Center){
                        Text(text = "Save" , fontSize =18.sp , fontWeight = FontWeight.Bold , color= Color.White )


                    }

                }
                Button(onClick = {




                    name=null
                    price=null
                    day=null
                    month=null
                    day=null
                    year=null} , colors = ButtonDefaults.buttonColors(
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

    }


}