package com.example.expensetrackerproject

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Button
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
import androidx.compose.ui.graphics.Color
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
fun PersonalInfo(navController: NavController,userUi:String) {
    val db = Firebase.firestore
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
        val currencies: List<String> = listOf("USD", "EUR", "LBP")

        val focusManager = LocalFocusManager.current
        val firstNameClearIcon =
            animateColorAsState(targetValue = if (firstName != null) Color.LightGray else Color.Transparent)
        val lastNameClearIcon =
            animateColorAsState(targetValue = if (lastName != null) Color.LightGray else Color.Transparent)
        val budgetClearIcon =
            animateColorAsState(targetValue = if (budget != null) Color.LightGray else Color.Transparent)
        var currencyClearIcon =
            animateColorAsState(targetValue = if (currency != null) Color.LightGray else Color.Transparent)


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
                isError = lastNameError

            )
            if (lastNameError) {
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
                isError = budgetError


            )
            if (budgetError) {
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
            OutlinedTextField(value = if (currency != null) currency.toString() else "",
                onValueChange = {
                    currency = if (it.isNotEmpty()) it else null
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
                    text = "Required field",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))


        Button(

            onClick = {
                if (firstName != null && lastName != null && budget != null && currency != null) {
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
                        "rent" to 0
                    )
                    db.collection("Users").document(userUi)
                        .set(user)
                        .addOnSuccessListener {
                            Log.d(
                                "user",
                                "DocumentSnapshot successfully written!"
                            )
                        }
                        .addOnFailureListener { e -> Log.w("User", "Error writing document", e) }
                    navController.navigate(route = "MainPage/$userUi")

                } else {
                    firstNameError = firstName == null

                   lastNameError = lastName == null

                    budgetError = budget == null

                   currencyError= currency==null
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
