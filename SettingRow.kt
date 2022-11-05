package com.example.expensetrackerproject.Settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerproject.Categories.Categorie
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.darkGray
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable

fun SettingRow(navController: NavController,userUi:String,sRow: SRow,){
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){
        Row(verticalAlignment = Alignment.CenterVertically){
            Icon(
                painter = painterResource(id =sRow.leadingIcon),
                contentDescription = " ${sRow.leadingIcon} icon",
                tint = Color.Black,
                modifier=Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text =sRow.text ,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = darkGray
            )


        }
        IconButton(onClick = {
            when (sRow.text) {
                "Update User Info" -> navController.navigate(route="PersonalInfo?password=/$userUi"){

                }
                "change Password" -> navController.navigate(route = "PasswordSecurity/$userUi"){

                }
                "Switch Account" -> navController.navigate(route = "SignInScreen") {
                    popUpTo(route = "MainPage/$userUi")
                }
                else -> {Firebase.auth.signOut()
                navController.navigate(route ="FirstScreen"){
                    popUpTo(0)




                }}
            }
        })

        {
            Icon(
                painter = painterResource(id = sRow.trailingIcon),
                contentDescription = "Arrow icon",
                tint = Color.Black,

                )
        }

    }


}