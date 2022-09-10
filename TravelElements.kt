package com.example.expensetrackerproject.Categories

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.pink
import java.time.LocalDate

@Composable
fun TravelElement(){
    val focusManager = LocalFocusManager.current
    var country :String?by remember{
        mutableStateOf(null)
    }
    var price:Float? by remember{
        mutableStateOf(null)
    }
    val locationClearIcon = animateColorAsState(targetValue = if(country!=null) Color.Gray else Color.Transparent ,
        animationSpec = tween(durationMillis = 1000 , easing = FastOutSlowInEasing)
    )
    val priceClearIcon = animateColorAsState(targetValue = if(price!=null) Color.Gray else Color.Transparent ,
        animationSpec = tween(durationMillis = 1000 , easing = FastOutSlowInEasing)
    )
    var day:Int? by remember {
        mutableStateOf(null)

    }
    var month:Int?by remember{
        mutableStateOf(null)
    }
    var year: Int? by remember {
        mutableStateOf(null)

    }


    LazyColumn(modifier= Modifier
        .fillMaxSize()
         , verticalArrangement = Arrangement.spacedBy(15.dp) , horizontalAlignment = Alignment.Start) {

        item {
            OutlinedTextField(
                value = if (country != null) country.toString() else "",
                onValueChange = {
                    country = if (it.isNotEmpty()) it else null
                },
                label = {
                    Text(text = "Country", fontSize = 18.sp, fontWeight = FontWeight.Medium)

                },
                placeholder = {
                    Text(
                        text = "Enter country",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.LightGray
                    )
                } ,
                leadingIcon = {
                    Icon(painter = painterResource(id =R.drawable.ic_location), contentDescription ="location icon", tint = Color.LightGray )

                } ,
                trailingIcon = {
                    IconButton(onClick = { country=null }) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription ="Clear Icon", tint=locationClearIcon.value )

                    }

                } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ) ,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ) ,
                modifier= Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray.copy(0.08f),
                    unfocusedLabelColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.Transparent ,
                    focusedIndicatorColor = Color.Red,
                    cursorColor = Color.LightGray,
                    focusedLabelColor = Color.Red

                )

            )


        }
        item{
            OutlinedTextField(value =if(price!=null)  price.toString() else "", onValueChange = {
                price = if(it.isNotEmpty())  it.toFloat() else null
            } , label ={
                Text(text ="Price" , fontSize=18.sp , fontWeight = FontWeight.Medium)
            } ,
                placeholder ={
                    Text(text ="Enter Price" , fontSize = 18.sp , fontWeight = FontWeight.Medium , color= Color.LightGray)
                } ,
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.ic_dollar), contentDescription ="dollar icon" , tint = Color.LightGray)
                } ,
                trailingIcon = {
                    IconButton(onClick = { price=null }) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription ="Clear icon" , tint=priceClearIcon.value )

                    }
                }  ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ) ,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ) ,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp)) ,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray.copy(0.08f),
                    unfocusedLabelColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.Transparent ,
                    focusedIndicatorColor = Color.Red,
                    cursorColor = Color.LightGray,
                    focusedLabelColor = Color.Red

                )

            )
        }
        item {
            Row(modifier= Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(5.dp))
                .padding(10.dp), verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.spacedBy(5.dp)){
                Icon(painter = painterResource(id =R.drawable.ic_calendar), contentDescription ="calendar icon" , tint = Color.LightGray )
                Spacer(modifier = Modifier.width(7.dp))
                Text(text ="Date" , fontSize = 18.sp , fontWeight = FontWeight.Medium , color= Color.LightGray)

                TextField(value =if(day!=null)day.toString() else "", onValueChange ={
                    day = if(it.isNotEmpty())it.toInt() else null
                }  ,
                    placeholder = {
                        Text(text = "DD" , fontSize = 18.sp , fontWeight = FontWeight.Medium , color = Color.LightGray)

                    } ,
                    modifier = Modifier.width(60.dp).clip(shape= RoundedCornerShape(5.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.LightGray.copy(0.08f),
                        unfocusedLabelColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.Transparent ,
                        focusedIndicatorColor = Color.Red,
                        cursorColor = Color.LightGray,
                        focusedLabelColor = Color.Red
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions (
                        onDone={
                            focusManager.clearFocus()
                        }
                    )
                )
                TextField(value =if(month!=null)month.toString() else "", onValueChange ={
                    month = if(it.isNotEmpty())it.toInt() else null
                }  ,
                    placeholder = {
                        Text(text = "MM" , fontSize = 18.sp , fontWeight = FontWeight.Medium , color = Color.LightGray)

                    } ,
                    modifier = Modifier.width(70.dp).clip(shape= RoundedCornerShape(5.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.LightGray.copy(0.08f),
                        unfocusedLabelColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.Transparent ,
                        focusedIndicatorColor = Color.Red,
                        cursorColor = Color.LightGray,
                        focusedLabelColor = Color.Red
                    ), keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions (
                        onDone={
                            focusManager.clearFocus()
                        }
                    )

                )
                TextField(value =if(year!=null)year.toString() else "", onValueChange ={
                    year = if(it.isNotEmpty())it.toInt() else null
                }  ,
                    placeholder = {
                        Text(text = "YYYY" , fontSize = 18.sp , fontWeight = FontWeight.Medium , color = Color.LightGray)

                    } ,
                    modifier = Modifier.width(80.dp).clip(shape= RoundedCornerShape(5.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.LightGray.copy(0.08f),
                        unfocusedLabelColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.Transparent ,
                        focusedIndicatorColor = Color.Red,
                        cursorColor = Color.LightGray,
                        focusedLabelColor = Color.Red
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions (
                        onDone={
                            focusManager.clearFocus()
                        }
                    )
                )

                IconButton(onClick = {day= LocalDate.now().dayOfMonth
                    month= LocalDate.now().monthValue
                    year= LocalDate.now().year

                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_today), contentDescription ="today icon", tint =pink )


                }




            }


        }
        item{
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier= Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.SpaceAround){
                Button(onClick = { /*TODO*/ } , colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ) , contentPadding = PaddingValues(), modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)) ) {
                    Box(modifier = Modifier.clip(shape = RoundedCornerShape(15.dp))
                        .width(150.dp).background(color=pink)
                        .height(50.dp) , contentAlignment = Alignment.Center){
                        Text(text = "Save" , fontSize =18.sp , fontWeight = FontWeight.Bold , color= Color.White )


                    }

                }
                Button(onClick = { /*TODO*/ } , colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                ) , contentPadding = PaddingValues(), modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)) ) {
                    Box(modifier = Modifier.clip(shape = RoundedCornerShape(15.dp))
                        .width(150.dp).background(color= Color.White).border(width = 2.dp , shape = RoundedCornerShape(20.dp), color= Color.Red)
                        .height(50.dp) , contentAlignment = Alignment.Center){
                        Text(text = "Reset" , fontSize =18.sp , fontWeight = FontWeight.Bold , color= Color.Red )


                    }

                }

            }
        }

    }
}

