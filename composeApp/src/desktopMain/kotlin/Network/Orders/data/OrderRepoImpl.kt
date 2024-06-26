package Network.Orders.data

import Network.Orders.domain.OrderRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class OrderRepoImpl(private val client: HttpClient) : OrderRepository {

    override suspend fun getOrders(): List<OrdersDataModel> {
       return client.get("/orders").body()
    }

}