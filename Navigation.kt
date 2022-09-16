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
fun Navigation(navController: NavController ){

    NavHost(navController = navController as NavHostController, startDestination ="WelcomeScreen" ){

        composable(route="WelcomeScreen"){
            WelcomeScreen(navController=navController )
        }
        composable(route = "SignUpScreen"){
            SignUp(navController=navController)
        }
        composable(route="SignInScreen"){
            SignIn(navController=navController)
        }
        composable(route="Home/{firstName}/{lastName}" , arguments = listOf(
            navArgument(name="firstName"){
                type= NavType.StringType
                nullable=false

            } ,
            navArgument(name="lastName"){
                type= NavType.StringType
                nullable=false
            }
        )
        ){
            backStackEntry ->
            Home(FirstName = backStackEntry.arguments?.get("firstName").toString() , LastName = backStackEntry.arguments?.get("lastName").toString())
        }
        composable(route="ResetPassword"){
            PasswordReset(navController=navController)
        }
        composable(route="Categories"){

        }

        composable(route="MainPage/{firstName}/{lastName}", arguments = listOf(
            navArgument(name="firstName"){
                type= NavType.StringType
                nullable=false

            } ,
            navArgument(name="lastName"){
                type= NavType.StringType
                nullable=false
            }
        )){
                backStackEntry ->
            MainPage(navController = navController,FirstName = backStackEntry.arguments?.get("firstName").toString() , LastName = backStackEntry.arguments?.get("lastName").toString())
            Log.d("first" , backStackEntry.arguments?.getString("firstName").toString())
            Log.d("last" , backStackEntry.arguments?.getString("lastName").toString())
        }
        composable(route="Settings"){
            Settings(navController=navController)
        }
        composable(route="PersonalInfo"){
            PersonalInfo(navController=navController)
        }


    }


}

