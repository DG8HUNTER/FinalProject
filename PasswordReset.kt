package com.example.expensetrackerproject

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.Green

@Composable
fun PasswordReset(navController: NavController) {
    val focusManager = LocalFocusManager.current
    var newPassword: String? by remember {
        mutableStateOf(null)
    }
    var newPasswordVisibility: Boolean by remember {
        mutableStateOf(false)
    }
    val newPasswordClearIcon by animateColorAsState(
        targetValue = if (newPassword != null) Color.Gray else Color.Transparent,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    var passwordVerification: String? by remember {
        mutableStateOf(null)
    }
    var passwordVerificationVisibility: Boolean by remember {
        mutableStateOf(false)

    }
    val passwordVerificationClearIcon by animateColorAsState(
        targetValue = if (passwordVerification != null) Color.Gray else Color.Transparent,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.spacedBy(1.dp)) {

IconButton(onClick = {
    navController.navigate(route="SignInScreen")
}) {
    Icon(painter = painterResource(id =R.drawable.arrow_left), contentDescription ="arrow left icon", tint =Color.Black, modifier=Modifier.size(25.dp))

}
            Text(
                text = "Password Reset",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }

        OutlinedTextField(value = if (newPassword != null) newPassword.toString() else "",
            onValueChange = {
                newPassword = if (it.isNotEmpty()) it else null
            },
            label = {
                Text(
                    text = "New Password",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,

                    )
            },
            placeholder = {
                Text(
                    text = " Enter new password",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.LightGray
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = "Lock icon",
                    tint = Color.LightGray,

                    )
            },
            trailingIcon = {
                IconButton(onClick = { newPasswordVisibility = !newPasswordVisibility }) {
                    Icon(
                        painter = painterResource(id = if (newPasswordVisibility) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                        contentDescription = if (newPasswordVisibility) "visibility icon" else "visibility off icon",
                        tint = newPasswordClearIcon
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),

            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            visualTransformation = if (newPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
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
                unfocusedLabelColor = Color.LightGray

            )

        )
        OutlinedTextField(
            value = if (passwordVerification != null) passwordVerification.toString() else "",
            onValueChange = {
                passwordVerification = if (it.isNotEmpty()) it else null
            },
            label = {
                Text(
                    text = "Verify Password",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,

                    )
            },
            placeholder = {
                Text(
                    text = "Retype Password",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.LightGray
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = "Lock icon",
                    tint = Color.LightGray,

                    )
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVerificationVisibility = !passwordVerificationVisibility
                }) {
                    Icon(
                        painter = painterResource(id = if (passwordVerificationVisibility) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                        contentDescription = if (passwordVerificationVisibility) "visibility icon" else "visibility off icon",
                        tint = passwordVerificationClearIcon
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),

            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            visualTransformation = if (passwordVerificationVisibility) VisualTransformation.None else PasswordVisualTransformation(),
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
                unfocusedLabelColor = Color.LightGray

            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = { /*TODO*/ },
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
                    .background(
                        color = com.example.expensetrackerproject.ui.theme.Green,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {

                Text(
                    text = "Reset Password",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )

            }


        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewResetPassword(){
    PasswordReset(navController = rememberNavController( ))
}


