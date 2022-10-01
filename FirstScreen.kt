package com.example.expensetrackerproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerproject.ui.theme.Green



@Composable
fun FirstScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.pinkcircle),
                    contentDescription = "pink circle"
                )
                Image(
                    painter = painterResource(id = R.drawable.greencircle),
                    contentDescription = "pink circle"
                )
                Image(
                    painter = painterResource(id = R.drawable.lightorangecircle),
                    contentDescription = "pink circle"
                )
            }


            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {

                Image(
                    painter = painterResource(id = R.drawable.doublecircles),
                    contentDescription = "double circles"
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "It's your money own It", fontSize = 40.sp , fontWeight = FontWeight.Bold, modifier=Modifier.width(170.dp), textAlign = TextAlign.Center)

                    Image(
                        painter = painterResource(id = R.drawable.violetcircle),
                        contentDescription = "pink circle"
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.bluecircle),
                    contentDescription = "bleu Circle"
                )
                Button(onClick = {
                    navController.navigate(route="WelcomeScreen")
                }, colors =ButtonDefaults.buttonColors(
                    backgroundColor = Green
                ), modifier = Modifier
                    .width(155.dp)
                    .height(88.dp)
                    .padding(end = 10.dp, bottom = 10.dp)
                    .clip(shape = RoundedCornerShape(topStart = 70.dp, bottomEnd = 50.dp))) {

                    Row(modifier=Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                        Text(text ="Start", fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,)
                      Icon(painter = painterResource(id =R.drawable.arrow), contentDescription ="arrow", tint=Color.White, modifier=Modifier.height(16.dp).width(16.dp).padding(start=5.dp,top=4.dp) )


                    }





                }


            }


        }

    }


}


    

