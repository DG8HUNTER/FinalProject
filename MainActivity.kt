package com.example.expensetrackerproject

import Navigation
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerproject.ui.theme.ExpenseTrackerProjectTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    val auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ExpenseTrackerProjectTheme {
                val navController:NavController= rememberNavController()
                val currentUser = auth.currentUser
                if(currentUser != null){
           Log.d("userStatus" , "user  exist ${currentUser.uid}")
                    Navigation(navController = navController)
                    navController.navigate(route="MainPage/${currentUser.uid}")

        }else{
                    Log.d("userStatus" , "user doesn't exist")
                    Navigation(navController = navController)

                }


            }
        }
    }

    }






