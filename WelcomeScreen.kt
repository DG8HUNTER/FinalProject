package com.example.expensetrackerproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerproject.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.temporal.TemporalAdjusters.next


@Composable
fun WelcomeScreen(navController: NavController){

    Column(modifier= Modifier
        .fillMaxSize()
        .padding(20.dp) , horizontalAlignment = Alignment.Start, verticalArrangement =Arrangement.Top){
Spacer(modifier = Modifier.height(40.dp))
        Text(text ="Welcome" , fontSize=30.sp , fontWeight = FontWeight.Bold , color= Color.Black , modifier = Modifier.fillMaxWidth() , textAlign = TextAlign.Start, fontFamily = FontFamily.Default)
        Spacer(modifier =Modifier.height(20.dp))
        Row(modifier=Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(id = R.drawable.expenses),
                contentDescription = "Wallet icon ",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .background(color = Color.Transparent)
            )
        }
        Spacer(modifier =Modifier.height(30.dp))
        Text(buildAnnotatedString {
           withStyle(style = SpanStyle(color = Color.Gray , fontWeight = FontWeight.Bold , fontSize = 14.sp)){
               append("You Track")
           }
           withStyle(style = SpanStyle(color=Color.Gray,fontWeight= FontWeight.Normal, fontSize = 14.sp)){
               append(" allows you to record your expenses and categorize them so you can know where you are spending, what you are spending on, your total expenses for each category and the remaining part of your salary for the month.")
           }
        }, modifier = Modifier.fillMaxWidth() , textAlign = TextAlign.Start, lineHeight = 18.sp)

        Spacer(modifier =Modifier.height(40.dp))
    Row(modifier=Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.End) {
        Button(onClick = { navController.navigate(route="SignUpScreen"){
            popUpTo("WelcomeScreen")
        } } , colors = ButtonDefaults.buttonColors(
         backgroundColor = Color.Transparent
        ), contentPadding = PaddingValues(0.dp), modifier = Modifier
            .width(120.dp)
            .clip(shape = RoundedCornerShape(8.dp))
           ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(color = com.example.expensetrackerproject.ui.theme.Green, shape = RoundedCornerShape(8.dp))
                    .padding(10.dp)
                    , contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Next",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.next),
                        contentDescription = "Next Arrow",
                        tint = Color.White,
                        modifier= Modifier
                            .padding(start = 10.dp, top = 4.dp)
                    )

                }

            }
        }

        }

    }





}
