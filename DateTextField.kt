package com.example.expensetrackerproject.Categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.mediumGray
import com.example.expensetrackerproject.ui.theme.mint
import com.example.expensetrackerproject.ui.theme.pink
import java.time.LocalDate

@Composable
fun DateTextField(color: Color, focusManager:FocusManager,lightColor:Color){
    val day = mainActivityViewModel.day
    val month= mainActivityViewModel.month
    val year = mainActivityViewModel.year

    Row(modifier= Modifier
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(5.dp))
        .padding(10.dp), verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.spacedBy(5.dp)){
        Icon(painter = painterResource(id = R.drawable.ic_calendar), contentDescription ="calendar icon" , tint = mediumGray )
        Spacer(modifier = Modifier.width(7.dp))
        Text(text ="Date" , fontSize = 18.sp , fontWeight = FontWeight.Medium , color= mediumGray)

        OutlinedTextField(value =if(day.value!=null)day.value.toString() else "", onValueChange ={
            if(it.isNotEmpty()&&it.length<=2) {
                mainActivityViewModel.setValue(it,"day")
            }
        }  ,
            placeholder = {
                Text(text = "DD" , fontSize = 18.sp , fontWeight = FontWeight.Medium , color = mediumGray)
            } ,
            modifier = Modifier
                .width(60.dp)
                .clip(shape = RoundedCornerShape(5.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor=Color.White,
                unfocusedLabelColor = mediumGray,
                unfocusedIndicatorColor = mediumGray ,
                focusedIndicatorColor =mint,
                cursorColor = mediumGray,
                focusedLabelColor = mint
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions (
                onNext={
                    focusManager.moveFocus(FocusDirection.Right)
                }
            )
        )
        OutlinedTextField(value =if(month.value!=null)month.value.toString() else "", onValueChange ={
            if(it.isNotEmpty()&&it.length<=2) {
               mainActivityViewModel.setValue(it,"month")
            }

        }  ,
            placeholder = {
                Text(text = "MM" , fontSize = 18.sp , fontWeight = FontWeight.Medium , color = mediumGray)
            } ,
            modifier = Modifier
                .width(70.dp)
                .clip(shape = RoundedCornerShape(5.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor=Color.White,
                unfocusedLabelColor = mediumGray,
                unfocusedIndicatorColor = mediumGray ,
                focusedIndicatorColor =mint,
                cursorColor = mediumGray,
                focusedLabelColor = mint
            ), keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions (
                onNext={
                    focusManager.moveFocus(FocusDirection.Right)
                }
            ),

        )
        OutlinedTextField(value =if(year.value!=null)year.value.toString() else "", onValueChange ={
            if(it.isNotEmpty()&&it.length<=4) {
                mainActivityViewModel.setValue(it,"year")
            }
        }  ,
            placeholder = {
                Text(text = "YYYY" , fontSize = 18.sp , fontWeight = FontWeight.Medium , color = mediumGray)

            } ,
            modifier = Modifier
                .width(80.dp)
                .clip(shape = RoundedCornerShape(5.dp)),
                colors = TextFieldDefaults.textFieldColors(
                backgroundColor=Color.White,
                unfocusedLabelColor = mediumGray,
                unfocusedIndicatorColor = mediumGray ,
                focusedIndicatorColor =mint,
                cursorColor = mediumGray,
                focusedLabelColor = mint
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
        IconButton(onClick = {
            mainActivityViewModel.setValue( LocalDate.now().dayOfMonth,"day")
            mainActivityViewModel.setValue( LocalDate.now().monthValue,"month")
            mainActivityViewModel.setValue( LocalDate.now().year,"year")
            focusManager.clearFocus()

        }) {
            Icon(painter = painterResource(id = R.drawable.ic_today), contentDescription ="today icon", tint = mint)
        }
    }
}


