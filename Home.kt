package com.example.expensetrackerproject.Home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.*
@Preview(showBackground = true)
@Composable
fun Home(){
    Column(modifier= Modifier
        .fillMaxSize()
        .padding(15.dp), horizontalAlignment = Alignment.Start , verticalArrangement = Arrangement.spacedBy(5.dp) ){
        Text(text ="Welcome     " , fontSize=25.sp , fontWeight = FontWeight.Bold , color= Color.Black)
        // Text(text = "Budget    " , fontSize = 20.sp , fontWeight = FontWeight.Bold , color=Color.Black)
        //   Text(text = "Expense    " , fontSize = 20.sp , fontWeight = FontWeight.Bold , color=Color.Black)

        LazyColumn(modifier= Modifier.fillMaxSize() , horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(15.dp)){
            item{
                HomeCircularIndicator()
            }
            item{
                Text(text ="Categories" , fontSize=20.sp , fontWeight = FontWeight.Bold ,color= Color.Black)
            }
            item{
                Surface( modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(shape = RoundedCornerShape(25.dp)), color = pink){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp) , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.Start){

                        Icon(painter = painterResource(id = R.drawable.travel), contentDescription = "travel icon" , tint = Color.Red)
                        Spacer(modifier =Modifier.fillMaxWidth(0.9f))
                        Text(text = "0%" , fontSize = 20.sp , fontWeight = FontWeight.Medium ,color= Red )
                    }
                }
            }
            item{
                Surface( modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(shape = RoundedCornerShape(25.dp)), color = lightBlue){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp) , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.Start){

                        Icon(painter = painterResource(id = R.drawable.restaurant), contentDescription = "restaurant icon" , tint = Darkblue)
                        Spacer(modifier =Modifier.fillMaxWidth(0.9f))
                        Text(text = "0%" , fontSize = 20.sp , fontWeight = FontWeight.Medium ,color= Darkblue )

                    }
                }

            }

            item{
                Surface( modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(shape = RoundedCornerShape(25.dp)), color = lightOrange){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp) , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.Start){

                        Icon(painter = painterResource(id = R.drawable.shopping), contentDescription = "shopping icon" , tint = orange)
                        Spacer(modifier =Modifier.fillMaxWidth(0.9f))
                        Text(text = "0%" , fontSize = 20.sp , fontWeight = FontWeight.Medium , color= orange )

                    }
                }


            }

            item{
                Surface( modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(shape = RoundedCornerShape(25.dp)), color = lightViolet){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp) , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.Start){

                        Icon(painter = painterResource(id = R.drawable.key), contentDescription = "rent icon" , tint = darkViolet)
                        Spacer(modifier =Modifier.fillMaxWidth(0.9f))
                        Text(text = "0%" , fontSize = 20.sp , fontWeight = FontWeight.Medium , color=violet )

                    }
                }
                Spacer(modifier = Modifier.height(50.dp))


            }
        }



    }

}
