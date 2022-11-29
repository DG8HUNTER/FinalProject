package com.example.expensetrackerproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true)
@Composable
fun Image(){
    Column(modifier=Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.planet_earth),
            contentDescription = "icon",
            modifier=Modifier.size(42.dp)
        )
        androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.map),
        contentDescription = "icon",
        modifier=Modifier.size(42.dp)
        )
    }
}



@Composable

fun PreviewImage(){
    Column(modifier= Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        Image()
    }

}