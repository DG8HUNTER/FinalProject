package com.example.expensetrackerproject

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.platform.LocalContext
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
import com.example.expensetrackerproject.Categories.mainActivityViewModel
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.Green
import com.example.expensetrackerproject.ui.theme.mediumGray
import com.example.expensetrackerproject.ui.theme.mint
import com.example.expensetrackerproject.ui.theme.onSurface
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.EmailAuthProvider.*
import com.google.firebase.auth.FacebookAuthProvider.getCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import isValidEmail
import kotlinx.coroutines.delay

@Composable
fun PasswordReset(navController: NavController, page:String,userUi:String?,oldPassword:String?) {
    val db =Firebase.firestore
    var buttonText:String by remember {
        mutableStateOf ("Reset Password")
    }
    var email :String?  by remember{
        mutableStateOf(null)
    }
    val focusManager= LocalFocusManager.current
    var emailRequirementError by remember {
        mutableStateOf(false)
    }
    var emailErrorMessage: String? by remember {
        mutableStateOf(null)
    }
    var isEmailEmpty: Boolean by remember {
        mutableStateOf(true)
    }
    val context = LocalContext.current
    var newPassword: String? by remember {
        mutableStateOf(null)
    }
    var newPasswordVisibility: Boolean by remember {
        mutableStateOf(false)
    }
    val newPasswordClearIcon by animateColorAsState(
        targetValue = if (newPassword != null) mediumGray else Color.Transparent,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    var passwordVerification: String? by remember {
        mutableStateOf(null)
    }
    var passwordVerificationVisibility: Boolean by remember {
        mutableStateOf(false)

    }
    val passwordVerificationClearIcon by animateColorAsState(
        targetValue = if (passwordVerification != null) mediumGray else Color.Transparent,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )
    var newPasswordError by remember{
        mutableStateOf(false)
    }
    var passwordVError by remember {
        mutableStateOf(false)

    }
    var newPasswordErrorMessage :String? by remember{
        mutableStateOf(null)
    }
    var passwordVErrorMessage:String?  by remember{
        mutableStateOf(null)
    }
    val clearIconColor = animateColorAsState(
        targetValue = if (!isEmailEmpty) mediumGray else Color.Transparent,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            IconButton(onClick = {
                if (page == "SignInScreen") {
                    navController.navigate(route =page)
                } else {
                    navController.navigate(route ="$page/$userUi")
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "arrow left icon",
                    tint = Color.Black,
                    modifier = Modifier.size(25.dp)
                )

            }
            Text(
                text = if(page=="SignInScreen")"Password Reset" else "Password Change" ,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
        if(page=="SignInScreen") {
            Row(
                modifier = Modifier.fillMaxWidth(5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enter your email address we will send you an email to reset your password.",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = mediumGray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.Start
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
                            }
                        }
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp))
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(5.dp)
                        ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor= onSurface,
                        backgroundColor = Color.Transparent,
                        unfocusedIndicatorColor = mediumGray,
                        focusedIndicatorColor = mint,
                        cursorColor = mediumGray,
                        focusedLabelColor = mint,
                        unfocusedLabelColor = mediumGray,
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
            }
        }
        else{
            buttonText="Change Password"

                Column(modifier=Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start , verticalArrangement = Arrangement.spacedBy(2.dp)) {
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
                                tint = mediumGray,

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
                            imeAction = ImeAction.Next
                        ),

                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(focusDirection = FocusDirection.Down)
                            }
                        ),
                        visualTransformation = if (newPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.onFocusEvent {
                            if (newPassword != null && newPassword!!.length >= 6) {
                                newPasswordError = false
                                newPasswordErrorMessage = null
                            }
                        }
                            .onFocusChanged {
                                    focusState ->
                                if(!focusState.isFocused){
                                    if(newPassword!=null && newPassword!!.length<6){
                                        newPasswordError=true
                                        newPasswordErrorMessage="Password must be at least 6 characters"
                                    }
                                    else{
                                        newPasswordError=false
                                        newPasswordErrorMessage=null
                                    }
                                } else{
                                    if(newPassword!=null && newPassword!!.length>=6){
                                        newPasswordError=false
                                        newPasswordErrorMessage=null
                                    }
                                    if(newPasswordError && newPasswordErrorMessage=="Required Field"){
                                        newPasswordError=false
                                        newPasswordErrorMessage=null
                                    }
                                }
                            }
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp)),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor= onSurface,
                            backgroundColor = Color.Transparent,
                            unfocusedIndicatorColor = mediumGray,
                            focusedIndicatorColor = mint,
                            cursorColor = onSurface,
                            focusedLabelColor = mint,
                            unfocusedLabelColor = mediumGray,
                        ) , isError = newPasswordError

                    )

                    if(newPasswordError){
                        Text(text ="$newPasswordErrorMessage" ,
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                        )
                    }
                }
                Column(modifier=Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start , verticalArrangement = Arrangement.spacedBy(2.dp)) {
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
                                tint = mediumGray,

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
                        modifier = Modifier.onFocusEvent {
                            focusState ->
                            if(focusState.isFocused){
                                if(passwordVError && passwordVErrorMessage=="Required Field"){
                                    passwordVError=false

                                }
                                if(passwordVerification==null){
                                    passwordVError=false
                                }
                            }
                        }
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(color = Color.Transparent, shape = RoundedCornerShape(5.dp)),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor= onSurface,
                            backgroundColor = Color.Transparent,
                            unfocusedIndicatorColor = mediumGray,
                            focusedIndicatorColor = mint,
                            cursorColor = onSurface,
                            focusedLabelColor = mint,
                            unfocusedLabelColor = mediumGray,

                        ) , isError = passwordVError
                    )
                    if(passwordVError){
                        Text(
                            text = "$passwordVErrorMessage",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp, top = 0.dp))
                    }
                    if(passwordVerification!=null && passwordVerification!=newPassword){
                        passwordVError=true
                        passwordVErrorMessage="Passwords do not match"

                    }
                    if(passwordVerification!=null && passwordVerification==newPassword){
                        passwordVError=false
                    }
                }
        }
        Button(
        onClick = {
            focusManager.clearFocus()
            if(page=="SignInScreen"){
            if(email==null){
                emailRequirementError=true
                emailErrorMessage="Required Field"
            }
            else {
                if (email != null && !isValidEmail(email)) {
                    emailRequirementError = true
                    emailErrorMessage = "Invalid Email"
                } else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email!!)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Email sent successfully to reset your password",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate(route = "SignInScreen")
                            } else {
                                Log.w("TAG", "signInWithEmail:failure", task.exception)
                                emailRequirementError = true
                                emailErrorMessage = "Email does not exist"
                            }
                        }


                }
            }
            }
            else{

                val auth = Firebase.auth
                val currentUser=auth.currentUser
                Log.d("userCurrent",currentUser?.email.toString())
                if(newPassword==null){
                    newPasswordError=true
                    newPasswordErrorMessage="Required Field"
                }
                if(passwordVerification==null){
                    passwordVError=true
                    passwordVErrorMessage="Required Field"
                }

                if(!newPasswordError && !passwordVError){
                    val credential = EmailAuthProvider.getCredential(currentUser?.email.toString(), oldPassword.toString())

                    Log.d("provide data" , currentUser?.providerData.toString())


                    currentUser!!.reauthenticate(credential)
                        .addOnCompleteListener { Log.d("TAG", "User re-authenticated.") }

                    Log.d("Current", currentUser.email.toString().toString())
                    currentUser.updatePassword(newPassword!!)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                mainActivityViewModel.setValue(newPassword,"password")
                                Log.d("TAG", "User password updated.")
                                Toast.makeText(context, "Password updated successfully",Toast.LENGTH_LONG).show()



                                Log.d("ui",userUi.toString())
                                mainActivityViewModel.setValue("Home","_selectedButton")
                                navController.navigate(route="MainPage/$userUi"){
                                    popUpTo(0)

                                }

                            }
                            else{
                                Toast.makeText(context,"Please re-enter your old password",Toast.LENGTH_LONG).show()
                                navController.navigate(route="PasswordSecurity/$userUi"){
                                    popUpTo(0)
                                   popUpTo(route="MainPage/$userUi")
                                }
                            }
                        }
                }
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
                .background(
                    color = mint,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = buttonText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )

        }


    }


    }

}












