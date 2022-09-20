package com.example.expensetrackerproject.Settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.Green
import com.example.expensetrackerproject.ui.theme.darkGray
import com.example.expensetrackerproject.ui.theme.lightGreen


@Composable
fun Settings(navController: NavController , userUi:String) {
    var sendNotification by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(text = "Settings", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.account),
                contentDescription = "Account Icon"
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Account", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(1.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                .clip(shape = RoundedCornerShape(5.dp))
        )

        Text(text = "Name : $userUi", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = darkGray)
        Text(text = "Email : ", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = darkGray)
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = "Lock icon",
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Change Password",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = darkGray
                )
            }
            IconButton(onClick = { navController.navigate(route = "ResetPassword") }) {


                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Arrow icon",
                    tint = Color.Black
                )
            }

        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.tabler_switch),
                    contentDescription = "Lock icon",
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Switch Account",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = darkGray
                )
            }
            IconButton(onClick = { navController.navigate(route = "SignInScreen") }) {


                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Arrow icon",
                    tint = Color.Black
                )
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.notification),
                contentDescription = "notification icon",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Notification", fontSize = 16.sp, fontWeight = FontWeight.Normal)

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(1.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                .clip(shape = RoundedCornerShape(5.dp))
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Receive Notification",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = darkGray
            )
            Switch(
                checked = sendNotification, onCheckedChange = {
                    sendNotification = true
                }, colors = SwitchDefaults.colors(
                    checkedTrackColor = lightGreen,
                    checkedThumbColor = Green


                )
            )

        }


    }
}




