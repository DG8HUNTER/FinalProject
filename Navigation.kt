import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expensetrackerproject.Categories.Categories
import com.example.expensetrackerproject.Home.Home
import com.example.expensetrackerproject.MainPage
import com.example.expensetrackerproject.PasswordReset
import com.example.expensetrackerproject.Settings.Settings
import com.example.expensetrackerproject.WelcomeScreen



@Composable
fun Navigation(navController: NavController){
    NavHost(navController = navController as NavHostController, startDestination ="WelcomeScreen" ){

        composable(route="WelcomeScreen"){
            WelcomeScreen(navController=navController)
        }
        composable(route = "SignUpScreen"){
            SignUp(navController=navController)
        }
        composable(route="SignInScreen"){
            SignIn(navController=navController)
        }
        composable(route="Home"){
            Home()
        }
        composable(route="ResetPassword"){
            PasswordReset()
        }
        composable(route="Categories"){
            Categories()
        }

        composable(route="MainPage"){
           MainPage(navController=navController)
        }
        composable(route="Settings"){
            Settings(navController=navController)
        }


    }


}