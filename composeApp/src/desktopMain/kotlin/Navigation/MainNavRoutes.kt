package Navigation

sealed class MainNavRoutes(val routes:String){


        object Dashboard:MainNavRoutes("Dashboard"){
            object DashboardScreen:MainNavRoutes("DashboardScreen")
        }

        object Orders:MainNavRoutes("Order"){
            object OrderListScreen:MainNavRoutes("OrderListScreen")
            object OrderDetailScreen:MainNavRoutes("OrderDetailScreen")
        }
        object Products:MainNavRoutes("Products"){
            object CategoryList:MainNavRoutes("CategoryList")
            object AddCategory:MainNavRoutes("AddCategory")
        }









}