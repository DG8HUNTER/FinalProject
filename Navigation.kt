import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expensetrackerproject.Categories.Categories
import com.example.expensetrackerproject.Home.Home
import com.example.expensetrackerproject.MainPage
import com.example.expensetrackerproject.PasswordReset
import com.example.expensetrackerproject.PersonalInfo
import com.example.expensetrackerproject.Settings.Settings
import com.example.expensetrackerproject.WelcomeScreen
import com.google.firebase.firestore.ktx.FirebaseFirestoreKtxRegistrar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun Navigation(navController: NavController ) {

    NavHost(
        navController = navController as NavHostController,
        startDestination = "WelcomeScreen"
    ) {

        composable(route = "WelcomeScreen") {
            WelcomeScreen(navController = navController)
        }
        composable(route = "SignUpScreen") {
            SignUp(navController = navController)
        }
        composable(route = "SignInScreen") {
            SignIn(navController = navController)
        }
        composable(route = "Home/{userUi}", arguments = listOf(
            navArgument(name = "userUi") {
                type = NavType.StringType
                nullable = false

            }
        )
        ) { backStackEntry ->
            Home(userUi = backStackEntry.arguments?.get("userUi").toString())
        }
        composable(route = "ResetPassword") {
            PasswordReset(navController = navController)
        }
        composable(route = "Categories") {

        }

        composable(route = "MainPage/{userUi}", arguments = listOf(

            navArgument(name = "userUi") {
                type = NavType.StringType
                nullable = false
            }
        )) { backStackEntry ->
            MainPage(
                navController = navController,
                userUi = backStackEntry.arguments?.get("userUi").toString()
            )

        }
//        composable(route="Settings"){
//            Settings(navController=navController)
//        }
        composable(route = "PersonalInfo/{userUi}", arguments = listOf(
            navArgument(name = "userUi") {
                type = NavType.StringType
                nullable = false
            }
        )) {

                navBackStackEntry ->
            PersonalInfo(
                navController = navController,
                userUi = navBackStackEntry.arguments?.get("userUi").toString()
            )
        }


    }


}

