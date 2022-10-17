package com.example.expensetrackerproject.Categories

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerproject.ui.theme.violet

val mainActivityViewModel=MainActivityViewModel()
@Composable
fun OTextField(name:String, placeHolder:String, color: Color,  leadingIcon:Int , trailingIcon:ImageVector, colorAnimation:Color,focusManager:FocusManager ){

    val data by when(name){
        "country"->mainActivityViewModel.country
        "name"->mainActivityViewModel.name
        "location"-> mainActivityViewModel.location
        "price" -> mainActivityViewModel.price
       else->mainActivityViewModel.quantity
    }
    OutlinedTextField(
        value = if (data != null) data.toString() else "",
        onValueChange = {
            if (it.isNotEmpty()) {
                mainActivityViewModel.setValue(newValue = it.toString(), name = name)
                Log.d("ho",mainActivityViewModel.country.value.toString())
            } else {
                mainActivityViewModel.setValue(newValue = null, name = name)
            }
        },
        label = {
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,


            )

        },
        placeholder = {
            Text(
                text = placeHolder,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.LightGray
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = "$name leading icon",
                tint=Color.LightGray

            )
        },
        trailingIcon = {
            IconButton(onClick = { mainActivityViewModel.setValue(newValue = null, name = name) }) {
                Icon(
                   imageVector = trailingIcon,
                    contentDescription = "$name trailing Icon",
                    tint = colorAnimation
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = when (name) {
                "country" -> KeyboardType.Text
                "name" -> KeyboardType.Text
                "location"->KeyboardType.Text
                "price" -> KeyboardType.Number
                else -> KeyboardType.Number
            },
            imeAction = ImeAction.Next

        ),
        keyboardActions = KeyboardActions(
            onNext = {
                when (name) {
                    "price" -> focusManager.moveFocus(FocusDirection.Next)
                    else -> focusManager.moveFocus(FocusDirection.Down)
                }
            },

            ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(5.dp)),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.LightGray.copy(0.08f),
            unfocusedLabelColor = Color.LightGray,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = color,
            cursorColor = Color.LightGray,
            focusedLabelColor = color
        ),
    )

}


//OTextField(
//name = "country",
//placeHolder ="Enter the country" ,
//color =Color.Red,
//leadingIcon = R.drawable.ic_location,
//trailingIcon =Icons.Filled.Clear,
//colorAnimation =locationClearIcon.value ,
//
//focusManager = focusManager
//)
//OTextField(
//name ="price",
//placeHolder ="Enter the price" ,
//color =Red,
//leadingIcon = R.drawable.ic_dollar,
//trailingIcon =Icons.Filled.Clear,
//colorAnimation =priceClearIcon.value ,
//focusManager = focusManager
//)