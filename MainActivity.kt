package com.example.expensetrackerproject

import Navigation
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerproject.Categories.mainActivityViewModel
import com.example.expensetrackerproject.ui.theme.ExpenseTrackerProjectTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate


class MainActivity : ComponentActivity() {
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ExpenseTrackerProjectTheme {
                val currentUser = auth.currentUser
                mainActivityViewModel.setValue(LocalDate.now().dayOfMonth,"_dayOfMonth")
                mainActivityViewModel.setValue(LocalDate.now().monthValue,"_monthOfYear")
                mainActivityViewModel.setValue(LocalDate.now().year,"_yearNum")
                if(mainActivityViewModel.dayOfMonth.value==1){
                    val db =Firebase.firestore
                    val userInfoRef = db.collection("UsersInfo").document(currentUser!!.uid)
                    userInfoRef.update("expenses",0)
                    userInfoRef.update("travel",0)
                    userInfoRef.update("food",0)
                    userInfoRef.update("shopping",0)
                    userInfoRef.update("rent",0)
                    userInfoRef.update("currency","LBP")
                }
                val navController:NavController= rememberNavController()

                Navigation(navController = navController)
                if(currentUser != null){
                    mainActivityViewModel.setValue("Home","_selectedButton")
           Log.d("userStatus" , "user  exist ${currentUser.uid}")
                    navController.navigate(route="MainPage/${currentUser.uid}") {
                        popUpTo(0)
                    }
        }else{
                    Log.d("userStatus" , "user doesn't exist")

                }



            }
        }
    }

    }






