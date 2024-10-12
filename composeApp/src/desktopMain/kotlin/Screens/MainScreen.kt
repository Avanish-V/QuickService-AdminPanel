package Screens

import DrawerItem
import Navigation.MainNavRoutes
import Screens.Dashboard.DashboardScreen
import Screens.Offers.CreateOfferScreen
import Screens.Offers.OfferListScreen
import Screens.Orders.OrderDetailsScreen
import Screens.Orders.OrderListScreen
import Screens.Products.AddCategoryListScreen
import Screens.Products.AddProductScreen
import Screens.Products.CategoryListScreen
import Screens.Products.ProductScreen
import Screens.Professionals.ProfessionalDetail
import Screens.Professionals.ProfessionalListScreen
import Screens.Promotion.PromotionScreen
import Screens.RateCard.RateCardScreen
import UI.secondary_color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import drawerList
import me.sample.library.resources.Res
import me.sample.library.resources.savvy
import me.sample.library.resources.savvy_black

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()

    val navHostController = rememberNavController()

    var destName by remember {
        mutableStateOf(
            navHostController.currentBackStackEntry?.destination?.displayName ?: ""
        )
    }

    LaunchedEffect(navHostController) {
        navHostController.currentBackStackEntryFlow.collect { backStackEntry ->
            destName = backStackEntry.destination.displayName

        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                },
                backgroundColor = Color.White,
                elevation = 0.dp,
                actions = {

                    Row (modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically){
                        Image(
                            modifier = Modifier,
                            alignment = Alignment.CenterStart,
                            painter = org.jetbrains.compose.resources.painterResource(Res.drawable.savvy_black),
                            contentDescription = ""
                        )
                    }

                }

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
                contentPadding = PaddingValues(end = 20.dp)
            ) {

                item {}

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
                            if(it.title =="Offer"){
                                navHostController.navigate(MainNavRoutes.Offer.OfferListScreen.routes)
                            }
                            if(it.title =="Promotion"){
                                navHostController.navigate(MainNavRoutes.Promotion.PromotionScreen.routes)
                            }
                            if(it.title =="Professionals"){
                                navHostController.navigate(MainNavRoutes.Professionals.ProfessionalList.routes)
                            }
                            if(it.title =="Rate Card"){
                                navHostController.navigate(MainNavRoutes.RateCard.RateCardScreen.routes)
                            }
                        },
                        title = it.title,
                        clickedItem = clickedValue,
                        icon = it.icon
                    )

                }

            }

            Column {


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
                            OrderDetailsScreen(navHostController = navHostController)
                        }

                    }

                    navigation(route = MainNavRoutes.Products.routes, startDestination = MainNavRoutes.Products.CategoryList.routes){

                        composable(route = MainNavRoutes.Products.CategoryList.routes){
                            CategoryListScreen(navHostController = navHostController)
                        }
                        composable(route = MainNavRoutes.Products.AddCategory.routes){
                            AddCategoryListScreen(navHostController = navHostController)
                        }
                        composable(route = MainNavRoutes.Products.ProductScreen.routes){
                            ProductScreen(navHostController = navHostController)
                        }
                        composable(route = MainNavRoutes.Products.AddProductScreen.routes){
                            AddProductScreen(navHostController = navHostController)
                        }

                    }

                    navigation(
                        route = MainNavRoutes.Offer.routes,
                        startDestination = MainNavRoutes.Offer.OfferListScreen.routes,

                        ){
                        composable(route = MainNavRoutes.Offer.OfferListScreen.routes) {
                            OfferListScreen(navHostController = navHostController)
                        }

                        composable(route = MainNavRoutes.Offer.createOfferScreen.routes) {
                            CreateOfferScreen(navHostController = navHostController)
                        }


                    }

                    navigation(
                        route = MainNavRoutes.Promotion.routes,
                        startDestination = MainNavRoutes.Promotion.PromotionScreen.routes,

                        ){
                        composable(route = MainNavRoutes.Promotion.PromotionScreen.routes) {
                            PromotionScreen(navHostController = navHostController)
                        }

                    }

                    navigation(
                        route = MainNavRoutes.Professionals.routes,
                        startDestination = MainNavRoutes.Professionals.ProfessionalList.routes,

                        ){
                        composable(route = MainNavRoutes.Professionals.ProfessionalList.routes) {
                            ProfessionalListScreen(navHostController = navHostController)
                        }
                        composable(route = MainNavRoutes.Professionals.ProfessionalDetail.routes) {
                            ProfessionalDetail(navHostController = navHostController)
                        }

                    }

                    navigation(
                        route = MainNavRoutes.RateCard.routes,
                        startDestination = MainNavRoutes.RateCard.RateCardScreen.routes,

                        ){
                        composable(route = MainNavRoutes.RateCard.RateCardScreen.routes) {
                            RateCardScreen()
                        }


                    }


                }

            }


        }





    }
}