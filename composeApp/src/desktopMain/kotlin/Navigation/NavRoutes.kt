package Navigation

sealed class NavRoutes(val routes:String){

    object Authentication : NavRoutes("Authentication"){
        object LoginScreen : NavRoutes("Login")
        object SignupScreen : NavRoutes("Signup")
    }
    object MainScreen:NavRoutes("MainScreen"){


        object MainScreen:NavRoutes("MainScreen")

        object Dashboard:NavRoutes("Dashboard")

        object Orders:NavRoutes("Order"){
            object OrderListScreen:NavRoutes("OrderListScreen")
            object OrderDetailScreen:NavRoutes("OrderDetailScreen")
        }
        object Products:NavRoutes("Products"){
            object CategoryList:NavRoutes("CategoryList")
            object AddCategory:NavRoutes("AddCategory")
        }


    }



}