package com.example.expensetrackerproject.Categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.mediumGray
import com.example.expensetrackerproject.ui.theme.mint


@Composable
fun LandingPage(navController: NavController){
    val interactionSource = remember { MutableInteractionSource() }
    Column(modifier= Modifier
        .fillMaxSize()
        .padding(20.dp) , horizontalAlignment =Alignment.CenterHorizontally , verticalArrangement =Arrangement.spacedBy(20.dp) ){
Spacer(modifier=Modifier.height(5.dp))
        Image(painter = painterResource(id = R.drawable.landingpageicon),
            contentDescription ="landingPage icon" ,
            modifier=Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop

            ,
        )
      Column(modifier=Modifier.fillMaxWidth() , verticalArrangement = Arrangement.Center , horizontalAlignment =Alignment.CenterHorizontally) {
          Text(
              text = "Manage your ",
              fontSize = 30.sp,
              fontWeight = FontWeight.Bold,

          )
          Text(
              text = " Expenses Easily",
              fontSize = 30.sp,
              fontWeight = FontWeight.Bold,

          )
      }
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(color = mediumGray , fontWeight = FontWeight.Bold , fontSize = 14.sp)){
                append("You Track")
            }
            withStyle(style = SpanStyle(color= mediumGray,fontWeight= FontWeight.Normal, fontSize =14.sp)){
                append(" allows you to record your expenses and categorize them so you can know where you are spending, what you are spending on, your total expenses for each category and the remaining part of your salary for the month.")
            }
        }, modifier = Modifier.fillMaxWidth() , textAlign = TextAlign.Start, lineHeight = 18.sp)

       Spacer(modifier=Modifier.height(10.dp))
        Button(onClick = { navController.navigate(route="SignUpScreen"){
            popUpTo(route = "LandingPage")
        } } ,
            modifier= Modifier
                .fillMaxWidth(0.8f)
                .clip(
                    RoundedCornerShape(5.dp)

                ) ,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ), contentPadding = PaddingValues()
        ) {
            Box(modifier=Modifier.fillMaxWidth()
                .background(color=mint , shape = RoundedCornerShape(5.dp))
                .clip(shape= RoundedCornerShape(5.dp)).padding(20.dp)
                , contentAlignment = Alignment.Center){
                Text(text ="Get Started", fontSize=16.sp , fontWeight = FontWeight.Bold,color=White)
            }



        }



    }

}