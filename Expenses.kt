package com.example.expensetrackerproject.Expenses

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontSynthesis.Companion.All
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerproject.Categories.mainActivityViewModel
import com.example.expensetrackerproject.Home.ExpenseViewHolder
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.mediumGray
import com.example.expensetrackerproject.ui.theme.mint
import com.google.common.primitives.UnsignedBytes.toInt
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import me.saket.swipe.SwipeAction
import java.sql.Timestamp
import java.sql.Types.TIMESTAMP
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

@SuppressLint("SimpleDateFormat")
@Composable
fun Expenses(userUi: String) {
    LaunchedEffect(key1 = true) {
        mainActivityViewModel.setValue(mainActivityViewModel.monthOfYear.value!!, "_chosenMonth")
        mainActivityViewModel.setValue(mainActivityViewModel.yearNum.value!!, "_chosenYear")
    }
    Log.d("month ", mainActivityViewModel.chosenMonth.value.toString())
    Log.d("year", mainActivityViewModel.chosenYear.value.toString())

    val db = Firebase.firestore
    val focusManager = LocalFocusManager.current



    val categories = listOf("All", "Travel", "Food", "Shopping", "Rent")
    var category: String by remember {
        mutableStateOf("All")
    }
    var size: Int by remember {
        mutableStateOf(0)
    }

    if(mainActivityViewModel.expense.value.size==0){
        mainActivityViewModel.setValue(0f , "_total")

    }else{
        mainActivityViewModel.setValue(0f , "_total")
        mainActivityViewModel.expense.value.forEach { expense ->

            mainActivityViewModel.setValue((mainActivityViewModel.total.value).plus(expense["price"].toString().toFloat()), "_total")

        }


    }
    val total = animateFloatAsState(targetValue =if(mainActivityViewModel.total.value==0f)0f else mainActivityViewModel.total.value ,
        animationSpec = tween(durationMillis = 1000 , easing = FastOutSlowInEasing)
    )


    Log.d("chosenYear", mainActivityViewModel.chosenYear.value!!.toInt().toString())

    val currencyArrowClicked by remember {
        mutableStateOf(false)
    }
    val currencyArrowDirection = animateFloatAsState(
        targetValue = if (currencyArrowClicked) 180f else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )



    Log.d("expenseSize", mainActivityViewModel.expense.value.size.toString())


    var isExpanded: Boolean by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Expenses",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "Month  ${mainActivityViewModel.chosenMonth.value!!}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Slider(
                    modifier = Modifier.semantics { contentDescription = "Localized Description" },
                    value = mainActivityViewModel.chosenMonth.value!!.toFloat(),
                    onValueChange = {

                        mainActivityViewModel.setValue(it.toInt(), "_chosenMonth")
//                        mainActivityViewModel.setValue(if(monthSelected.toInt()<12) mainActivityViewModel.yearNum.value!! else mainActivityViewModel.yearNum.value!!+1,"_chosenYear")
                    },
                    valueRange = 1f..12f,
                    onValueChangeFinished = {
                        // launch some business logic update with the state you hold
                        // viewModel.updateSelectedSliderValue(sliderPosition)
                    },
                    steps = 10,

                    )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.62f),
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Year: ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    IconButton(
                        onClick = {

                            mainActivityViewModel.setValue(
                                mainActivityViewModel.chosenYear.value!! - 1,
                                "_chosenYear"
                            )
                            Log.d("Year", mainActivityViewModel.chosenYear.value.toString())
                        }, modifier = Modifier
                            .size(24.dp)
                            .background(color = mint, shape = CircleShape)
                            .clip(shape = CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = "minus icon",
                            tint = Color.White,
                            modifier = Modifier.size(15.dp)
                        )

                    }

                    OutlinedTextField(value = mainActivityViewModel.chosenYear.value!!.toString(),
                        onValueChange = {
                            if (it.isNotEmpty() && it.length <= 4) {
                                mainActivityViewModel.setValue(it.toInt(), "_chosenYear")
                            }
                        },
                        placeholder = {
                            Text(
                                text = "YYYY",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = mediumGray
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            unfocusedLabelColor = mediumGray,
                            unfocusedIndicatorColor = mediumGray,
                            focusedIndicatorColor = mint,
                            cursorColor = mediumGray,
                            focusedLabelColor = mint
                        ),
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
                            .fillMaxWidth(0.5f)
                            .height(55.dp)
                    )
                    IconButton(
                        onClick = {
                            mainActivityViewModel.setValue(
                                mainActivityViewModel.chosenYear.value!! + 1,
                                "_chosenYear"
                            )
                            Log.d("Year", mainActivityViewModel.chosenYear.value.toString())
                        }, modifier = Modifier
                            .size(24.dp)
                            .background(color = mint, shape = CircleShape)
                            .clip(shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "add icon",
                            tint = Color.White
                        )

                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = {
                            if (it.isNotEmpty()) {
                                category = it
                            }
                        },

                        trailingIcon = {
                            IconButton(onClick = {
                                isExpanded = true
                            }, modifier = Modifier.rotate(currencyArrowDirection.value)) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "down arrow",
                                    tint = mediumGray
                                )

                                if (isExpanded) {
                                    DropdownMenu(expanded = isExpanded, onDismissRequest = {

                                        isExpanded = false
                                    }) {
                                        categories.forEach { cat ->
                                            DropdownMenuItem(onClick = {
                                                category = cat
                                                isExpanded = false
                                            }) {
                                                Text(
                                                    text = cat,
                                                    fontSize = 15.sp,
                                                    color = mediumGray
                                                )

                                            }
                                        }


                                    }


                                }
                            }
                        },


                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            cursorColor = mediumGray,

                            ),
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
                            .fillMaxWidth(1f)
                            .height(55.dp),
                        maxLines = 1,
                        readOnly = true


                    )


                }


            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = "Total: ${String.format("%.1f", total.value)}" ,  fontSize = 18.sp, fontWeight = FontWeight.Bold )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(1.dp)
                    .background(mediumGray)
            )
        }


//    val docRef=db.collection("expenses")
//        .whereGreaterThanOrEqualTo("tempStamp", SimpleDateFormat("dd-MM-yyyy").parse("${1}-${monthSelected}-${year}"))
//            .whereLessThan("tempStamp", SimpleDateFormat("dd-MM-yyyy").parse("${1}-${nextMonth}-${nextOrCurrentYear}"))
//        docRef.get()
//        .addOnSuccessListener { results->
//            for(document in results){
//                if(document !=null){
//                    if(category=="All" && document.data["userUi"]==userUi){
//                        mainActivityViewModel.AddTo_expense(document.data as HashMap<String, Any>)
//                    }
//                    else{
//                        db.collection("expenses")
//                            .whereEqualTo("category" , category)
//                            .whereEqualTo("userUi" , userUi)
//                            .get()
//
//
//
//
//                    }
//                }
//            }
//
//        }
        val firstDate: Date =
            SimpleDateFormat("dd-MM-yyyy").parse("${1}-${mainActivityViewModel.chosenMonth.value!!}-${mainActivityViewModel.chosenYear.value!!}") as Date


        val lastDate: Date =
            SimpleDateFormat("dd-MM-yyyy").parse(
                "${1}-${
                    if (mainActivityViewModel.chosenMonth.value!! < 12) mainActivityViewModel.chosenMonth.value!! + 1 else 1
                }-${if (mainActivityViewModel.chosenMonth.value!! < 12) mainActivityViewModel.chosenYear.value!! else mainActivityViewModel.chosenYear.value!! + 1}"
            ) as Date

        Log.d("firstDate", firstDate.toString())
        Log.d("lastDate", lastDate.toString())


        Log.d("firstDate", firstDate.toString())
        Log.d("lastDate", lastDate.toString())


//        LaunchedEffect(key1 = true) {
//
//            docRef.whereGreaterThanOrEqualTo(
//                "tempStamp",
//                SimpleDateFormat("dd-MM-yyyy").parse("${1}-${mainActivityViewModel.chosenMonth.value!!}-${mainActivityViewModel.chosenYear.value!!}") as Date
//            )
//                .whereLessThan(
//                    "tempStamp", SimpleDateFormat("dd-MM-yyyy").parse(
//                        "${1}-" +
//                                "${if (mainActivityViewModel.chosenMonth.value!! < 12) mainActivityViewModel.chosenMonth.value!! + 1 else 1}-" +
//                                "${if (mainActivityViewModel.chosenMonth.value!! < 12) mainActivityViewModel.chosenYear.value!! else mainActivityViewModel.chosenYear.value!! + 1}"
//                    ) as Date
//                )
//                .get()
//                .addOnSuccessListener { documents ->
//
//                    if (category == "All") {
//
//                        for (document in documents) {
//                            if (document != null && document.data["userUID"] == userUi) {
//                                mainActivityViewModel.AddTo_expense(document.data as HashMap<String, Any>)
//
//                            }
//                        }
//
//                    } else {
//                        for (document in documents) {
//                            if (document != null && document.data["userUID"] == userUi && document.data["category"] == category) {
//                                mainActivityViewModel.AddTo_expense(document.data as HashMap<String, Any>)
//
//                            }
//                        }
//
//                    }
//                    Log.d("size1", mainActivityViewModel.expense.value.size.toString())
//                    Log.d("expenses", mainActivityViewModel.expense.value.toString())
//                    size = mainActivityViewModel.expense.value.size
//
//                }
//        }


            val docRef = db.collection("expenses").orderBy("tempStamp", Query.Direction.DESCENDING)
                .whereGreaterThanOrEqualTo(
                    "tempStamp",
                    SimpleDateFormat("dd-MM-yyyy").parse("${1}-${mainActivityViewModel.chosenMonth.value!!}-${mainActivityViewModel.chosenYear.value!!}") as Date
                )
                .whereLessThan(
                    "tempStamp", SimpleDateFormat("dd-MM-yyyy").parse(
                        "${1}-${
                            if (mainActivityViewModel.chosenMonth.value!! < 12) mainActivityViewModel.chosenMonth.value!! + 1 else 1
                        }-${if (mainActivityViewModel.chosenMonth.value!! < 12) mainActivityViewModel.chosenYear.value!! else mainActivityViewModel.chosenYear.value!! + 1}"
                    ) as Date
                )


            docRef.get()
                .addOnSuccessListener { documents ->
                    mainActivityViewModel.setValue(
                        mutableListOf<HashMap<String, Any>>(),
                        "expense"
                    )

                    if (category == "All") {

                        for (document in documents) {
                            if (document != null && document.data["userUID"] == userUi) {
                                mainActivityViewModel.AddTo_expense(document.data as HashMap<String, Any>)
                            }
                        }
                    } else {
                        for (document in documents) {
                            if (document != null && document.data["userUID"] == userUi && document.data["category"] == category) {
                                mainActivityViewModel.AddTo_expense(document.data as HashMap<String, Any>)
                            }
                        }
                    }
                    Log.d("size1", mainActivityViewModel.expense.value.size.toString())
                    Log.d("expenses", mainActivityViewModel.expense.value.toString())
                    size = mainActivityViewModel.expense.value.size

                }

            docRef.addSnapshotListener { snapshot, e->
                if (e != null) {
                    Log.w("user", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.documents.isNotEmpty()) {
                    mainActivityViewModel.setValue(
                        mutableListOf<HashMap<String, Any>>(),
                        "expense"
                    )
                    if (category == "All") {

                        for (document in snapshot) {
                            if (document != null && document.data["userUID"] == userUi) {
                                mainActivityViewModel.AddTo_expense(document.data as HashMap<String, Any>)
                            }
                        }
                    } else {
                        for (document in snapshot) {
                            if (document != null && document.data["userUID"] == userUi && document.data["category"] == category) {
                                mainActivityViewModel.AddTo_expense(document.data as HashMap<String, Any>)
                            }
                        }
                    }
                    Log.d("size1", mainActivityViewModel.expense.value.size.toString())
                    Log.d("expenses", mainActivityViewModel.expense.value.toString())
                    size = mainActivityViewModel.expense.value.size
                    Log.d("snapshot size" , size.toString())




                } else {
                    Log.d("user", "Current data: null")
                }


            }


        






        Log.d(" expenses from outside", mainActivityViewModel.expense.value.toString())
        Log.d("size from outside", mainActivityViewModel.expense.value.size.toString())
        if (size == 0) {
            Log.d(" expenses from inside", mainActivityViewModel.expense.value.toString())
            Log.d("size from inside", mainActivityViewModel.expense.value.size.toString())
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No  expenses found.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, color = Color.Black,

                    )
            }


        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                mainActivityViewModel.expense.value.forEachIndexed { index, expense ->
                    val timestamp = expense["tempStamp"] as com.google.firebase.Timestamp
                    val date = timestamp.toDate()
                    Log.d("date" , date.toString())
                    Log.d("firstDate" , firstDate.toString())
                    Log.d("lsatDate" , lastDate.toString())
                    val date1 = SimpleDateFormat("dd-MM-yyyy").parse("${1}-${mainActivityViewModel.monthOfYear.value!!}-${mainActivityViewModel.yearNum.value!!}") as Date
                    val date2 =SimpleDateFormat("dd-MM-yyyy").parse("${1}-${if(mainActivityViewModel.monthOfYear.value!!<12)mainActivityViewModel.monthOfYear.value!!+1 else 1 }-" +
                            "${if(mainActivityViewModel.monthOfYear.value!!<12)mainActivityViewModel.yearNum.value!! else mainActivityViewModel.yearNum.value!!+1}") as Date

                    val delete = SwipeAction(
                        onSwipe = {
                            if (expense["category"] == "Travel" &&
                                date >= date1  && date2 > date
                            ) {
                                Log.d("ex", expense.toString())
                                Log.d("condition" , "1")
                                db.collection("expenses")
                                    .whereEqualTo("category", expense["category"])
                                    .whereEqualTo("country", expense["country"])
                                    .whereEqualTo("userUID", expense["userUID"])
                                    .whereEqualTo("date", expense["date"])
                                    .whereEqualTo("price", expense["price"])
                                    .get()
                                    .addOnSuccessListener { result ->
                                        for (document in result) {
                                            Log.d("dd", "${document.id} => ${document.data}")
                                            db.collection("UsersInfo")
                                                .document(userUi)
                                                .update(
                                                    "travel",
                                                    FieldValue.increment(
                                                        -(expense["price"].toString().toFloat()
                                                            .toLong())
                                                    )
                                                )
                                            db.collection("UsersInfo")
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
                            } else if (expense["category"] == "Travel" &&
                               date <date1   || date > date2
                            ) {
                                Log.d("condition" , "2")
                                db.collection("expenses")
                                    .whereEqualTo("category", expense["category"])
                                    .whereEqualTo("country", expense["country"])
                                    .whereEqualTo("userUID", expense["userUID"])
                                    .whereEqualTo("date", expense["date"])
                                    .whereEqualTo("price", expense["price"])
                                    .get()
                                    .addOnSuccessListener { result ->
                                        for (document in result) {
                                            db.collection("expenses").document(document.id).delete()
                                            break

                                        }


                                    }
                            } else if (expense["category"] != "Travel" &&
                                date >= date1  && date2 > date
                            ) {
                                Log.d("condition" , "3")
                                db.collection("expenses")
                                    .whereEqualTo("category", expense["category"])
                                    .whereEqualTo("name", expense["name"])
                                    .whereEqualTo("userUID", expense["userUID"])
                                    .whereEqualTo("price", expense["price"])
                                    .whereEqualTo("date", expense["date"])
                                    .whereEqualTo("quantity", expense["quantity"])
                                    .get()
                                    .addOnSuccessListener { result ->
                                        for (document in result) {
                                            db.collection("UsersInfo").document(userUi)
                                                .update(
                                                    expense["category"].toString().lowercase(),
                                                    FieldValue.increment(
                                                        -(expense["price"]).toString().toFloat()
                                                            .toLong()
                                                    )
                                                )

                                            db.collection("UsersInfo")
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
                            } else {
                                Log.d("condition" , "4")
                                db.collection("expenses")
                                    .whereEqualTo("category", expense["category"])
                                    .whereEqualTo("name", expense["name"])
                                    .whereEqualTo("userUID", expense["userUID"])
                                    .whereEqualTo("price", expense["price"])
                                    .whereEqualTo("date", expense["date"])
                                    .whereEqualTo("quantity", expense["quantity"])
                                    .get()
                                    .addOnSuccessListener { result ->
                                        for (document in result) {
                                            db.collection("expenses").document(document.id).delete()
                                            break

                                        }
                                    }
                            }

                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.delete),
                                contentDescription = null,
                                tint = Black,
                                modifier = Modifier.padding(start = 15.dp)
                            )

                        },
                        background = Color.Red,
                    )

                    item {
                        ExpenseViewHolder(
                            category = expense["category"].toString().lowercase(),
                            expense = expense,
                            delete = delete
                        )

                    }
                }
            }

        }

    }


}








