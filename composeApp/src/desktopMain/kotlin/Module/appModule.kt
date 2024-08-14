package di


import Network.Category.Presantation.CategoryViewModel
import Network.Category.data.CategoryRepoImpl
import Network.Category.domain.CategoryRepository
import Network.Orders.data.OrderRepoImpl
import Network.Orders.domain.OrderRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.withOptions
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import utils.Constant
import Network.Orders.presantation.OrdersViewModel
import Network.Products.data.ProductDataModel
import Network.Products.data.ProductRepoImpl
import Network.Products.domain.ProductRepository
import Network.Products.presantation.ProductViewModel
import Network.Professionals.data.ProfessionalRepoImpl
import Network.Professionals.domain.ProfessionalRepository
import Network.Professionals.presantation.ProfessionalsViewModel
import Network.Promotion.data.PromotionRepoImpl
import Network.Promotion.domain.PromotionRepository
import Network.Promotion.presantation.PromotionViewModel
import Network.Rate.RateCardRepoImpl
import Network.Rate.RateCardRepository
import Network.Rate.RateCardViewModel
import Network.offers.data.OfferRepoImpl
import Network.offers.domain.OfferRepository
import Network.offers.presantation.OfferViewModel
import Screens.Offers.createOfferViewModel
import SharedViewModel.SharedOrderViewModel
import SharedViewModel.SharedProfessionalViewModel
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
val appModule = module {

    single {
        HttpClient {

            install(ContentNegotiation){
                json(
                    Json {
                        explicitNulls = false
                        isLenient = true
                        prettyPrint = true
                        ignoreUnknownKeys = true // This helps avoid errors with unexpected fields
                    }
                )
            }
            install(Logging){
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }

            }
            defaultRequest {
                url(Constant.BASE_URL)
                headers.append(HttpHeaders.Accept, "application/json")
            }

        }
    }
    single<OrderRepository> { OrderRepoImpl(get()) }
    single <CategoryRepository>{ CategoryRepoImpl(get()) }
    single <ProductRepository>{ ProductRepoImpl(get()) }
    single <OfferRepository>{ OfferRepoImpl(get()) }
    single <PromotionRepository>{ PromotionRepoImpl(get()) }
    single <ProfessionalRepository>{ ProfessionalRepoImpl(get()) }
    single <RateCardRepository>{ RateCardRepoImpl(get()) }

    single { OrdersViewModel(get()) }
    single { CategoryViewModel(get()) }
    single { ProductViewModel(get()) }
    single { OfferViewModel(get()) }
    single { SharedOrderViewModel() }
    single { SharedProfessionalViewModel() }
    single { createOfferViewModel() }
    single { PromotionViewModel(get()) }
    single { ProfessionalsViewModel(get()) }
    single { RateCardViewModel(get()) }

}