import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
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
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

@Composable
fun SignIn(navController: NavController) {
    val auth = Firebase.auth
    val interactionSource = remember { MutableInteractionSource() }
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
    var emailRequirementError by remember {
        mutableStateOf(false)
    }
    var passwordRequirementError by remember {
        mutableStateOf(false)
    }
    var signInButtonClicked by remember {
        mutableStateOf(false)
    }
    var isSigningIn by remember {
        mutableStateOf(false)
    }
    var signInError by remember {
        mutableStateOf(false)
    }
    var errorMessage: String? by remember {
        mutableStateOf(null)
    }
    var emailErrorMessage: String? by remember {
        mutableStateOf(null)
    }
    var passwordErrorMessage: String? by remember {
        mutableStateOf(null)
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
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    OutlinedTextField(value = if (email != null) email.toString() else "",
                        onValueChange = {
                            if (it.isNotEmpty()) {
                                email = it
                                isEmailEmpty = false

                            } else {
                                email = null
                                isEmailEmpty = true
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
                            IconButton(onClick = {
                                email = null
                                isEmailEmpty = true
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear Icon",
                                    tint = clearIconColor.value
                                )
                            }
                        },
                        modifier = Modifier
                            .onFocusEvent { focusState ->
                                if (focusState.isFocused) {
                                    emailRequirementError = false
                                    errorMessage = null
                                }
                            }
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(5.dp)
                            ),
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
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.moveFocus(FocusDirection.Next)
                            }
                        ),
                        isError = emailRequirementError
                    )

                    if (emailRequirementError) {
                        Text(
                            text = "$emailErrorMessage",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp, top = 0.dp)

                        )
                    }

                    if (errorMessage == "The email address is badly formatted." || errorMessage == "There is no user record corresponding to this identifier. The user may have been deleted.") {
                        emailRequirementError = true
                        emailErrorMessage = "Email does not exist."
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
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
                            .onFocusEvent { focusState ->
                                if (focusState.isFocused) {
                                    passwordRequirementError = false
                                    errorMessage = null
                                    if (email == null) {
                                        emailRequirementError = true
                                        emailErrorMessage = "Required Field"
                                    } else {
                                        if (email != null && !isValidEmail(email)) {
                                            emailRequirementError = true
                                            emailErrorMessage = "Invalid Email"
                                        } else {
                                            emailRequirementError = false
                                        }
                                    }
                                }
                            }
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(5.dp)
                            ),
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
                        ), isError = passwordRequirementError

                    )
                    if (passwordRequirementError) {
                        Text(
                            text = "$passwordErrorMessage",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                        )
                    }
                    if (errorMessage == "The password is invalid or the user does not have a password.") {
//                            //"The password is invalid or the user does not have a password."
                        passwordRequirementError = true
                        passwordErrorMessage = "Wrong message !"
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Forgot Password ?",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = blueLink,
                        modifier = Modifier.clickable {
                            navController.navigate(route="ResetPassword?userUi=&oldPassword=/SignInScreen")
                        }

//                            .clickable(interactionSource=interactionSource, indication = null){
//                                focusManager.clearFocus()
//                                if (email == null) {
//                                    emailRequirementError = true
//                                    emailErrorMessage = "Required Field"
//                                } else {
//                                    if (email != null && !isValidEmail(email)) {
//                                        emailRequirementError = true
//                                        emailErrorMessage = "Invalid Email"
//                                    } else {
//
//                                    }
                               // }
                    //        },
                        )
                }
            }
            item {
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    onClick = {
                        if (email == null) {
                            emailRequirementError = true
                            emailErrorMessage = "Required Field"
                        }
                        if (password == null) {
                            passwordRequirementError = true
                            passwordErrorMessage = "Required Field"
                        }

                        signInButtonClicked = true
                        focusManager.clearFocus()
                        if (email != null && password != null) {
                            isSigningIn = true
                            auth.signInWithEmailAndPassword(email!!, password!!)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("TAG", "signInWithEmail:success")
                                        val userUi = auth.currentUser?.uid
                                        navController.navigate(route = "MainPage/$userUi")
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                                        isSigningIn = false
                                        signInButtonClicked = false
                                        signInError = true
                                        errorMessage = task.exception?.message.toString()
                                    }
                                }
                            Log.d(
                                "currentuser", EmailAuthProvider.getCredential(
                                    email!!,
                                    password!!
                                ).toString()
                            )
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
                        if (isSigningIn) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Signing In",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                CircularProgressIndicator(
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(16.dp),
                                    color = Color.White
                                )
                            }
                        } else {
                            Text(
                                text = "Sign In",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
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
                    onClick = {
                        googleButtonClicked = !googleButtonClicked
                        navController.navigate(route = "MainPage") {
                            popUpTo(route = "MainPage") { inclusive = true }
                        }
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

//focusManager.moveFocus(FocusDirection.Down)