package com.example.expensetrackerproject.Home

import android.annotation.SuppressLint
import android.icu.util.ULocale
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerproject.BottomNavigationItemInfo
import com.example.expensetrackerproject.Categories.Categorie
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.light_surfaceVariant
import com.example.expensetrackerproject.ui.theme.mint
import com.example.expensetrackerproject.ui.theme.onLightSurfaceVariant
import com.example.expensetrackerproject.ui.theme.red
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


@SuppressLint("SuspiciousIndentation")
@Composable

fun ExpenseCard(expense:ExpenseInfo,navController: NavController ){
  val expensePer = animateFloatAsState(targetValue = if(expense.percentage==0f) 0f else expense.percentage)
val userUi=Firebase.auth.currentUser?.uid.toString()
            Card(
                modifier = Modifier
                    .size(180.dp)
                    .clip(shape = RoundedCornerShape(10.dp)), backgroundColor = light_surfaceVariant
            ) {
                Column(modifier= Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(15.dp)

                ) {
                    Icon(
                        painter = painterResource(id = expense.icon),
                        contentDescription = "$expense.name",
                        modifier = Modifier.size(24.dp),
                        tint = onLightSurfaceVariant

                    )
                    Row(modifier=Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = expense.name.capitalize(Locale.ROOT),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = onLightSurfaceVariant
                        )
                        Text(text ="${String.format("%.1f", (expensePer.value*100))} %" ,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = onLightSurfaceVariant

                        )
                    }
                    LinearProgressIndicator(progress =expensePer.value,
                        color=mint,
                        backgroundColor = Color.White,
                        modifier= Modifier
                            .clip(shape = RoundedCornerShape(10.dp))
                            .height(20.dp)

                    )

                    Row(modifier=Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.Start){
                        Text(
                            text = "View Expenses",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = onLightSurfaceVariant,
                            style = TextStyle(
                                textDecoration = TextDecoration.combine(
                                    listOf(
                                        TextDecoration.Underline,

                                    )

                        )

                            ),
                            modifier = Modifier.clickable {
                                navController.navigate(route="DisplayExpenses/${expense.name}"){
                                    popUpTo(0)
                                    popUpTo(route="MainPage/$userUi")
                                }
                            }
                        )
                    }



4


                }
            }
        }

@Preview(showBackground = true)
@Composable
fun PreviewExpenseCard(){
    ExpenseCard(expense =     ExpenseInfo(
        name = "travel",
        percentage = 0.8f,
        icon= R.drawable.travel
    ), navController = rememberNavController())

}