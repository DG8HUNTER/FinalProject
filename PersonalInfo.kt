package com.example.expensetrackerproject

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerproject.ui.theme.Green
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

@Composable
fun PersonalInfo(navController: NavController,userUi:String,password:String?) {
    Log.d("Password in PI",password.toString())
    val db = Firebase.firestore
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        var firstName: String? by remember {
            mutableStateOf(null)
        }
        var lastName: String? by remember {
            mutableStateOf(null)
        }
        var budget: Float? by remember {
            mutableStateOf(null)
        }
        var currency: String? by remember {
            mutableStateOf(null)
        }
        val currencyArrowClicked by remember {
            mutableStateOf(false)
        }
        val currencyArrowDirection = animateFloatAsState(
            targetValue = if (currencyArrowClicked) 180f else 0f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
        var firstNameError by remember {
            mutableStateOf(false)
        }
        var lastNameError by remember {
            mutableStateOf(false)
        }
        var budgetError by remember {
            mutableStateOf(false)
        }
        var currencyError by remember {
            mutableStateOf(false)
        }
        var submitClicked by remember {
            mutableStateOf(false)
        }

        var isExpanded by remember {
            mutableStateOf(false)
        }
        val currencies: List<String> = listOf("USD", "EUR","GBP","LBP")

        val focusManager = LocalFocusManager.current
        val firstNameClearIcon =
            animateColorAsState(targetValue = if (firstName != null) Color.LightGray else Color.Transparent)
        val lastNameClearIcon =
            animateColorAsState(targetValue = if (lastName != null) Color.LightGray else Color.Transparent)
        val budgetClearIcon =
            animateColorAsState(targetValue = if (budget != null) Color.LightGray else Color.Transparent)
        var currencyClearIcon =
            animateColorAsState(targetValue = if (currency != null) Color.LightGray else Color.Transparent)

        LaunchedEffect(true ) {
            if (password == null) {
                db.collection("Users").document(userUi)
                    .get()
                    .addOnSuccessListener { document ->
                        firstName = document.data?.get("firstName").toString()
                        lastName = document.data?.get("lastName").toString()
                        budget = document.data?.get("budget").toString().toFloat()
                        currency = document.data?.get("currency").toString()
                        Log.d("PPPPPPPPPP", firstName.toString())
                    }
            }
        }
        Text(text = "Personal Info", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            OutlinedTextField(
                value = if (firstName != null) firstName.toString() else "",
                onValueChange = {
                    firstName = if (it.isNotEmpty()) it else null
                },
                isError = firstNameError,
                label = {
                    Text(text = "FirstName", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                },
                placeholder = {
                    Text(
                        text = "Enter your firstname",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Person icon",
                        tint = Color.LightGray
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { firstName = null }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear icon",
                            tint = firstNameClearIcon.value
                        )

                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedIndicatorColor = Color.Gray,
                    cursorColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.LightGray,
                )

            )
            if (firstNameError) {
                Text(
                    text = "Required field",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            OutlinedTextField(value = if (lastName != null) lastName.toString() else "",
                onValueChange = {
                    lastName = if (it.isNotEmpty()) it else null
                },
                label = {
                    Text(
                        text = "LastName",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                placeholder = { Text(text = "Enter your lastname") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Person icon",
                        tint = Color.LightGray
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { lastName = null }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear icon",
                            tint = lastNameClearIcon.value
                        )

                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedIndicatorColor = Color.Gray,
                    cursorColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.LightGray,
                ),
                isError = lastNameError

            )
            if (lastNameError) {
                Text(
                    text = "Required Field",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            OutlinedTextField(value = if (budget != null) budget.toString() else "",
                onValueChange = {
                    budget = if (it.isNotEmpty()) it.toFloat() else null
                },
                label = { Text(text = "Budget", fontSize = 15.sp, fontWeight = FontWeight.Medium) },
                placeholder = {
                    Text(
                        text = "Enter your monthly budget",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.salary),
                        contentDescription = "salary icon",
                        tint = Color.LightGray,
                        modifier = Modifier.size(24.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { budget = null }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear icon",
                            tint = budgetClearIcon.value
                        )

                    }

                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedIndicatorColor = Color.Gray,
                    cursorColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.LightGray,
                ),
                isError = budgetError


            )
            if (budgetError) {
                Text(
                    text = "Required Field",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            OutlinedTextField(value = if (currency != null) currency.toString() else "",
                onValueChange = {
                    if(it.length<=3){
                        currency=it
                    }
                },
                label = {
                    Text(text = "Currency", fontSize = 15.sp, fontWeight = FontWeight.Medium)

                },
                placeholder = {
                    Text(text = "Enter Currency", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.currency),
                        contentDescription = "Currency icon",
                        tint = Color.LightGray,
                        modifier = Modifier.size(24.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        isExpanded = true
                    }, modifier = Modifier.rotate(currencyArrowDirection.value)) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "down arrow",
                            tint = Color.LightGray
                        )

                        if (isExpanded) {
                            DropdownMenu(expanded = isExpanded, onDismissRequest = {

                                isExpanded = false
                            }) {
                                currencies.forEach { cur ->
                                    DropdownMenuItem(onClick = { currency = cur }) {
                                        Text(text = cur, fontSize = 15.sp)

                                    }
                                }


                            }
                        }

                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedIndicatorColor = Color.Gray,
                    cursorColor = Color.LightGray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.LightGray,
                ),
                isError = currencyError
            )
            if (currencyError) {
                Text(
                    text = "Required Field",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))


        Button(

            onClick = {
                  focusManager.clearFocus()
                if (firstName != null && lastName != null && budget != null && currency != null) {
                    if (password != null) {
                        val user = hashMapOf(
                            "id" to userUi,
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "budget" to budget,
                            "currency" to currency,
                            "expenses" to 0,
                            "travel" to 0,
                            "food" to 0,
                            "shopping" to 0,
                            "rent" to 0,

                        )
                        db.collection("Users").document(userUi)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(
                                    "user",
                                    "DocumentSnapshot successfully written!"
                                )
                                navController.navigate(route = "MainPage/$userUi") {
                                    popUpTo(route = "FirstScreen") {
                                        inclusive = true
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                }

                            }
                            .addOnFailureListener { e ->
                                Log.w(
                                    "User",
                                    "Error writing document",
                                    e
                                )
                            }


                    }
                    else{


                            val doc = db.collection("Users").document(userUi)
                            doc.update("firstName", firstName)
                            doc.update("lastName", lastName)
                            doc.update("budget", budget)
                            doc.update("currency", currency)
                                .addOnSuccessListener {
                                    Log.d("TAB", "Info Updated !")
                                    navController.navigate(route = "MainPage/$userUi") {
                                        popUpTo(route = "FirstScreen") {
                                            inclusive = true
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
                                    Toast.makeText(context, "Info Updated Successfully !", Toast.LENGTH_LONG).show()
                                }
                                .addOnFailureListener {
                                    Log.d("Tab", "error updating Info")
                                }

                        }

                } else{
                    firstNameError = firstName == null

                    lastNameError = lastName == null

                    budgetError = budget == null

                    currencyError = currency == null
                }

            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp)),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = Green, shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Submit",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}
