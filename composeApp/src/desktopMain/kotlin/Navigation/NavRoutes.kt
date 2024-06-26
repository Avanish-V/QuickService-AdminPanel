package Navigation

sealed class NavRoutes(val routes:String){

    object Authentication : NavRoutes("Authentication"){
        object LoginScreen : NavRoutes("Login")
        object SignupScreen : NavRoutes("Signup")
    }

    object MainScreen:NavRoutes("MainScreen"){
        object MainScreen:NavRoutes("RouteMainScreen")
    }

}