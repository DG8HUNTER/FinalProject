package com.example.expensetrackerproject

import Navigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.G
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerproject.Categories.Categories
import com.example.expensetrackerproject.Categories.Categories
import com.example.expensetrackerproject.ui.theme.ExpenseTrackerProjectTheme
import com.example.expensetrackerproject.ui.theme.lightGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerProjectTheme {
                // A surface container using the 'background' color from the theme
               val navController:NavController= rememberNavController()
                Navigation(navController = navController)

            }
        }
    }


}

