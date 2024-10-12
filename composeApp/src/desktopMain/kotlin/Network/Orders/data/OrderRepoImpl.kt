package Network.Orders.data

import Network.Orders.domain.OrderRepository
import domain.usecase.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class OrderRepoImpl(private val client: HttpClient) : OrderRepository {

    override suspend fun getOrders(): List<OrdersDataModel> {
       return client.get("/orders").body()
    }

    override suspend fun getOrdersByProfessionalId(prof_id: String): Flow<UiState<List<OrdersDataModel>>> = callbackFlow {

        trySend(UiState.LOADING)

        try {

            val response = client.get("/orders/getOrdersByProfessionalId/$prof_id")

            when(response.status.value){
                in 200..299->{
                  trySend(UiState.SUCCESS(response.body()))
                }
            }

        }catch (e:Exception){
            trySend(UiState.ERROR(e))
        }

        awaitClose {
            close()
        }

    }


}