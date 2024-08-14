package Network.Orders.domain

import Network.Orders.data.OrdersDataModel
import domain.usecase.UiState
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun getOrders():List<OrdersDataModel>

    suspend fun getOrdersByProfessionalId(prof_id:String) : Flow<UiState<List<OrdersDataModel>>>

}