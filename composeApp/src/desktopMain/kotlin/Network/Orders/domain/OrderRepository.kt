package Network.Orders.domain

import Network.Orders.data.OrdersDataModel

interface OrderRepository {

    suspend fun getOrders():List<OrdersDataModel>

}