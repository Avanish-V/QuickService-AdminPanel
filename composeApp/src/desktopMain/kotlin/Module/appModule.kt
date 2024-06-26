package di


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

val appModule = module {

    single {
        HttpClient {
            install(ContentNegotiation){
                json(
                    Json {
                        isLenient = true
                        prettyPrint = true
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
                filter { request->
                    request.url.host.contains("127.0.0.1")
                }
                sanitizeHeader { header-> header == HttpHeaders.Authorization }
            }
            defaultRequest {
                url(Constant.BASE_URL)
            }
        }
    }
    single {OrderRepoImpl(get())}

}