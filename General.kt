package com.example.expensetrackerproject.Home.Pages

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerproject.Categories.mainActivityViewModel
import com.example.expensetrackerproject.Home.ExpenseViewHolder
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.*
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import me.saket.swipe.SwipeAction
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


@SuppressLint("MutableCollectionMutableState", "SimpleDateFormat", "SuspiciousIndentation")
@Composable
fun  General(userUi:String) {
    var firstName by remember {
        mutableStateOf("")
    }
    var income by remember {
        mutableStateOf(0f)
    }
    var currency by remember {
        mutableStateOf("")
    }
    var expense by remember {
        mutableStateOf(0f)
    }
    var todayTotal :Float by remember{
        mutableStateOf(0f)
    }

    if(mainActivityViewModel.todayExpense.value.size==0){
        todayTotal=0f
    }

    else{
        todayTotal=0f
        mainActivityViewModel.todayExpense.value.forEachIndexed { index, document ->
            todayTotal=todayTotal.plus(document["price"].toString().toFloat())
        }

    }

    val db = Firebase.firestore
    LaunchedEffect(key1 =true ) {
        db.collection("Users").document(userUi)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    firstName = document.data?.get("firstName") as String
                }
            }
    }

        val infoRef = db.collection("UsersInfo").document(userUi)
        infoRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    income = document.data?.get("income").toString().toFloat()
                    currency = document.data?.get("currency") as String
                    expense = document.data?.get("expenses").toString().toFloat()
                }
            }

    infoRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w("user", "Listen failed.", e)
            return@addSnapshotListener
        }

        if (snapshot != null && snapshot.exists()) {
            Log.d("user", "Current data: ${snapshot.data}")
            income = snapshot.data?.get("income").toString().toFloat()
            currency = snapshot.data?.get("currency") as String
            expense = snapshot.data?.get("expenses").toString().toFloat()
        }

    }
    val progress by animateFloatAsState(targetValue = if(expense==0f)0f else if(expense<=income)(expense/income) else 1f,
        animationSpec = tween(durationMillis =2000, easing = FastOutSlowInEasing ))

    val transition = rememberInfiniteTransition()
    val translateAnim= transition.animateFloat(initialValue = 0f, targetValue =1000f, animationSpec = infiniteRepeatable(animation= tween(4000, easing = FastOutSlowInEasing)) )


            val docRef=db.collection("expenses")
                .whereEqualTo("userUID",userUi)
                .whereEqualTo("tempStamp",SimpleDateFormat("dd-MM-yyyy").parse("${mainActivityViewModel.dayOfMonth.value!!}-${mainActivityViewModel.monthOfYear.value!!}-${mainActivityViewModel.yearNum.value!!}"))
                docRef.get()
                .addOnSuccessListener {  results->
                    mainActivityViewModel.setValue(mutableListOf<HashMap<String,Any>>(),"todayExpense")
                    for(document in results){
                        Log.d("result",document.data.toString())
                        if(document!=null){
                            mainActivityViewModel.AddTo_todayExpense(newValue = document.data as kotlin.collections.HashMap<String,Any>)

                        }
                    }
                }
    val expensesRef =db.collection("expenses")
    expensesRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w("user", "Listen failed.", e)
            return@addSnapshotListener
        }

        if (snapshot != null && snapshot.documents.isNotEmpty()) {
//            expenses = mutableListOf()

            Log.d("user", "Current data: ${snapshot.documents}")
           val ref= db.collection("expenses").whereEqualTo("userUID",userUi)
               .whereEqualTo("tempStamp",SimpleDateFormat("dd-MM-yyyy").parse("${mainActivityViewModel.dayOfMonth.value!!}-${mainActivityViewModel.monthOfYear.value!!}-${mainActivityViewModel.yearNum.value!!}"))
            ref.get()
                .addOnSuccessListener{
                    results->
                    mainActivityViewModel.setValue(mutableListOf<HashMap<String,Any>>(),"todayExpense")
                    for(document in results) {
                        if (document != null) {
                            mainActivityViewModel.AddTo_todayExpense(newValue = document.data as kotlin.collections.HashMap<String, Any>)

                        }
                    }

                }

        } else {
            Log.d("user", "Current data: null")
        }
    }

val brush =Brush.linearGradient(colors= listOf(Color(0xFF292C31), mediumGray), start = Offset.Zero, end = Offset(x=translateAnim.value, y = translateAnim.value) )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(15.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Hello ${firstName.capitalize(Locale.ROOT)}",
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = mediumGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            textAlign = TextAlign.Start
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(9f)
                .background(
                    brush = brush, shape = RoundedCornerShape(20.dp)
                )
                .padding(10.dp)
                .height(200.dp)
                .clip(shape = RoundedCornerShape(20.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(5.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "$currency  $income",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
//                    modifier = Modifier.padding(top = 10.dp)
                    )

                    Text(
                        text = "Balance",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,

                        )
                }
//                Spacer(modifier=Modifier.height(5.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
//                        .drawBehind {
//                            drawRowBackground(
//                                componentSize = size,
//                                color = LightGray
//                            )
//                            drawRowForeground(
//                                componentSize = size,
//                                color = red,
//                                income = income,
//                                expense = expense
//                            )
//                        })
                ) {
                    LinearProgressIndicator(
                        progress = progress, modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .background(color = White, shape = RoundedCornerShape(20.dp))
                            .clip(shape = RoundedCornerShape(10.dp)),
                        color = red,
                        backgroundColor = Color.White
                    )

                }
                Spacer(modifier = Modifier.width(10.dp))


//Spacer(modifier=Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.6f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "****", fontSize = 17.sp, color = White)
                        Text(text = "****", fontSize = 17.sp, color = White)
                        Text(text = "402", fontSize = 17.sp, color = White)
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.creditcardcircles),
                        contentDescription = "CreditCardCircles",
                        modifier = Modifier.size(25.dp),
                        tint = Color.Unspecified
                    )
                }
            }


        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Today's expenses", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "$todayTotal $currency", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        if (mainActivityViewModel.todayExpense.value.size == 0) {
         Column(modifier= Modifier
             .fillMaxWidth()
             .fillMaxHeight(0.85f) , verticalArrangement = Arrangement.Bottom , horizontalAlignment = Alignment.CenterHorizontally){
             Row(Modifier.fillMaxWidth().fillMaxHeight(0.5f) , verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                 Text(
                     text = " No expenses for this day yet.",
                     fontSize = 17.sp,
                     fontWeight = FontWeight.Medium,
                     color = Color.Gray,
                 )
             }
             Row(Modifier.fillMaxWidth().fillMaxHeight(0.5f), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
                 IconButton(onClick = {
                     mainActivityViewModel.setValue(
                         "Category",
                         "_selectedButton"
                     )
                 },
                     modifier = Modifier.background(color = mint, shape = RoundedCornerShape(5.dp))
                         .clip(shape = RoundedCornerShape(8.dp))
                 ) {
                     Icon(
                         imageVector = Icons.Filled.Add,
                         contentDescription = "add icon",
                         tint = Color.White
                     )

                 }
             }
         }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(items = mainActivityViewModel.todayExpense.value) { expense ->
                    val category = expense["category"].toString().lowercase(Locale.ROOT)
                    val delete = SwipeAction(
                        onSwipe = {
                            if (expense["category"] == "Travel") {
                                Log.d("ex", expense.toString())
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
//                                  todayTotal= decrementTotal(todayTotal,expense["price"].toString().toFloat())
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w("TAG", "Error getting documents.", exception)
                                    }
                            } else {
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
                                                    category,
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
fun DrawScope.drawRowBackground(
        componentSize: androidx.compose.ui.geometry.Size,
        color: Color
    ) {
        drawLine(
            color = color,
            start = Offset(x = 5f, y = 30f),
            end = Offset(x = componentSize.width/1.05f, y = 30f),
            strokeWidth = componentSize.height,
            cap = StrokeCap.Round
        )
    }

    fun DrawScope.drawRowForeground(
        componentSize: androidx.compose.ui.geometry.Size,
        color: Color,
        income:Float,
        expense: Float
    ) {


//        drawLine(
//            color = color,
//            start = Offset(x =5f, y = 30f),
//            end = Offset(
//               x= if(expense!=0.0) ((componentSize.width/1.05f)*(expense/income).toFloat()) else 5f ,
//                y = 30f
//            ),
//            strokeWidth = componentSize.height,
//            cap = StrokeCap.Round
//        )
        drawLine(
            brush = Brush.linearGradient(colors = listOf(red, yellow) ),
            start = Offset(x = 5f, y = 30f),
            end =  Offset(x= ((componentSize.width / 1.05f) * (expense / income).toFloat()),y=30f),
            strokeWidth = componentSize.height,
            cap = StrokeCap.Round,



        )
    }

//((expense / income) * (componentSize.width/1.05f)+5f).toFloat()

fun incrementTotal(todayTotal: Float, value: Float): Float {
    return todayTotal + value

}
fun decrementTotal(todayTotal: Float, value: Float): Float {
    return todayTotal - value

}


//launchedEffect is very important to avoid duplication when a recomposition occurs
