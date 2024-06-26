
import Navigation.NavRoutes
import Screens.Login.LoginScreen
import Screens.MainScreen
import Screens.Login.SignupScreen
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navHostController = rememberNavController()

    MaterialTheme {

        Surface {

            NavHost(navController = navHostController, startDestination = NavRoutes.Authentication.routes){

                navigation(route = NavRoutes.Authentication.routes, startDestination = NavRoutes.Authentication.LoginScreen.routes){

                    composable(route = NavRoutes.Authentication.LoginScreen.routes){

                        LoginScreen(navHostController = navHostController)
                    }

                    composable(route = NavRoutes.Authentication.SignupScreen.routes){

                        SignupScreen()
                    }

                }

                navigation(route = NavRoutes.MainScreen.routes, startDestination = NavRoutes.MainScreen.MainScreen.routes){

                    composable(route = NavRoutes.MainScreen.MainScreen.routes){

                        MainScreen()

                    }



                }


                }

            }


        }

    }



