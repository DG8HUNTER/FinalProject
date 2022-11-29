package com.example.expensetrackerproject.Home
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerproject.Categories.mainActivityViewModel
import com.example.expensetrackerproject.Home.Pages.General
import com.example.expensetrackerproject.Home.Pages.Statistics
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.SliderComp
import com.example.expensetrackerproject.ui.theme.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


@SuppressLint("MutableCollectionMutableState")
@Composable
fun Home(navController: NavController , userUi:String,tabSelected:String) {
    var user: MutableMap<String, Any> by remember {
        mutableStateOf(
            hashMapOf(
                "userUID" to "",
                "firstName" to "",
                "lastName" to "",


                )
        )
    }
    var userInfo: MutableMap<String, Any> by remember {
        mutableStateOf(
            hashMapOf(
                "userUID" to "",
                "income" to 0f,
                "expenses" to 0f,
                "travel" to 0f,
                "food" to 0f,
                "shopping" to 0f,
                "rent" to 0f
            )
        )
    }

    var firstName: String by remember {
        mutableStateOf(user["firstName"].toString())
    }
    var lastName: String by remember {
        mutableStateOf(user["lastName"].toString())
    }
    var income: Float by remember {
        mutableStateOf(userInfo["income"].toString().toFloat())
    }
    var expenses: Float by remember {
        mutableStateOf(userInfo["expenses"].toString().toFloat())
    }
    var travel: Float by remember {
        mutableStateOf(userInfo["travel"].toString().toFloat())
    }
    var food: Float by remember {
        mutableStateOf(userInfo["food"].toString().toFloat())
    }
    var shopping: Float by remember {
        mutableStateOf(userInfo["shopping"].toString().toFloat())
    }
    var rent: Float by remember {
        mutableStateOf(userInfo["rent"].toString().toFloat())
    }

    val db = Firebase.firestore

    Log.d("userUIMain", userUi.toString())
    val docRef = db.collection("Users").document(userUi)
    docRef.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                user = document.data as MutableMap<String, Any>
                Log.d("userdoc", user.toString())

            } else {
                Log.d("User", "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d("User", "get failed with ", exception)
        }
    val userInfoRef = db.collection("UsersInfo").document(userUi)
    userInfoRef.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                userInfo = document.data as MutableMap<String, Any>
                Log.d("userdoc", user.toString())
            } else {
                Log.d("User", "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d("User", "get failed with ", exception)
        }

    docRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w("user", "Listen failed.", e)
            return@addSnapshotListener
        }

        if (snapshot != null && snapshot.exists()) {
            Log.d("user", "Current data: ${snapshot.data}")
            user = snapshot.data as MutableMap<String, Any>
        } else {
            Log.d("user", "Current data: null")
        }
    }

    userInfoRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w("user", "Listen failed.", e)
            return@addSnapshotListener
        }

        if (snapshot != null && snapshot.exists()) {
            Log.d("user", "Current data: ${snapshot.data}")
            userInfo = snapshot.data as MutableMap<String, Any>
        } else {
            Log.d("user", "Current data: null")
        }
    }


    Log.d("user", user.toString())
    firstName = user["firstName"].toString()
    Log.d("FirstName", firstName.toString())
    lastName = user["lastName"].toString()
    Log.d("lastName", lastName.toString())
    income = userInfo["income"].toString().toFloat()
    Log.d("budget", user["budget"].toString())
    expenses = userInfo["expenses"].toString().toFloat()
    Log.d("expenses", expenses.toString())
    travel = userInfo["travel"].toString().toFloat()
    Log.d("Travel", travel.toString())
    food = userInfo["food"].toString().toFloat()
    Log.d("food", food.toString())
    shopping = userInfo["shopping"].toString().toFloat()
    Log.d("shopping", shopping.toString())
    rent = userInfo["rent"].toString().toFloat()
    Log.d("rent", travel.toString())


    if (tabSelected == "Statistics") {
        Statistics(
            navController = navController,
            userUi = userUi,
            firstName = firstName,
            lastName = lastName,
            income = income,
            expenses = expenses,
            travel = travel,
            food = food,
            shopping = shopping,
            rent = rent
        )
    }
    else{
        General(userUi=userUi)
    }
}



