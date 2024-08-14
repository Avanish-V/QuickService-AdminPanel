package Navigation

sealed class MainNavRoutes(val routes: String) {


    object Dashboard : MainNavRoutes("Dashboard") {
        object DashboardScreen : MainNavRoutes("DashboardScreen")
    }

    object Orders : MainNavRoutes("Order") {
        object OrderListScreen : MainNavRoutes("OrderListScreen")
        object OrderDetailScreen : MainNavRoutes("OrderDetailScreen")
    }

    object Products : MainNavRoutes("Products") {
        object CategoryList : MainNavRoutes("CategoryList")
        object AddCategory : MainNavRoutes("AddCategory")
        object ProductScreen : MainNavRoutes("ProductScreen")
        object AddProductScreen : MainNavRoutes("AddProductScreen")
    }

    object Offer : MainNavRoutes("Offer") {
        object OfferListScreen : MainNavRoutes("OfferListScreen")
        object createOfferScreen : MainNavRoutes("CreateOfferScreen")
    }
    object Promotion : MainNavRoutes("Promotion") {
        object PromotionScreen : MainNavRoutes("PromotionScreen")

    }
    object Professionals : MainNavRoutes("Professional") {
        object ProfessionalList : MainNavRoutes("ProfessionalListScreen")
        object ProfessionalDetail : MainNavRoutes("ProfessionalDetailScreen")

    }

    object RateCard : MainNavRoutes("RateCard") {
        object RateCardScreen : MainNavRoutes("RateCardScreen")


    }


}