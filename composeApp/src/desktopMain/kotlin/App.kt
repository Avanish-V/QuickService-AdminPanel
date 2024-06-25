
import Navigation.NavRoutes
import Screens.DashboardScreen
import Screens.LoginScreen
import Screens.SignupScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import quickservice_adminpanel.composeapp.generated.resources.Res
import quickservice_adminpanel.composeapp.generated.resources.compose_multiplatform

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
                       DashboardScreen()
                    }


                }

            }


        }

    }
}


