package Network.Orders.presantation

import Network.Orders.data.OrderRepoImpl
import Network.Orders.data.OrdersDataModel
import Network.Orders.domain.OrderRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private val _allOrders: MutableStateFlow<UiState<List<OrdersDataModel>>> = MutableStateFlow(UiState.LOADING)
    val allOrders = _allOrders.asStateFlow()

    fun getAllOrders(){

        viewModelScope.launch {

            _allOrders.value = UiState.LOADING
            try {
                val response = orderRepository.getOrders()
                _allOrders.value = UiState.SUCCESS(response)
            } catch (e: Exception) {
                _allOrders.value = UiState.ERROR(e)
            }


        }
    }



}