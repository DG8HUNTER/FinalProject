package com.example.expensetrackerproject

//
//import androidx.compose.animation.animateColorAsState
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Clear
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.State
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.focus.FocusManager
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//
//fun CreateTextField(  type:String , label:String, placeholder:String, leadingIcon:Int, focusManager: FocusManager,color:Color ){
//  var expense = type
//    val trailingIconColor = animateColorAsState(targetValue =if(type.value!=null)Color.LightGray else Color.Transparent)
//    OutlinedTextField(
//        value = expense?.toString() ?: "",
//        onValueChange = {
//         expense.value= if (it.isNotEmpty()) it else null
//        },
//        label = {
//            Text(text = label, fontSize = 18.sp, fontWeight = FontWeight.Medium)
//
//        },
//        placeholder = {
//            Text(
//                text = placeholder,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Medium,
//                color = Color.LightGray
//            )
//        } ,
//        leadingIcon = {
//            Icon(painter = painterResource(id =leadingIcon), contentDescription ="location icon", tint = Color.LightGray )
//
//        } ,
//        trailingIcon = {
//            IconButton(onClick = { expense=null }) {
//                Icon(imageVector = Icons.Filled.Clear, contentDescription ="Clear Icon", tint=trailingIconColor.value )
//
//            }
//
//        } ,
//        keyboardOptions = KeyboardOptions(
//            keyboardType = KeyboardType.Text,
//            imeAction = ImeAction.Done
//        ) ,
//        keyboardActions = KeyboardActions(
//            onDone = {
//                focusManager.clearFocus()
//            }
//        ) ,
//        modifier= Modifier
//            .fillMaxWidth()
//            .clip(shape = RoundedCornerShape(5.dp)),
//        colors = TextFieldDefaults.textFieldColors(
//            backgroundColor = Color.LightGray.copy(0.08f),
//            unfocusedLabelColor = Color.LightGray,
//            unfocusedIndicatorColor = Color.Transparent ,
//            focusedIndicatorColor = color,
//            cursorColor = Color.LightGray,
//            focusedLabelColor = color
//
//        )
//
//    )
//}
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun SliderComp(){
    var sliderPosition by remember { mutableStateOf(0f) }
    Column {
        Text(text = sliderPosition.toInt().toString())
        Slider(
            modifier = Modifier.semantics { contentDescription = "Localized Description" },
            value = sliderPosition.toFloat(),
            onValueChange = { sliderPosition = it },
            valueRange = 1f..12f,
            onValueChangeFinished = {
                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            },
            steps = 12,
        )
    }
}

@Preview
@Composable
fun PreviewSlider(){
    SliderComp()
}
