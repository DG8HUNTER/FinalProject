import android.text.TextUtils
import android.util.Log
import android.util.Patterns
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
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusModifier
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerproject.R
import com.example.expensetrackerproject.ui.theme.Green
import com.example.expensetrackerproject.ui.theme.blueLink
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
@Composable
fun SignUp(navController: NavController) {
    lateinit var auth: FirebaseAuth
    auth = Firebase.auth
    var email: String? by remember {
        mutableStateOf(null)
    }
    var isEmailEmpty: Boolean by remember {
        mutableStateOf(true)
    }
    var password: String? by remember {
        mutableStateOf(null)
    }
    var passVisibility by remember {
        mutableStateOf(false)
    }
    var isPasswordEmpty by remember {
        mutableStateOf(true)

    }
    var passwordVerification: String? by remember {
        mutableStateOf(null)
    }
    var isPasswordVerificationEmpty: Boolean by remember {
        mutableStateOf(true)
    }
    var passwordVerificationVisibility: Boolean by remember {
        mutableStateOf(false)
    }

    var emailRequirementError: Boolean by remember {
        mutableStateOf(false)
    }
    var passwordRequirementError: Boolean by remember {
        mutableStateOf(false)
    }
    var passwordVRequirementError: Boolean by remember {
        mutableStateOf(false)
    }
    var createAccount by remember {
        mutableStateOf(false)
    }
    var createButtonClicked by remember {
        mutableStateOf(false)
    }


    var compatibility by remember {
        mutableStateOf(false)
    }
    var emailErrorMessage: String? by remember {
        mutableStateOf("Required Field")
    }
    var passwordErrorMessage :String ? by remember {
        mutableStateOf("Required Field")
    }
    var passwordVErrorMessage :String ? by remember {
        mutableStateOf("Required Field")
    }



//    var userUi :String? by remember{
//        mutableStateOf(null)
//    }

    val focusManager = LocalFocusManager.current
    val clearIconColor = animateColorAsState(
        targetValue = if (!isEmailEmpty) Color.LightGray else Color.Transparent,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )
    val passTrailingIconColor = animateColorAsState(
        targetValue = if (isPasswordEmpty) Color.Transparent else Color.LightGray,
        animationSpec = tween(
            easing = FastOutSlowInEasing
        )
    )
    var googleButtonClicked by remember {
        mutableStateOf(false)
    }

    val passwordVerificationIconColor =
        animateColorAsState(targetValue = if (passwordVerification != null) Color.LightGray else Color.Transparent)
    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Create Account",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
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
                                emailRequirementError = false

                            } else {
                                email = null
                                isEmailEmpty = true
                                if (createButtonClicked) {
                                    emailRequirementError = true
                                    emailErrorMessage = "Required Field"
                                }
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
                                if (createButtonClicked) {
                                    emailRequirementError = true
                                }
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
                                if (!focusState.isFocused) {
                                    if (email != null && !isValidEmail(email)) {
                                        emailRequirementError = true
                                        emailErrorMessage = "Invalid Email"
                                    }
                                } else {
                                    if(email==null){
                                        emailRequirementError=false
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
                            unfocusedLabelColor = Color.LightGray,


                            ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                if (!isValidEmail(email)) {
                                    emailRequirementError = true
                                    emailErrorMessage = "Invalid Email"
                                }
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
                                    if (email == null) {
                                        emailRequirementError = true
                                        emailErrorMessage = "Required Field"
                                    }

                                    if (password != null && password!!.length >= 6) {
                                        passwordRequirementError = false
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
                        ),
                        isError = passwordRequirementError
                    )
                    if(passwordRequirementError){
                        Text(
                                text =" $passwordErrorMessage",
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                            )
                    }
//                    if (createButtonClicked && password != null) {
//                        if (password!!.length < 6) {
//                            passwordRequirementError = true
//                            passwordLengthError = true
//                            Text(
//                                text = " Password should be at least 6 characters",
//                                color = MaterialTheme.colors.error,
//                                style = MaterialTheme.typography.caption,
//                                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
//                            )
//                        } else {
//                            passwordRequirementError = false
//                            passwordLengthError = false
//                        }
//
//                    }
//                    if (createButtonClicked && password == null) {
//                        Text(
//                            text = "Required Field",
//                            color = MaterialTheme.colors.error,
//                            style = MaterialTheme.typography.caption,
//                            modifier = Modifier.padding(start = 16.dp, top = 0.dp)
//                        )
//                    }
            }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {

                    OutlinedTextField(value = if (passwordVerification != null) passwordVerification.toString() else "",
                        onValueChange = {
                            if (it.isNotEmpty()) {
                                passwordVerification = it
                                isPasswordVerificationEmpty = false

                            } else {
                                passwordVerification = null
                                isPasswordVerificationEmpty = true

                            }
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
                                text = "Reenter Password",
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
                                passwordVerificationVisibility = !passwordVerificationVisibility
                            }) {
                                Icon(
                                    painter = painterResource(id = if (passwordVerificationVisibility) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                                    contentDescription = if (passwordVerificationVisibility) "ic_visibility" else "ic_visibility_off",
                                    tint = passwordVerificationIconColor.value
                                )
                            }
                        },
                        visualTransformation = if (passwordVerificationVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .onFocusEvent { focusState ->
                                if (focusState.isFocused) {
                                    if (passwordVerification != null && password != passwordVerification) {
                                        passwordVRequirementError = true
                                        passwordVErrorMessage = "Passwords do not match"
                                    } else if (passwordVerification != null && password == passwordVerification) {
                                        passwordVRequirementError = false
                                    } else{
                                        passwordVRequirementError = false
                                    }
                                    if (password == null) {
                                        passwordRequirementError = true
                                        passwordErrorMessage = "Required Field"
                                    } else if ( password!=null && password!!.length < 6) {
                                        passwordRequirementError=true
                                        passwordErrorMessage="Password should be at least 6 characters"

                                    }
                                    else{
                                        passwordRequirementError=false
                                    }

                                    if (email == null) {
                                        emailRequirementError = true
                                        emailErrorMessage = "Required Field"
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
                        ),
                        isError = passwordVRequirementError
                    )
                    if(passwordVRequirementError){
                               Text( text = "$passwordVErrorMessage",
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                            )
                    }

//                    if (createButtonClicked) {
//                        if (passwordVerification == null) {
//                            passwordVRequirementError = true
//
//                            Text(
//                                text = "Required Field",
//                                color = MaterialTheme.colors.error,
//                                style = MaterialTheme.typography.caption,
//                                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
//                            )
//                        } else {
//                            if (password == passwordVerification) {
//                                passwordVRequirementError = false
//                                compatibility = true
//
//                            } else {
//                                passwordVRequirementError = true
//                                compatibility = false
//                                Text(
//                                    text = "Passwords do not match",
//                                    color = MaterialTheme.colors.error,
//                                    style = MaterialTheme.typography.caption,
//                                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
//                                )
//                            }
//                        }
//                    }
//                }
                }
            }
            item {
                Log.d("email", email.toString())
                Spacer(modifier = Modifier.size(10.dp))
            }
            item {
                Button(
                    onClick = {
                        createButtonClicked = true
                        focusManager.clearFocus()
                        if (email != null && password != null && passwordVerification != null) {
                            if (!emailRequirementError && !passwordRequirementError && !passwordVRequirementError) {
                                createAccount=true
                                    auth.createUserWithEmailAndPassword(email!!, password!!)
                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                // Sign in success, update UI with the signed-in user's information
                                                Log.d("TAG", "createUserWithEmail:success")
                                                val userUi = auth.currentUser?.uid.toString()
                                                Log.d("User", userUi.toString())
                                                navController.navigate(route = "PersonalInfo/$userUi") {
                                                    popUpTo("SignUpScreen")
                                                }
                                            } else {
                                                emailRequirementError = true
                                                emailErrorMessage = it.exception?.message.toString()
                                                createAccount = false
                                            }
                                        }

                            }
                        }
                        else {
                            emailRequirementError = email == null
                            passwordRequirementError = password == null
                            passwordVRequirementError = passwordVerification == null
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
                    if (createAccount) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(color = Green, shape = RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Text(
                                    text = "Creating Account",
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
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(color = Green, shape = RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center,
                        ) {

                            Text(
                                text = "Create Account",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account ?",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    Text(
                        text = " Sign In ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = blueLink,
                        modifier = Modifier.clickable {
                            navController.navigate(route = "SignInScreen") {
                                popUpTo("SignUpScreen")
                            }
                        })
                }
            }
            item {
                Spacer(modifier = Modifier.size(20.dp))
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
                Spacer(modifier = Modifier.size(20.dp))
                Button(
                    onClick = { googleButtonClicked = !googleButtonClicked },
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
fun isValidEmail(target: CharSequence?): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

