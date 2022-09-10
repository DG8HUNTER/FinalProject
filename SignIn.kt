import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.Green
import com.example.expensetrackerproject.ui.theme.blueLink
import kotlinx.coroutines.delay


@Composable
fun SignIn(navController: NavController) {

    var email: String? by remember {
        mutableStateOf(null)
    }
    var isEmailEmpty: Boolean by remember {
        mutableStateOf(true)
    }
    val clearIconColor = animateColorAsState(
        targetValue = if (!isEmailEmpty) Color.LightGray else Color.Transparent,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )
    var password: String? by remember {
        mutableStateOf(null)
    }
    var passVisibility by remember {
        mutableStateOf(false)
    }
    var isPasswordEmpty by remember {
        mutableStateOf(true)
    }
    val passTrailingIconColor = animateColorAsState(
        targetValue = if (isPasswordEmpty) Color.Transparent else Color.LightGray,
        animationSpec = tween(
            easing = FastOutSlowInEasing
        )
    )
    var googleButtonClicked by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Sign In",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.spacedBy(20.dp)) {


            item {
                OutlinedTextField(value = if (email != null) email.toString() else "",
                    onValueChange = {
                        if (it.isNotEmpty()) {
                            email = it
                            isEmailEmpty = !isEmailEmpty

                        } else {
                            email = null
                            isEmailEmpty = !isEmailEmpty
                        }
                    },
                    label = {
                        Text(text = "Email", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                    },
                    placeholder = {
                        Text(
                            text = "Create Email",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.LightGray
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "Email icon",
                            tint = Color.LightGray
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { email = null }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear Icon",
                                tint = clearIconColor.value
                            )

                        }
                    },
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
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
            }
            item {
                OutlinedTextField(value = if (password != null) password.toString() else "",
                    onValueChange = {
                        if (it.isNotEmpty()) {
                            password = it
                            isPasswordEmpty = false
                        } else {
                            password = null
                            isPasswordEmpty = true
                        }
                    },
                    label = {
                        Text(
                            text = "Password",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,

                            )
                    },
                    placeholder = {
                        Text(
                            text = "Enter Password",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.LightGray

                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lock),
                            contentDescription = "lock icon",
                            tint = Color.LightGray
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {

                            passVisibility = !passVisibility
                        }) {
                            Icon(
                                painter = painterResource(id = if (passVisibility) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                                contentDescription = if (passVisibility) "ic_visibility" else "ic_visibility_off",
                                tint = passTrailingIconColor.value
                            )


                        }
                    },
                    visualTransformation = if (!passVisibility) PasswordVisualTransformation() else VisualTransformation.None,
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

                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )

                )
            }

            item {
                Text(
                    text = "Forgot Password ?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = blueLink,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{
                            navController.navigate(route="ResetPassword")
                        },
                    textAlign = TextAlign.End
                )
            }
            item {
                Spacer(modifier = Modifier.size(10.dp))
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
                            .background(color = Green, shape = RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center,
                    ) {

                        Text(
                            text = "Sign In",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )

                    }


                }
            }
            item {
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account ?",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Text(
                        text = " Sign Up ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = blueLink,
                        modifier = Modifier.clickable {
                            navController.navigate(route = "SignUpScreen")

                        })

                }
            }

            item {
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(0.47f)
                            .height(1.dp)
                            .background(color = Color.LightGray)
                            .clip(shape = RoundedCornerShape(20.dp))
                    )
                    Text(
                        text = " OR ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.LightGray
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(1.dp)
                            .background(color = Color.LightGray)
                            .clip(shape = RoundedCornerShape(20.dp))
                    )


                }
            }
            item {


                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    onClick = { googleButtonClicked = !googleButtonClicked
                             navController.navigate(route="MainPage")

                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp))
                        .shadow(elevation = 10.dp, shape = RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                            .clip(shape = RoundedCornerShape(10.dp))
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(10.dp),
                                color = Color.LightGray
                            ), contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.google),
                                contentDescription = "Google icon",
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Sign in with Google",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray,
                                modifier = Modifier.height(24.dp)
                            )
                            if (googleButtonClicked) {
                                Spacer(modifier = Modifier.width(8.dp))
                                CircularProgressIndicator(
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(16.dp),
                                    color = Green
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}
