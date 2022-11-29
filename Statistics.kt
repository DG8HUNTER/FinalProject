package com.example.expensetrackerproject.Home.Pages

import android.annotation.SuppressLint
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
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerproject.Home.ExpenseCard
import com.example.expensetrackerproject.Home.ExpenseInfo
import com.example.expensetrackerproject.Home.HomeCircularIndicator
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.Darkblue
import com.example.expensetrackerproject.ui.theme.mediumGray
import com.example.expensetrackerproject.ui.theme.orange
import com.example.expensetrackerproject.ui.theme.violet
import java.util.*

@SuppressLint("DefaultLocale")
@Composable
fun Statistics(navController: NavController, userUi:String, firstName:String, lastName:String, income:Float, expenses:Float, travel:Float, food:Float, shopping:Float, rent:Float ) {
    val travelPercentage = animateFloatAsState(
        targetValue = if (travel == 0f) 0f else (travel.toFloat() / expenses.toFloat()),
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
    val travelIndicatorColor =
        animateColorAsState(targetValue = if (travelPercentage.value == 0f) Color.Transparent else Color.Red)
    val travelIndicatorWidth = if (expenses != 0f) ((travel * 290) / expenses).dp else 0.dp


    val foodPercentage = animateFloatAsState(
        targetValue = if (food == 0f) 0f else (food.toFloat() / expenses.toFloat()),
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val foodIndicatorColor =
        animateColorAsState(targetValue = if (foodPercentage.value == 0f) Color.Transparent else Darkblue)

    val foodIndicatorWidth = if (expenses != 0f) ((food * 290) / expenses).dp else 0.dp


    val shoppingPercentage = animateFloatAsState(
        targetValue = if (shopping == 0f) 0f else (shopping.toFloat() / expenses.toFloat()),
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val shoppingIndicatorColor =
        animateColorAsState(targetValue = if (shoppingPercentage.value == 0f) Color.Transparent else orange)

    val shoppingIndicatorWidth = if (expenses != 0f) ((shopping * 290) / expenses).dp else 0.dp

    val rentPercentage = animateFloatAsState(
        targetValue = if (rent == 0f) 0f else (rent.toFloat() / expenses.toFloat()),
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
    var state by remember { mutableStateOf(0) }
    val titles = listOf("General","Statistics")
    val rentIndicatorColor =
        animateColorAsState(targetValue = if (rentPercentage.value == 0f) Color.Transparent else violet)

    val rentIndicatorWidth = if (expenses != 0f) ((rent * 290) / expenses).dp else 0.dp
    val interactionSource = remember { MutableInteractionSource() }

    val expenseInfo: List<ExpenseInfo> = listOf(
        ExpenseInfo(
            name = "travel",
            percentage = travelPercentage.value,
            icon= R.drawable.travel
        ),
        ExpenseInfo(
            name = "food",
            percentage = foodPercentage.value,
           icon =R.drawable.food
        ),
        ExpenseInfo(
            name = "shopping",
            percentage = shoppingPercentage.value,
            icon=R.drawable.shopping
        ),
        ExpenseInfo(
            name = "rent",
            percentage = rentPercentage.value,
            icon=R.drawable.rent
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
//        Text(
//            text = "Hello ${firstName.capitalize(Locale.ROOT)}",
//            fontSize = 17.sp,
//            fontWeight = FontWeight.Medium,
//            color = Gray,
//            modifier = Modifier.fillMaxWidth(),
//            textAlign = TextAlign.Start
//        )
        Text(text="This Month",
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = mediumGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            textAlign = TextAlign.Start
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {

                HomeCircularIndicator(income=income, expenses = expenses)

            }
            item {
                Text(
                    text = "Categories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

            }

            item {
                Row(modifier=Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween ) {
                    expenseInfo.forEachIndexed { index, expense ->

                        if(index<=1){
                            ExpenseCard(expense = expense,navController=navController)

                        }

                    }

                }
            }
            item {
                Row(modifier=Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.SpaceBetween) {
                    expenseInfo.forEachIndexed { index, expense ->

                        if(index>1){
                            ExpenseCard(expense = expense,navController=navController)

                        }

                    }

                }
            }
            item{
                Spacer(modifier = Modifier.height(80.dp))

            }



//            items(items = expenseInfo) { expense ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth(9f)
//                        .height(50.dp)
//                        ,
//                    horizontalArrangement = Arrangement.spacedBy(20.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Spacer(
//                        modifier = Modifier
//                            .height(30.dp)
//                            .width(3.dp)
//                            .background(color = expense.color)
//                    )
//
//                    Text(
//                        text = expense.name.capitalize(Locale.ROOT),
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Medium,
//                        color = Color.Black,
//                        modifier = Modifier.width(90.dp)
//                    )
//                    Row(modifier=Modifier.fillMaxSize()
//                        .drawBehind {
//                            drawBackgroundIndicator(
//                                componentSize = size,
//                                indicatorBackgroundColor = Color.LightGray.copy(0.3f),
//                                indicatorBackgroundStrokeWidth = size.height
//                            )
//                            drawForegroundIndicator(
//                                componentSize = size,
//                                indicatorForegroundColor = expense.indicatorForegroundColor,
//                                indicatorForegroundStrokeWidth = size.height,
//                                percentage = expense.percentage
//                            )
//
//                        }
//                        .clickable(interactionSource = interactionSource, indication = null) {
//                            navController.navigate(route = "DisplayExpenses/${expense.name}/$userUi")
//                        },
//                        horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically
//                    ){
//                        Text(
//                            text = "${String.format("%.1f", (expense.percentage * 100))} %",
//                            fontSize = 15.sp,
//                            fontWeight = FontWeight.Medium,
//                            color = if(expense.percentage==0f) Color.Black else Color.White,
//                        modifier=Modifier.padding(8.dp)
//                        )
//                    }
//
//
//
//
//
//                }
            }




        }


    }


fun DrawScope.drawBackgroundIndicator(componentSize: androidx.compose.ui.geometry.Size, indicatorBackgroundColor: Color, indicatorBackgroundStrokeWidth:Float){
    drawLine(
        color=indicatorBackgroundColor ,
        start = Offset(x=50f, y=45f),
        end= Offset(x=componentSize.width/1.1f,y=45f),
        strokeWidth = indicatorBackgroundStrokeWidth,
        cap = StrokeCap.Round,
        blendMode= BlendMode.Luminosity)
}
fun DrawScope.drawForegroundIndicator(componentSize: androidx.compose.ui.geometry.Size, indicatorForegroundColor: Color, indicatorForegroundStrokeWidth:Float, percentage:Float){
    drawLine(
        color=indicatorForegroundColor ,
        start = Offset(x=50f,y=45f),
        end= Offset(x=(percentage*(componentSize.width/1.1f-50f))+50f,y=45f),
        strokeWidth = indicatorForegroundStrokeWidth,
        cap = StrokeCap.Round)
}
