package Screens

import DrawerItem
import Navigation.NavRoutes
import UI.secondary_color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import drawerList

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()
    val navHostController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quick Service") },
                backgroundColor = Color.White

            )
        }
    ) {

        Row(modifier = Modifier.fillMaxSize().background(secondary_color)) {

            var clickedValue by rememberSaveable{
                mutableStateOf("Dashboard")
            }
            LazyColumn(
                modifier = Modifier.width(250.dp).fillMaxHeight().shadow(
                    elevation = 0.5.dp
                ).background(Color.White),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ) {

                items(drawerList){

                    DrawerItem(
                        onClick = {value->
                            clickedValue = value

                            if(it.title =="Dashboard"){
                                navHostController.navigate(NavRoutes.MainScreen.Dashboard.routes)
                            }
                            if(it.title =="Orders"){
                                navHostController.navigate(NavRoutes.MainScreen.Orders.routes)
                            }
                        },
                        title = it.title,
                        clickedItem = clickedValue
                    )

                }

            }


            NavHost(
                modifier = Modifier.padding(20.dp).fillMaxSize().clip(RoundedCornerShape(8.dp)),
                navController = navHostController,
                startDestination = NavRoutes.MainScreen.Dashboard.routes

            ) {

                composable(route = NavRoutes.MainScreen.Dashboard.routes) {
                   DashboardScreen()
                }

                navigation(
                    route = NavRoutes.MainScreen.Orders.routes,
                    startDestination = NavRoutes.MainScreen.Orders.OrderListScreen.routes
                ) {

                    composable(route = NavRoutes.MainScreen.Orders.OrderListScreen.routes) {
                        OrderListScreen(navHostController = navHostController)
                    }
                    composable(route = NavRoutes.MainScreen.Orders.OrderDetailScreen.routes) {
                        OrderDetailsScreen()
                    }

                }

            }
        }


    }
}