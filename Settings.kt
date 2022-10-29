package com.example.expensetrackerproject.Settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.darkGray
import com.example.expensetrackerproject.ui.theme.lightGreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun Settings(navController: NavController, userUi: String) {
    val db = Firebase.firestore
    val auth = Firebase.auth
    var sendNotification by remember {
        mutableStateOf(false)
    }

    var firstName: String? by remember {
        mutableStateOf("")
    }
    var lastName: String? by remember {
        mutableStateOf("")

    }
    var currency: String? by remember {
        mutableStateOf("")

    }
    val userEmail = Firebase.auth.currentUser?.email

    val rows: List<SRow> =
        listOf(SRow.UpdateUserInfo, SRow.ChangePassword, SRow.SwitchAccount, SRow.SignOut)

    val docRef = db.collection("Users").document(userUi)
    docRef.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                firstName = document.data?.get("firstName") as String?
                lastName = document.data?.get("lastName") as String?
                currency = document.data?.get("currency") as String?
                Log.d("TAG", "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d("TAG", "get failed with ", exception)
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


        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.account),
                    contentDescription = "Account Icon",
                    modifier = Modifier.size(20.dp)

                )
                Text(
                    text = "Account Info",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(1.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .clip(shape = RoundedCornerShape(5.dp))
            )


            Row {
                Text(
                    text = "Name : ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkGray
                )
                Text(
                    text = "$firstName  $lastName",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkGray
                )
            }
            Row {
                Text(
                    text = "Email : ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkGray
                )
                Text(
                    text = "$userEmail",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkGray
                )

            }
            Row {
                Text(
                    text = "Currency : ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkGray
                )
                Text(
                    text = "$currency",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkGray
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(1.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                    .clip(shape = RoundedCornerShape(5.dp))
            )

        }




        Spacer(modifier = Modifier.height(2.dp))
        rows.forEach { row ->
            SettingRow(navController = navController, userUi = userUi, sRow = row)
        }

    }
}




