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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
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
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


@SuppressLint("MutableCollectionMutableState")
@Composable
fun Home(navController: NavController , userUi:String) {
    var user: MutableMap<String, Any> by remember {
        mutableStateOf(
            hashMapOf(
                "firstName" to "" ,
                "lastName" to "",
                "budget" to 0f,
                "expenses" to 0f,
                "travel" to 0f,
                "food" to 0f,
                "shopping" to 0f,
                "rent" to 0f

            )
        )
    }

    var firstName :String by remember{
        mutableStateOf(user["firstName"].toString())
    }
    var lastName :String by remember{
        mutableStateOf(user["lastName"].toString())
    }
    var budget :Float  by remember {
        mutableStateOf(user["budget"].toString().toFloat())
    }
    var expenses :Float by remember {
        mutableStateOf(user["expenses"].toString().toFloat())
        }
    var travel:Float by remember {
        mutableStateOf(user["travel"].toString().toFloat())
    }
    var food :Float by remember{
        mutableStateOf(user["food"].toString().toFloat())
    }
    var shopping :Float by remember{
        mutableStateOf(user["shopping"].toString().toFloat())
    }
    var rent :Float by remember{
        mutableStateOf(user["rent"].toString().toFloat())
    }

    val db = Firebase.firestore

    Log.d("userUIMain", userUi.toString())
    val docRef = db.collection("Users").document(userUi)
    docRef.get()
        .addOnSuccessListener { document ->
            if (document != null) {
             user = document.data as MutableMap<String, Any>
          Log.d("userdoc" , user.toString())

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


    Log.d("user" ,user.toString() )
    firstName=user["firstName"].toString()
    Log.d("FirstName" ,firstName.toString() )
    lastName=user["lastName"].toString()
    Log.d("lastName" , lastName.toString())
   budget = user["budget"].toString().toFloat()
    Log.d("budget" , user["budget"].toString())
    expenses=user["expenses"].toString().toFloat()
    Log.d("expenses", expenses.toString())
    travel=user["travel"].toString().toFloat()
    Log.d("Travel" , travel.toString())
    food =user["food"].toString().toFloat()
    Log.d("food" , food.toString())
   shopping =user["shopping"].toString().toFloat()
    Log.d("shopping" , shopping.toString())
   rent=user["rent"].toString().toFloat()
    Log.d("rent" , travel.toString())



    CreateHome(navController=navController, userUi = userUi ,firstName = firstName, lastName =lastName , budget =budget  , expenses =expenses , travel =travel , food=food , shopping=shopping , rent = rent)
}


@SuppressLint("DefaultLocale")
@Composable
fun CreateHome(navController: NavController,userUi:String,firstName:String, lastName:String ,budget:Float , expenses:Float , travel:Float , food:Float  ,shopping:Float , rent:Float ) {
    val travelPercentage = animateFloatAsState(
        targetValue = if (travel == 0f) 0f else (travel.toFloat() / expenses.toFloat()),
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
    val travelIndicatorColor =
        animateColorAsState(targetValue = if (travelPercentage.value == 0f) Color.Transparent else Red)
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

    val rentIndicatorColor =
        animateColorAsState(targetValue = if (rentPercentage.value == 0f) Color.Transparent else violet)

    val rentIndicatorWidth = if (expenses != 0f) ((rent * 290) / expenses).dp else 0.dp
    val interactionSource = remember { MutableInteractionSource() }

    val expenseInfo: List<ExpenseInfo> = listOf(
        ExpenseInfo(
            name = "travel",
            percentage = travelPercentage.value,
            indicatorForegroundColor = travelIndicatorColor.value,
            color = Color.Red,
            indicatorForegroundWidth = travelIndicatorWidth.value
        ),
        ExpenseInfo(
            name = "food",
            percentage = foodPercentage.value,
            color = Darkblue,
            indicatorForegroundColor = foodIndicatorColor.value,
            indicatorForegroundWidth = foodIndicatorWidth.value
        ),
        ExpenseInfo(
            name = "shopping",
            percentage = shoppingPercentage.value,
            color = orange,
            indicatorForegroundWidth = foodIndicatorWidth.value,
            indicatorForegroundColor = shoppingIndicatorColor.value
        ),
        ExpenseInfo(
            name = "rent",
            percentage = rentPercentage.value,
            color = violet,
            indicatorForegroundWidth = rentIndicatorWidth.value,
            indicatorForegroundColor = rentIndicatorColor.value
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "Home  ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Text(
            text = "Hello ${firstName.capitalize(Locale.ROOT)}",
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = Color.LightGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )



        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {

                HomeCircularIndicator(budget = budget, expenses = expenses)

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

            items(items = expenseInfo) { expense ->
                Row(
                    modifier = Modifier.fillMaxWidth(9f).height(50.dp).drawBehind {
                        drawBackgroundIndicator(
                            componentSize = size,
                            indicatorBackgroundColor = LightGray.copy(0.3f),
                            indicatorBackgroundStrokeWidth = size.height
                        )
                        drawForegroundIndicator(
                            componentSize = size,
                            indicatorForegroundColor = expense.indicatorForegroundColor,
                            indicatorForegroundStrokeWidth = size.height,
                            percentage = expense.percentage
                        )

                    }.clickable(interactionSource=interactionSource, indication = null ) {
                        navController.navigate(route = "DisplayExpenses/${expense.name}/$userUi")
                    },
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(30.dp)
                            .width(3.dp)
                            .background(color = expense.color)
                    )

                    Text(
                        text = expense.name.capitalize(Locale.ROOT),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.width(90.dp)
                    )

                    Text(
                        text = "${String.format("%.1f", (expense.percentage * 100))} %",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = if(expense.percentage==0f)Color.Black else Color.White
                    )


                }
            }
            item {
                Spacer(modifier = Modifier.height(60.dp))

            }



        }


    }

}
fun DrawScope.drawBackgroundIndicator(componentSize: androidx.compose.ui.geometry.Size, indicatorBackgroundColor:Color, indicatorBackgroundStrokeWidth:Float){
    drawLine(
        color=indicatorBackgroundColor ,
        start = Offset(x=260f, y=45f),
        end= Offset(x=componentSize.width/1.1f,y=45f),
        strokeWidth = indicatorBackgroundStrokeWidth,
        cap = StrokeCap.Round,
    blendMode=BlendMode.Luminosity)
}
fun DrawScope.drawForegroundIndicator(componentSize: androidx.compose.ui.geometry.Size, indicatorForegroundColor:Color, indicatorForegroundStrokeWidth:Float, percentage:Float){
    drawLine(
        color=indicatorForegroundColor ,
        start = Offset(x=260f,y=45f),
        end= Offset(x=(percentage*(componentSize.width/1.1f-260f))+260f,y=45f),
        strokeWidth = indicatorForegroundStrokeWidth,
        cap = StrokeCap.Round)
}

