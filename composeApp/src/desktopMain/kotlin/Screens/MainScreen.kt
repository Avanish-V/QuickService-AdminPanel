package Screens

import DrawerItem
import Navigation.MainNavRoutes
import Screens.Dashboard.DashboardScreen
import Screens.Orders.OrderDetailsScreen
import Screens.Orders.OrderListScreen
import Screens.Products.AddCategoryListScreen
import Screens.Products.CategoryListScreen
import UI.secondary_color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
                backgroundColor = Color.White,
                elevation = 0.dp

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
                                navHostController.navigate(MainNavRoutes.Dashboard.DashboardScreen.routes)
                            }
                            if(it.title =="Orders"){
                                navHostController.navigate(MainNavRoutes.Orders.OrderListScreen.routes)
                            }

                            if(it.title =="Products"){
                                navHostController.navigate(MainNavRoutes.Products.CategoryList.routes)
                            }
                        },
                        title = it.title,
                        clickedItem = clickedValue,
                        icon = it.icon
                    )

                }

            }


            NavHost(
                modifier = Modifier.padding(20.dp).fillMaxSize().clip(RoundedCornerShape(8.dp)),
                navController = navHostController,
                startDestination = MainNavRoutes.Dashboard.routes

            ) {

                navigation(
                    route = MainNavRoutes.Dashboard.routes,
                    startDestination = MainNavRoutes.Dashboard.DashboardScreen.routes,

                ){
                    composable(route = MainNavRoutes.Dashboard.DashboardScreen.routes) {
                        DashboardScreen()
                    }


                }


                navigation(
                    route = MainNavRoutes.Orders.routes,
                    startDestination = MainNavRoutes.Orders.OrderListScreen.routes
                ) {

                    composable(route = MainNavRoutes.Orders.OrderListScreen.routes) {
                        OrderListScreen(navHostController = navHostController)
                    }
                    composable(route = MainNavRoutes.Orders.OrderDetailScreen.routes) {
                        OrderDetailsScreen()
                    }

                }

                navigation(route = MainNavRoutes.Products.routes, startDestination = MainNavRoutes.Products.CategoryList.routes){

                    composable(route = MainNavRoutes.Products.CategoryList.routes){
                        CategoryListScreen(navHostController = navHostController)
                    }
                    composable(route = MainNavRoutes.Products.AddCategory.routes){
                        AddCategoryListScreen(navHostController = navHostController)
                    }

                }

            }
        }


    }
}